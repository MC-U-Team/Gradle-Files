package info.u_team.gradle_files_plugin.tool

import org.gradle.api.GradleException

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.GitUtil

class FetchGitBuilderNumber {
	
	static void fetch(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		
		if(!project.hasProperty(Constants.BUILD_PROPERTY)) {
			return
		}
		
		// Check for git repository with git status
		GitUtil.executeGitCommandException(plugin.project, "status")
	}
}
