package info.u_team.gradle_files_plugin.settings.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.initialization.Settings

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants

@CompileStatic
class SetupProjectImpl {

	static void setupProject(final Settings settings, final String rootName, final String ... subProjects) {
		settings.rootProject.name = rootName

		subProjects.each { subProject ->
			settings.include(subProject)

			settings.project(":${subProject}").name = "${rootName}_${StringUtils.capitalize(subProject)}"
		}

		if(subProjects.length > 0) {
			settings.gradle.allprojects { project ->
				project.extensions.extraProperties.set(Constants.MULTILOADER_MAIN_PROJECT_SETTING, settings.gradle.rootProject.path)
			}
		}
	}
}
