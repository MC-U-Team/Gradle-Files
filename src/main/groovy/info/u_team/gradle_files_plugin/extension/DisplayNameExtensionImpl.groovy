package info.u_team.gradle_files_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project

import info.u_team.gradle_files_plugin.GradleFilesExtension
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class DisplayNameExtensionImpl {
	
	static def displayName(final Project project, final GradleFilesExtension extension) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		final def builder = new StringBuilder()
		builder.append(config.mod.name)
		if(!extension.loaderSuffix.blank) {
			builder.append(" ")
			builder.append("(")
			builder.append(StringUtils.capitalize(extension.loaderSuffix))
			builder.append(")")
		}
		
		return builder.toString()
	}
}