package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.extension.VersionExtensionImpl
import info.u_team.gradle_files_plugin.task.UpdateGitBuildNumberTask
import info.u_team.gradle_files_plugin.util.GitUtil

class FetchGitBuildNumber {
	
	static void fetch(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def config = project.extensions.extraProperties.config
		
		def buildNumber = "DEV"
		
		if(project.hasProperty(Constants.BUILD_PROPERTY)) {
			final def gitRepo = new File(project.buildDir, Constants.GIT_REPOSITORY_NAME)
			gitRepo.mkdirs()
			
			final def repositoryPath = GitUtil.repositoryPath(project, gitRepo)
			
			if(!gitRepo.canonicalPath.equals(repositoryPath)) {
				project.logger.quiet("Create local git repository here {}", gitRepo)
				
				// Create repository
				GitUtil.executeGitCommandException(project, gitRepo, "init")
				
				// Get remote from main repository
				final def url = GitUtil.executeGitCommandException(project, project.rootDir, "remote", "get-url", "origin")
				
				// Set url in new repository
				GitUtil.executeGitCommandException(project, gitRepo, "remote", "add", "origin", url)
			}
			
			final def versioningBranch = Constants.VERSIONING_BRANCH
			
			// Fetch upstream
			GitUtil.fetch(project, gitRepo)
			
			project.logger.quiet("Check if branch {} already exists", versioningBranch)
			
			final def branchExists = GitUtil.branchExists(project, gitRepo, versioningBranch)
			if(branchExists) {
				project.logger.quiet("Branch exists. Try to read patch file")
				
				// Checkout existing versioning branch
				GitUtil.executeGitCommandException(project, gitRepo, "checkout", "-f", versioningBranch)
				
				// Pull with merge existing
				GitUtil.pull(project, gitRepo)
				
				// Read patch number and increment
				final def patchNumber = (new File(gitRepo, Constants.PATCH_FILE).text as Integer) + 1
				buildNumber = patchNumber as String
			} else {
				project.logger.quiet("Branch does not exist. Create new branch and patch file starting at build number 1")
				
				// Create new orphan branch
				GitUtil.executeGitCommandException(project, gitRepo, "checkout", "--orphan", versioningBranch)
				
				// Create patch file, commit and push. Set incremented patch number as start value
				new File(gitRepo, Constants.PATCH_FILE).write("0", "UTF-8")
				GitUtil.alwaysCommit(project, gitRepo, Constants.PATCH_FILE) { "Created ${versioningBranch} branch with build number 1" }
				GitUtil.push(project, gitRepo, versioningBranch)
				buildNumber = 1 as String
			}
		} else {
			project.logger.quiet("Using dev as build number")
		}
		
		project.logger.quiet("Finished fetching build number")
		project.logger.quiet("The current buildnumber is {}", buildNumber)
		
		config.mod.buildnumber = buildNumber
		
		project.logger.quiet("Buildversion is {}", VersionExtensionImpl.version())
	}
}
