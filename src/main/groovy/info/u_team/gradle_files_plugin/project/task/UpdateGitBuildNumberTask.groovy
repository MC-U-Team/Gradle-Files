package info.u_team.gradle_files_plugin.project.task

import javax.inject.Inject

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import org.gradle.work.DisableCachingByDefault

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.ExecuteUtil
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@CompileStatic
@DisableCachingByDefault(because = "Interacts with git")
abstract class UpdateGitBuildNumberTask extends DefaultTask {

	@CompileDynamic
	UpdateGitBuildNumberTask() {
		final def createBuild = project.hasProperty(Constants.BUILD_PROPERTY)
		onlyIf {
			createBuild
		}

		final def config = GradleFilesUtil.getProjectConfig(project)
		buildNumber.convention(project.provider { config.mod.buildnumber as String })
		gitRepository.convention(project.layout.buildDirectory.dir(Constants.GIT_REPOSITORY_NAME))
	}

	@Input
	abstract Property<String> getBuildNumber()

	@Internal
	abstract DirectoryProperty getGitRepository()

	@Inject
	abstract ExecOperations getExecOperations()

	@TaskAction
	void update() {
		final def executor = ExecuteUtil.fromExecOperations(logger, execOperations)
		final def gitRepo = gitRepository.get().asFile
		final def versioningBranch = Constants.VERSIONING_BRANCH
		final def buildNumber = getBuildNumber().get()

		// Fetch upstream
		GitUtil.fetch(executor, gitRepo)

		// Checkout versioning branch
		GitUtil.executeGitCommandException(executor, gitRepo, "checkout", "-f", versioningBranch)

		// Update patch file, commit and push.
		new File(gitRepo, Constants.PATCH_FILE).write(buildNumber, "UTF-8")
		GitUtil.alwaysCommit(executor, gitRepo, Constants.PATCH_FILE) { "Updated build number to ${buildNumber}" }
		GitUtil.push(executor, gitRepo, versioningBranch)

		logger.quiet("Updated build number to ${(buildNumber as Integer)+1}")
	}
}
