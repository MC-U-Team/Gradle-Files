package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

class VersionExtensionImpl {

	static def version(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)

		final def appendix
		if(Boolean.valueOf(config.mod.snapshot)) {
			appendix = "-SNAPSHOT"
		} else {
			appendix = ""
		}

		return "${config.mod.version}.${config.mod.buildnumber}${appendix}"
	}
}
