package info.u_team.gradle_files_plugin.project.task

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import info.u_team.gradle_files_plugin.project.Constants
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

abstract class CreateGitTagTask extends DefaultTask {

	CreateGitTagTask() {
		onlyIf {
			project.hasProperty(Constants.BUILD_PROPERTY)
		}
	}

	@TaskAction
	void create() {
		final def config = GradleFilesUtil.getProjectConfig(project)
		final def tagName = "${config.minecraft.version}-${project.version}"

		GitUtil.executeGitCommandException(project, project.rootDir, "tag", tagName)
		GitUtil.executeGitCommandException(project, project.rootDir, "push", "origin", tagName)

		project.logger.quiet("Created release tag ${tagName}")
	}
}
