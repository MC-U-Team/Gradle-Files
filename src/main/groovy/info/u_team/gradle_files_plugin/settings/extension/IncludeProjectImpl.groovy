package info.u_team.gradle_files_plugin.settings.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.initialization.Settings

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.NamingUtil

@CompileStatic
class IncludeProjectImpl {

	static def includeProject(final Settings settings, final String subProject) {
		settings.include(subProject)

		final def project = settings.project(":${subProject}")
		project.name = NamingUtil.subProjectName(settings.rootProject.name, project.name)
		
		return project;
	}
}
