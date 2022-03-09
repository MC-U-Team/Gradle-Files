package info.u_team.gradle_files_plugin.task

import org.gradle.api.tasks.TaskAction

import info.u_team.gradle_files_plugin.Constants

class ReleaseModTask extends ReleaseTask {
	
	ReleaseModTask() {
		dependsOn("publish")
		dependsOn("curseforge")
		dependsOn(Constants.CREATE_GIT_TAG_TASK)
		dependsOn(Constants.UPDATE_BUILD_NUMBER_TASK)
	}
	
	@TaskAction
	void create() {
		project.logger.quiet("Released mod")
	}
}
