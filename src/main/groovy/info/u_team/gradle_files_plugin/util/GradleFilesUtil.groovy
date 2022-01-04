package info.u_team.gradle_files_plugin.util

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesUtil {
	
	static def getConfig() {
		final def project = GradleFilesPlugin.instance.project
		final def config = project.extensions.extraProperties.config
		
		return [project, config]
	}
}
