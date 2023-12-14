package info.u_team.gradle_files_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.GitUtil
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

@CompileStatic
abstract class UpdateGitBuildNumberTask extends DefaultTask {

	UpdateGitBuildNumberTask() {
		onlyIf {
			project.hasProperty(Constants.BUILD_PROPERTY)
		}
	}

	@TaskAction
	void update() {
		final def gitRepo = new File(project.layout.buildDirectory.asFile.get(), Constants.GIT_REPOSITORY_NAME)
		final def versioningBranch = Constants.VERSIONING_BRANCH
		final def buildNumber = getBuildNumber(project)

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

	@CompileDynamic
	private String getBuildNumber(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		return config.mod.buildnumber
	}
}
