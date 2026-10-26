package info.u_team.gradle_files_plugin.project.task

import javax.inject.Inject

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.ExecuteUtil
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

abstract class CreateGitTagTask extends DefaultTask {

	CreateGitTagTask() {
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
		final def tagName = "${config.minecraft.version}-${project.version}"

		GitUtil.executeGitCommandException(executor, project.rootDir, "tag", "-a", tagName, "-m", tagName)
		GitUtil.executeGitCommandException(executor, project.rootDir, "push", "origin", tagName)

		project.logger.quiet("Created release tag ${tagName}")
	}
}
