package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.NamingUtil

@CompileStatic
class ProjectExtensionImpl {

	static Project project(final Project project, final String name) {
		project.project(":${NamingUtil.subProjectName(project.rootProject.name, name)}")
	}
}
