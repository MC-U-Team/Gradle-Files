package info.u_team.gradle_files_plugin.tool

import org.gradle.api.GradleException

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.GitUtil

class FetchGitBuildNumber {
	
	static void fetch(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def config = project.extensions.extraProperties.config
		
		def buildNumber = "DEV"
		
		if(project.hasProperty(Constants.BUILD_PROPERTY)) {
			final def gitRepo = new File(project.buildDir, Constants.GIT_REPOSITORY_NAME)
			gitRepo.mkdirs()
			
			final def repositoryPath = GitUtil.repositoryPath(project)
			
			if(!gitRepo.canonicalPath.equals(repositoryPath)) {
				// Create repository from root repository
				
				println GitUtil.executeGitCommandException(project, "init")
			}
			
			
			
			final def versioningBranch = Constants.VERSIONING_BRANCH
			
			//			// Get main branch and check if branch match expected one
			//			final def expectedBranch = config.github.branch
			//			final def mainBranch = GitUtil.executeGitCommandException(project, "rev-parse", "--abbrev-ref", "HEAD")
			//
			//			if(expectedBranch != mainBranch) {
			//				throw new GradleException("Expected git branch to be ${expectedBranch} but its ${mainBranch}")
			//			}
			//
			//			project.logger.quiet("Fetching build number for branch {} with git {} branch", mainBranch, versioningBranch)
			//
			//			// Commit changes on main branch
			//			final def changed = GitUtil.commit(project, ".", mainBranch)
			//
			//			// Fetch upstream
			//			GitUtil.fetch(project)
			//
			//			project.logger.quiet("Check if branch {} already exists", versioningBranch)
			//
			//			final def branchExists = GitUtil.branchExists(project, versioningBranch)
			//
			//			if(branchExists) {
			//				project.logger.quiet("Branch exists. Try to read patch file")
			//
			//				// Checkout existing versioning branch
			//				GitUtil.executeGitCommandException(project, "checkout", "-f", versioningBranch)
			//
			//				// Read patch number and increment
			//				final def patchNumber = (project.file(Constants.PATCH_FILE).text as Integer) + 1
			//				buildNumber = patchNumber as String
			//			} else {
			//				project.logger.quiet("Branch does not exist. Create new branch and patch file starting at build number 1")
			//
			//				// Create new orphan branch and remove all files
			//				GitUtil.executeGitCommandException(project, "checkout", "--orphan", versioningBranch)
			//				GitUtil.executeGitCommandException(project, "rm", "--cache", "-r", "-f", "*")
			//
			//				// Create patch file, commit and push. Set incremented patch number as start value
			//				project.file(Constants.PATCH_FILE).write("0", "UTF-8")
			//				GitUtil.alwaysCommit(project, Constants.PATCH_FILE) { "Created ${versioningBranch} branch with build number 1" }
			//				GitUtil.push(project, versioningBranch)
			//				buildNumber = 1
			//			}
			//
			//			// Checkout main branch
			//			GitUtil.executeGitCommandException(project, "checkout", "-f", mainBranch)
			//
			//			// Push possible changes on main branch
			//			if(changed) {
			//				GitUtil.pullAndPush(project, mainBranch)
			//			}
		} else {
			project.logger.quiet("Using dev as build number")
		}
		
		project.logger.quiet("Finished fetching build number")
		project.logger.quiet("The current buildnumber is {}", buildNumber)
		
		config.mod.buildnumber = buildNumber
	}
}
