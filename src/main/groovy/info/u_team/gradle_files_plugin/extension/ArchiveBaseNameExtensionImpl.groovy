package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class ArchiveBaseNameExtensionImpl {
	
	static def archivesBaseName(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		return "${config.mod.filename}-${config.minecraft.version}"
	}
}
