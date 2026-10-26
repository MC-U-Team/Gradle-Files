package info.u_team.gradle_files_plugin.project.task

import javax.inject.Inject

import org.gradle.api.DefaultTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.ExecuteUtil
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@CompileStatic
abstract class UpdateGitBuildNumberTask extends DefaultTask {

	UpdateGitBuildNumberTask() {
		onlyIf {
			project.hasProperty(Constants.BUILD_PROPERTY)
		}
	}

	@Inject
	abstract ExecOperations getExecOperations()

	@TaskAction
	void update() {
		final def executor = ExecuteUtil.fromExecOperations(logger, execOperations)
		final def gitRepo = new File(project.layout.buildDirectory.asFile.get(), Constants.GIT_REPOSITORY_NAME)
		final def versioningBranch = Constants.VERSIONING_BRANCH
		final def buildNumber = getBuildNumber(project)

		// Fetch upstream
		GitUtil.fetch(executor, gitRepo)

		// Checkout versioning branch
		GitUtil.executeGitCommandException(executor, gitRepo, "checkout", "-f", versioningBranch)

		// Update patch file, commit and push.
		new File(gitRepo, Constants.PATCH_FILE).write(buildNumber as String, "UTF-8")
		GitUtil.alwaysCommit(executor, gitRepo, Constants.PATCH_FILE) { "Updated build number to ${buildNumber}" }
		GitUtil.push(executor, gitRepo, versioningBranch)

		project.logger.quiet("Updated build number to ${(buildNumber as Integer)+1}")
	}

	@CompileDynamic
	private String getBuildNumber(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		return config.mod.buildnumber
	}
}
