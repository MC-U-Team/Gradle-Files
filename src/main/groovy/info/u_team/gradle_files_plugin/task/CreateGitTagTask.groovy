package info.u_team.gradle_files_plugin.task

import org.gradle.api.tasks.TaskAction

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.GitUtil

class CreateGitTagTask extends ReleaseTask {
	
	CreateGitTagTask() {
		dependsOn(Constants.CHECK_GIT_TASK)
	}
	
	@TaskAction
	void create() {
		final def config = project.extensions.extraProperties.config
		final def tagName = "${config.minecraft.version}-${project.version}"
		
		GitUtil.executeGitCommandException(project, project.rootDir, "tag", tagName)
		GitUtil.executeGitCommandException(project, project.rootDir, "push", "origin", tagName)
		
		project.logger.quiet("Created release tag ${tagName}")
	}
}
