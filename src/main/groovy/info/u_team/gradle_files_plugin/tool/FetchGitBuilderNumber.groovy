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
		
		final def (success, output) = GitUtil.executeGitCommand(plugin.project, "help")
		
		if(!success) {
			throw new GradleException("Cannot execute git because: ${output}")
		}
	}
}
