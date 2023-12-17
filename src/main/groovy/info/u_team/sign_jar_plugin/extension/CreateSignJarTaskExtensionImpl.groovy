package info.u_team.sign_jar_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.RegularFile
import org.gradle.api.plugins.BasePlugin
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
				if(archive) {
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

			task.keyStore.convention(extension.keyStore)
			task.alias.convention(extension.alias)
			task.storePass.convention(extension.storePass)
			task.keyPass.convention(extension.keyPass)

			task.enabled = canSign && project.hasProperty(Constants.BUILD_PROPERTY)

			task.dependsOn(taskProvider)
			task.mustRunAfter(taskProvider)
		})

		project.afterEvaluate {
			signJarTask.configure { task ->
				final Provider<RegularFile> archiveFile = fileFunction.call(taskProvider.get());

				task.inputFile.set(archiveFile)
				task.outputFile.set(archiveFile)
			}
		}

		DependencyUtil.allBuildingDependOn(project, signJarTask)

		return signJarTask
	}
}
