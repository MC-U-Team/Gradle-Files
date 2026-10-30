package info.u_team.sign_jar_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.ConfigurablePublishArtifact
import org.gradle.api.artifacts.PublishArtifact
import org.gradle.api.file.RegularFile
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider
import org.gradle.api.tasks.bundling.AbstractArchiveTask

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.ClassUtil
import info.u_team.sign_jar_plugin.SignJarExtension
import info.u_team.sign_jar_plugin.task.SignJarTask

@CompileStatic
class CreateSignJarTaskExtensionImpl {

	static final List<Closure<Provider<RegularFile>>> CUSTOM_ARCHIVE_MAPPING = new ArrayList()

	static TaskProvider<SignJarTask> sign(final Project project, final SignJarExtension extension, final TaskProvider<?> taskProvider) {
		sign(project, extension, taskProvider) { task ->
			if(task instanceof AbstractArchiveTask) {
				return task.archiveFile
			}
			for(archiveMapping in CUSTOM_ARCHIVE_MAPPING) {
				final def archive = archiveMapping.call(task)
				if(archive != null) {
					return archive
				}
			}
			throw new IllegalStateException("Task cannot be used as input for signing jars")
		}
	}

	static TaskProvider<SignJarTask> sign(final Project project, final SignJarExtension extension, final TaskProvider<?> taskProvider, @DelegatesTo(Task.class) final Closure<Provider<RegularFile>> fileFunction) {
		final boolean canSign = extension.alias.isPresent() && extension.storePass.isPresent();

		if(!canSign) {
			project.logger.warn("Signing of jars was requested, but required properties are missing")
		}

		final def signJarTask = project.tasks.register("sign" + StringUtils.capitalize(taskProvider.name), SignJarTask, { task ->
			task.description = "Sign the jar ${taskProvider.name}"
			task.group = BasePlugin.BUILD_GROUP

			task.enabled = canSign && project.hasProperty(Constants.BUILD_PROPERTY)

			task.inputFile.set(taskProvider.flatMap { Task archiveTask -> fileFunction.call(archiveTask) })

			// Use the input file as output if the task is disabled, so consumers of the signed jar get the unsigned jar instead
			final def signedFile = project.layout.buildDirectory.dir(Constants.SIGNED_JARS_DIRECTORY).zip(task.inputFile) { directory, file ->
				directory.file(file.asFile.name)
			}
			task.outputFile.convention(project.provider { task.enabled }.flatMap { boolean enabled ->
				enabled ? signedFile : task.inputFile
			})

			task.archiveClassifier.convention(taskProvider.map { Task archiveTask ->
				if(archiveTask instanceof AbstractArchiveTask) {
					return archiveTask.archiveClassifier.getOrNull()
				}
				if(archiveTask instanceof PublishArtifact) {
					return archiveTask.classifier
				}
				return null
			})
			task.archiveExtension.convention(task.inputFile.map { file -> StringUtils.substringAfterLast(file.asFile.name, ".") })

			task.keyStore.convention(extension.keyStore)
			task.alias.convention(extension.alias)
			task.storePass.convention(extension.storePass)
			task.keyPass.convention(extension.keyPass)

			task.dependsOn(taskProvider)
		})

		// Publish the signed jar instead of the original jar
		project.afterEvaluate {
			final def inputFile = signJarTask.get().inputFile.get().asFile
			[JavaPlugin.API_ELEMENTS_CONFIGURATION_NAME, JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME].each { name ->
				project.configurations.named(name) { configuration ->
					final def artifacts = configuration.outgoing.artifacts.findAll { PublishArtifact artifact -> artifact.file == inputFile }
					artifacts.each { PublishArtifact original ->
						configuration.outgoing.artifacts.remove(original)
						configuration.outgoing.artifact(signJarTask.flatMap { task -> task.outputFile }) { ConfigurablePublishArtifact artifact ->
							artifact.name = original.name
							artifact.classifier = original.classifier
							artifact.extension = original.extension
							artifact.type = original.type
						}
					}
				}
			}
		}

		DependencyUtil.allBuildingDependOn(project, signJarTask)

		return signJarTask
	}
}
