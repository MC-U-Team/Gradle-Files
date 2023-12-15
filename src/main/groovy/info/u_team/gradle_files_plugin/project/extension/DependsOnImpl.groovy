package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.util.DependencyUtil

@CompileStatic
class DependsOnImpl {

	static void assembleDependOn(final Project project, final Object... dependTask) {
		DependencyUtil.assembleDependOn(project, dependTask)
	}

	static void allPublishingDependOn(final Project project, final Object... dependTask) {
		DependencyUtil.allPublishingDependOn(project, dependTask)
	}

	static void allUploadDependOn(final Project project, final Object... dependTask) {
		DependencyUtil.allUploadDependOn(project, dependTask)
	}

	static void allBuildingDependOn(final Project project, final Object... dependTask) {
		DependencyUtil.allBuildingDependOn(project, dependTask)
	}
}
