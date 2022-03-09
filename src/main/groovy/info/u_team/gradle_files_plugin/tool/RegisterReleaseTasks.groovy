package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.task.CheckGitTask
import info.u_team.gradle_files_plugin.task.CreateGitTagTask
import info.u_team.gradle_files_plugin.task.ReleaseModTask
import info.u_team.gradle_files_plugin.task.UpdateGitBuildNumberTask

class RegisterReleaseTasks {
	
	static void register(final GradleFilesPlugin plugin) {
		final def tasks = plugin.project.tasks
		
		// Register updateBuildNumber task
		tasks.register(Constants.UPDATE_BUILD_NUMBER_TASK, UpdateGitBuildNumberTask)
		
		// Register checkGit task
		tasks.register(Constants.CHECK_GIT_TASK, CheckGitTask)
		
		// Register createGitTag task
		tasks.register(Constants.CREATE_GIT_TAG_TASK, CreateGitTagTask)
		
		// Register releaseMod task
		tasks.register(Constants.RELEASE_MOD_TASK, ReleaseModTask).configure { task ->
			task.group = "release"
		}
	}
}
