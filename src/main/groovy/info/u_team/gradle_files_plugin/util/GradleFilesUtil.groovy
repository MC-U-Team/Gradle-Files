package info.u_team.gradle_files_plugin.util

import info.u_team.gradle_files_plugin.GradleFilesExtension
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesUtil {
	
	static def getProjectProperties() {
		final def project = GradleFilesPlugin.instance.project
		final def config = project.extensions.extraProperties.config
		final def extension = project.extensions.getByType(GradleFilesExtension)
		
		return [project, config, extension]
	}
}
