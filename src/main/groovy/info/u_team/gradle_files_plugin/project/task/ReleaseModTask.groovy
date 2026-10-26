package info.u_team.gradle_files_plugin.project.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants

@CompileStatic
@DisableCachingByDefault(because = "Lifecycle task")
abstract class ReleaseModTask extends DefaultTask {

	ReleaseModTask() {
		final def createBuild = project.hasProperty(Constants.BUILD_PROPERTY)
		onlyIf {
			createBuild
		}
	}

	@TaskAction
	void create() {
		logger.quiet("Released mod")
	}
}
