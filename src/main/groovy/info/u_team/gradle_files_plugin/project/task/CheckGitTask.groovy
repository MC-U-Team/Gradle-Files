package info.u_team.gradle_files_plugin.project.task

import javax.inject.Inject

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import org.gradle.work.DisableCachingByDefault

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.ExecuteUtil
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@DisableCachingByDefault(because = "Interacts with git")
abstract class CheckGitTask extends DefaultTask {

	CheckGitTask() {
		onlyIf {
			project.hasProperty(Constants.BUILD_PROPERTY)
		}
	}

	@Inject
	abstract ExecOperations getExecOperations()

	@TaskAction
	void create() {
		final def executor = ExecuteUtil.fromExecOperations(logger, execOperations)
		final def config = GradleFilesUtil.getProjectConfig(project)

		// Get main branch and check if branch match expected one
		final def expectedBranch = config.github.branch
		final def mainBranch = GitUtil.executeGitCommandException(executor, project.rootDir, "rev-parse", "--abbrev-ref", "HEAD")

		if(expectedBranch != mainBranch) {
			throw new GradleException("Expected git branch to be ${expectedBranch} but its ${mainBranch}")
		}

		// Commit changes if there are some
		if(GitUtil.commit(executor, project.rootDir, ".", mainBranch, project.hasProperty(Constants.HEADLESS_BUILD_PROPERTY))) {
			project.logger.quiet("Committed to branch ${mainBranch}")
		}

		// Pull push all changes
		GitUtil.pullAndPush(executor, project.rootDir, mainBranch)
	}
}
