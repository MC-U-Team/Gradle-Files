package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.GradleFilesExtension
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class ArchiveBaseNameExtensionImpl {
	
	static def archivesBaseName(final Project project, final GradleFilesExtension extension) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		final def builder = new StringBuilder()
		builder.append(config.mod.filename)
		builder.append("-")
		if(!extension.loaderSuffix.blank) {
			builder.append(extension.loaderSuffix)
			builder.append("-")
		}
		builder.append(config.minecraft.version)
		
		return builder.toString()
	}
}
