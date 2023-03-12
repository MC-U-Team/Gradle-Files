package info.u_team.gradle_files_plugin.util

import info.u_team.gradle_files_plugin.GradleFilesExtension
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesUtil {
	
	static def getProjectProperties() {
		final def plugin = GradleFilesPlugin.instance
		final def project = plugin.project
		final def config = project.extensions.extraProperties.config
		final def extension = plugin.extension
		
		return [project, config, extension]
	}
	
	static boolean isRootProject() {
		final def project = GradleFilesPlugin.instance.project
		return project.rootProject.is(project)
	}
}
