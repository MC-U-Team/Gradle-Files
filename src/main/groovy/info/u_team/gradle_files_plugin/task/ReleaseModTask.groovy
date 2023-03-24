package info.u_team.gradle_files_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import info.u_team.gradle_files_plugin.Constants

class ReleaseModTask extends DefaultTask {
	
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
