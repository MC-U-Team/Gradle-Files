package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.GradleFilesExtension
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

@CompileStatic
class DefaultJarExtensionImpl {

	static void defaultJar(final Project project, final GradleFilesExtension extension, final TaskProvider<? extends Jar> taskProvider) {
		final def mainProject = GradleFilesUtil.getMainProject(project)

		taskProvider.configure { task ->
			task.from(mainProject.file("LICENSE"))
			task.exclude(".cache")

			task.manifest(DefaultManifestExtensionImpl.defaultManifest(project, extension))
		}
	}
}
