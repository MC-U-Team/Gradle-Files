package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.GradleFilesExtension
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class ArchiveBaseNameExtensionImpl {
	
	static def archivesBaseName(final Project project, final GradleFilesExtension extension) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		return "${config.mod.filename}-${extension.loaderSuffix}-${config.minecraft.version}"
	}
}
