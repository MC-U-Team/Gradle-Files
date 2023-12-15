package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.project.GradleFilesProjectExtension
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

class ArchiveNameExtensionImpl {

	static def archivesName(final Project project, final GradleFilesProjectExtension extension) {
		final def config = GradleFilesUtil.getProjectConfig(project)

		final def builder = new StringBuilder()
		builder.append(config.mod.filename)
		builder.append("-")
		if(!extension.loaderSuffix.get().blank) {
			builder.append(extension.loaderSuffix.get())
			builder.append("-")
		}
		builder.append(config.minecraft.version)

		return builder.toString()
	}
}
