package info.u_team.gradle_files_plugin.project.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants

@CompileStatic
abstract class ReleaseModTask extends DefaultTask {

	ReleaseModTask() {
		onlyIf {
			project.hasProperty(Constants.BUILD_PROPERTY)
		}
	}

	@TaskAction
	void create() {
		project.logger.quiet("Released mod")
	}
}
