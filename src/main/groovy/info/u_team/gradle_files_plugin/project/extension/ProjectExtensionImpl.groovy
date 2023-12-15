package info.u_team.gradle_files_plugin.project.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project

class ProjectExtensionImpl {

	static Project project(final Project project, final String name) {
		project.project(":${project.rootProject.name}_${StringUtils.capitalize(name)}")
	}
}
