package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class VersionExtensionImpl {
	
	static def version() {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		final def appendix
		if(config.mod.snapshot) {
			appendix = "-SNAPSHOT"
		} else {
			appendix = ""
		}
		
		return "${config.mod.version}.${config.mod.buildnumber}${appendix}"
	}
}
