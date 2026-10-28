package info.u_team.gradle_files_plugin.project.task

import javax.inject.Inject

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import org.gradle.work.DisableCachingByDefault

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.ExecuteUtil
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@DisableCachingByDefault(because = "Interacts with git")
abstract class CheckGitTask extends DefaultTask {

	CheckGitTask() {
		final def createBuild = project.hasProperty(Constants.BUILD_PROPERTY)
		onlyIf {
			createBuild
		}

		final def config = GradleFilesUtil.getProjectConfig(project)
		expectedBranch.convention(project.provider { config.github.branch as String })
		headless.convention(project.hasProperty(Constants.HEADLESS_BUILD_PROPERTY))
		final def rootDir = project.rootDir
		rootProjectDirectory.convention(project.layout.dir(project.provider { rootDir }))
	}

	@Input
	abstract Property<String> getExpectedBranch()

	@Input
	abstract Property<Boolean> getHeadless()

	@Internal
	abstract DirectoryProperty getRootProjectDirectory()

	@Inject
	abstract ExecOperations getExecOperations()

	@TaskAction
	void create() {
		final def executor = ExecuteUtil.fromExecOperations(logger, execOperations)
		final def rootDir = rootProjectDirectory.get().asFile

		// Get main branch and check if branch match expected one
		final def mainBranch = GitUtil.executeGitCommandException(executor, rootDir, "rev-parse", "--abbrev-ref", "HEAD")

		if(expectedBranch.get() != mainBranch) {
			throw new GradleException("Expected git branch to be ${expectedBranch.get()} but its ${mainBranch}")
		}

		// Commit changes if there are some
		if(GitUtil.commit(executor, rootDir, ".", mainBranch, headless.get())) {
			logger.quiet("Committed to branch ${mainBranch}")
		}

		// Pull push all changes
		GitUtil.pullAndPush(executor, rootDir, mainBranch)
	}
}
