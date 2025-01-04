package info.u_team.gradle_files_plugin.settings

import org.gradle.api.initialization.Settings

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.settings.extension.IncludeProjectImpl
import info.u_team.gradle_files_plugin.settings.extension.SetupProjectImpl

@CompileStatic
abstract class GradleFilesSettingsExtension {

	private final Settings settings

	GradleFilesSettingsExtension(final Settings settings) {
		this.settings = settings;
	}

	/**
	 * Include subprojects and setup for multiloader project when subprojects are supplied
	 * @param rootName The name of the root project
	 * @param subProjects The projects to be included
	 */
	void setupProject(final String rootName, final String ... subProjects) {
		SetupProjectImpl.setupProject(settings, rootName, subProjects)
	}
	
	/**
	 * Include a subproject
	 * @param subProject The project to be included
	 */
	def includeProject(final String subProject) {
		IncludeProjectImpl.includeProject(settings, subProject)
	}
}
