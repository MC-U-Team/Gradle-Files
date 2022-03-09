package info.u_team.gradle_files_plugin.task

import org.gradle.api.tasks.TaskAction

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.GitUtil

class UpdateGitBuildNumberTask extends ReleaseTask {
	
	@TaskAction
	void update() {
		final def gitRepo = new File(project.buildDir, Constants.GIT_REPOSITORY_NAME)
		final def versioningBranch = Constants.VERSIONING_BRANCH
		final def config = project.extensions.extraProperties.config
		final def buildNumber = config.mod.buildnumber
		
		// Fetch upstream
		GitUtil.fetch(project, gitRepo)
		
		// Checkout versioning branch
		GitUtil.executeGitCommandException(project, gitRepo, "checkout", "-f", versioningBranch)
		
		// Update patch file, commit and push.
		new File(gitRepo, Constants.PATCH_FILE).write(buildNumber as String, "UTF-8")
		GitUtil.alwaysCommit(project, gitRepo, Constants.PATCH_FILE) { "Updated build number to ${buildNumber}" }
		GitUtil.push(project, gitRepo, versioningBranch)
		
		project.logger.quiet("Updated build number to ${(buildNumber as Integer)+1}")
	}
}
