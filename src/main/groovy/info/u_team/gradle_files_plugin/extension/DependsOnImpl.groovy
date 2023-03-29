package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.DependencyUtil

@CompileStatic
class DependsOnImpl {
	
	static def assembleDependOn(final Project project, final Object... dependTask) {
		DependencyUtil.assembleDependOn(project, dependTask)
	}
	
	static def allPublishingDependOn(final Project project, final Object... dependTask) {
		DependencyUtil.allPublishingDependOn(project, dependTask)
	}
	
	static def allUploadDependOn(final Project project, final Object... dependTask) {
		DependencyUtil.allUploadDependOn(project, dependTask)
	}
}
