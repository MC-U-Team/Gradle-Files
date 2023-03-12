package info.u_team.gradle_files_plugin.util

import org.gradle.api.Project

import groovy.transform.Memoized
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesUtil {
	
	static def getProjectConfig(Project project) {
		return project.extensions.extraProperties.config
	}
	
	// TODO move
	static boolean isRootProject() {
		final def project = GradleFilesPlugin.instance.project
		return project.rootProject.is(project)
	}
	
	static Project getRootProject() {
		final def project = GradleFilesPlugin.instance.project
		def rootProject = project.rootProject
		while(!rootProject.is(rootProject.rootProject)) {
			rootProject = rootProject.rootProject
		}
		return rootProject
	}
}
