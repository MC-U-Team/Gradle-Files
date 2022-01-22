package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class ArchiveBaseNameExtensionImpl {
	
	static def archivesBaseName() {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		return "${config.mod.filename}-${config.forge.mcversion}"
	}
}
