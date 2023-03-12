package info.u_team.gradle_files_plugin.util

import org.gradle.api.Project

import groovy.transform.CompileStatic

@CompileStatic
class GradleFilesUtil {
	
	static def getProjectConfig(final Project project) {
		return project.extensions.extraProperties.get("config")
	}
	
	// TODO move
	static boolean isRootProject(final Project project) {
		return project.rootProject.is(project)
	}
	
	static Project getRootProject(final Project project) {
		def rootProject = project.rootProject
		while(!rootProject.is(rootProject.rootProject)) {
			rootProject = rootProject.rootProject
		}
		return rootProject
	}
}
