package info.u_team.gradle_files_plugin.util

import org.gradle.api.Project

import groovy.transform.CompileStatic
import groovy.transform.Memoized

@CompileStatic
class GradleFilesUtil {
	
	static def getProjectConfig(final Project project) {
		return project.extensions.extraProperties.get("config")
	}
	
	@Memoized
	static boolean isRootProject(final Project project) {
		return project.is(getRootProject(project))
	}
	
	@Memoized
	static Project getRootProject(final Project project) {
		def rootProject = project.rootProject
		while(!rootProject.is(rootProject.rootProject)) {
			rootProject = rootProject.rootProject
		}
		return rootProject
	}
}
