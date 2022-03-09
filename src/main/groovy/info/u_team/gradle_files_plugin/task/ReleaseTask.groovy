package info.u_team.gradle_files_plugin.task

import org.gradle.api.DefaultTask

import info.u_team.gradle_files_plugin.Constants

class ReleaseTask extends DefaultTask {
	
	protected ReleaseTask() {
		onlyIf {
			project.hasProperty(Constants.BUILD_PROPERTY)
		}
		mustRunAfter("publish")
		mustRunAfter("curseforge")
	}
}
