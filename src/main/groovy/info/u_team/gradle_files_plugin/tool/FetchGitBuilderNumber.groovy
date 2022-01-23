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
		GitUtil.executeGitCommandException(project, "status")
		
		final def mainBranch = GitUtil.executeGitCommandException(project, "rev-parse", "--abbrev-ref", "HEAD")
		
		println "____________________________________________________________________________________________"
		println mainBranch
	}
}
