package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

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
