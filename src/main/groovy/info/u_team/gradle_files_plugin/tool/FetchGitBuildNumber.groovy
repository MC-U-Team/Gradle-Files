package info.u_team.gradle_files_plugin.tool

import org.gradle.api.GradleException

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.GitUtil

class FetchGitBuildNumber {
	
	static void fetch(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		
		if(!project.hasProperty(Constants.BUILD_PROPERTY)) {
			return
		}
		
		// Get main branch and check if branch match expected one
		final def expectedBranch = project.extensions.extraProperties.config.github.branch
		final def mainBranch = GitUtil.executeGitCommandException(project, "rev-parse", "--abbrev-ref", "HEAD")
		
		if(expectedBranch != mainBranch) {
			throw new GradleException("Expected git branch to be ${expectedBranch} but its ${mainBranch}")
		}
		
		project.logger.quiet("Fetching build number for branch {} with git {} branch", mainBranch, Constants.VERSIONING_BRANCH)
		
		// Commit changes on main branch
		def changed = GitUtil.commit(project, ".", mainBranch)
		
		// TODO use git stash!!!
		
		if(changed) {
			GitUtil.push(project, mainBranch)
		}
	}
}
