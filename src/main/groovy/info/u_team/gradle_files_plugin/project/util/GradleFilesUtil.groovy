package info.u_team.gradle_files_plugin.project.util

import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension

import groovy.transform.CompileStatic
import groovy.transform.Memoized
import info.u_team.gradle_files_plugin.project.Constants

@CompileStatic
class GradleFilesUtil {

	static ExtraPropertiesExtension getExtraProperties(final Project project) {
		return project.extensions.extraProperties
	}

	static ConfigObject getProjectConfig(final Project project) {
		return getExtraProperties(project).get("config") as ConfigObject
	}

	/**
	 * Checks if this project is a multi loader project
	 * @param project Project
	 * @return True when this project is a multi loader project
	 */
	@Memoized
	static boolean isMultiLoaderProject(final Project project) {
		return getExtraProperties(project).has(Constants.MULTILOADER_MAIN_PROJECT_SETTING)
	}

	/**
	 * Checks if the supplied project is the main project
	 * @param project Project
	 * @return If supplied project is the main project
	 */
	@Memoized
	static boolean isMainProject(final Project project) {
		return project.is(getMainProject(project))
	}

	/**
	 * If property {@code info.u_team.gradle_files_plugin.Constants#MULTILOADER_MAIN_PROJECT_SETTING} is set
	 * then returns the main project, else return the supplied project
	 * @param project Project
	 * @return Main project
	 */
	@Memoized
	static Project getMainProject(final Project project) {
		final def extraProperties = getExtraProperties(project)

		if(extraProperties.has(Constants.MULTILOADER_MAIN_PROJECT_SETTING)) {
			final String mainProject = extraProperties.get(Constants.MULTILOADER_MAIN_PROJECT_SETTING)
			return project.project(mainProject)
		}
		return project
	}
}
