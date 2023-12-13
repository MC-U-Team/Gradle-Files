package info.u_team.sign_jar_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.DependencyUtil
import info.u_team.sign_jar_plugin.SignJarExtension
import info.u_team.sign_jar_plugin.task.SignJarTask

@CompileStatic
class CreateSignJarTaskExtensionImpl {

	static TaskProvider<SignJarTask> sign(final Project project, final SignJarExtension extension, final TaskProvider<? extends Jar> taskProvider) {
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

			task.enabled = canSign

			task.dependsOn(taskProvider)
			task.mustRunAfter(taskProvider)
		})

		project.afterEvaluate {
			signJarTask.configure { task ->
				final def archiveFile = taskProvider.get().archiveFile

				task.inputFile.set(archiveFile)
				task.outputFile.set(archiveFile)
			}
		}

		DependencyUtil.assembleDependOn(project, signJarTask)
		DependencyUtil.allPublishingDependOn(project, signJarTask)
		DependencyUtil.allUploadDependOn(project, signJarTask)

		return signJarTask
	}
}
