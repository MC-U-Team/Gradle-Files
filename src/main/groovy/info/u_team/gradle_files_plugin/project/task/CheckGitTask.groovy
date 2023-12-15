package info.u_team.gradle_files_plugin.project.task

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.Constants
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

abstract class CheckGitTask extends DefaultTask {

	CheckGitTask() {
		onlyIf {
			project.hasProperty(Constants.BUILD_PROPERTY)
		}
	}

	@TaskAction
	void create() {
		final def config = GradleFilesUtil.getProjectConfig(project)

		// Get main branch and check if branch match expected one
		final def expectedBranch = config.github.branch
		final def mainBranch = GitUtil.executeGitCommandException(project, project.rootDir, "rev-parse", "--abbrev-ref", "HEAD")

		if(expectedBranch != mainBranch) {
			throw new GradleException("Expected git branch to be ${expectedBranch} but its ${mainBranch}")
		}

		// Commit changes if there are some
		if(GitUtil.commit(project, project.rootDir, ".", mainBranch)) {
			project.logger.quiet("Committed to branch ${mainBranch}")
		}

		// Pull push all changes
		GitUtil.pullAndPush(project, project.rootDir, mainBranch)
	}
}
