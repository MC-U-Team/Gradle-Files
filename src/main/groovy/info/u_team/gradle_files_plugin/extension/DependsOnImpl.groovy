package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class DependsOnImpl {
	
	static def assembleDependOn(final Object... dependTask) {
		final def (Project project) = GradleFilesUtil.getProjectProperties()
		DependencyUtil.assembleDependOn(project, dependTask)
	}
	
	static def allPublishingDependOn(final Object... dependTask) {
		final def (Project project) = GradleFilesUtil.getProjectProperties()
		DependencyUtil.allPublishingDependOn(project, dependTask)
	}
}
