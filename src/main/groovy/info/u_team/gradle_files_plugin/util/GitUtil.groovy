package info.u_team.gradle_files_plugin.util

import org.gradle.api.GradleException
import org.gradle.api.Project

import info.u_team.gradle_files_plugin.Constants

class GitUtil {
	
	static def branchExists(final Project project, final def branch) {
		if(!localBranchExists(project, branch)) {
			return remoteBranchExists(project, branch)
		}
		return true
	}
	
	static def localBranchExists(final Project project, final def branch) {
		final def (success, output) = executeGitCommand(project, "show-ref", "--verify", "refs/heads/${branch}")
		return success
	}
	
	static def remoteBranchExists(final Project project, final def branch) {
		final def (success, output) = executeGitCommand(project, "show-ref", "--verify", "refs/remotes/origin/${branch}")
		return success
	}
	
	static def commit(final Project project, final String files, final def branch) {
		return commit(project, files) {
			if(project.hasProperty(Constants.HEADLESS_BUILD_PROPERTY)) {
				return Constants.COMMIT_FORCE_MESSAGE
			} else {
				project.logger.quiet("> Enter commit message for branch {}", branch)
				return System.in.newReader().readLine()
			}
		}
	}
	
	static def commit(final Project project, final String files, Closure closure) {
		if(!executeGitCommandException(project, "status", "--porcelain").empty) {
			alwaysCommit(project, files, closure)
			return true
		}
		return false
	}
	
	static def alwaysCommit(final Project project, final String files, Closure closure) {
		executeGitCommandException(project, "add", files)
		executeGitCommandException(project, "commit", "-m", closure.call())
	}
	
	static void fetch(final Project project) {
		executeGitCommandException(project, "fetch")
	}
	
	static void pullAndPush(final Project project, final def branch) {
		pull(project)
		push(project, branch)
	}
	
	static void pull(final Project project) {
		executeGitCommandException(project, "pull")
	}
	
	static void push(final Project project, final def branch) {
		executeGitCommandException(project, "push", "-u", "origin", branch)
	}
	
	static String repositoryPath(final Project project) {
		return project.file(executeGitCommandException(project, "rev-parse", "--show-toplevel")).canonicalPath
	}
	
	static String executeGitCommandException(final Project project, final String... args) {
		final def (success, output) = executeGitCommand(project, args)
		
		println "HERE: " + success + " -> " + output
		
		if(!success) {
			throw new GradleException("Cannot execute git because: ${output}")
		}
		
		return output
	}
	
	static def executeGitCommand(final Project project, final String... args) {
		final def (success, output) = ExecuteUtil.executeCommand(project, "git", args)
		return [success, output.trim()]
	}
}
