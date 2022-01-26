package info.u_team.gradle_files_plugin.util

import org.gradle.api.GradleException
import org.gradle.api.Project

import info.u_team.gradle_files_plugin.Constants

class GitUtil {
	
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
			executeGitCommandException(project, "add", files)
			executeGitCommandException(project, "commit", "-m", closure.call())
			return true
		}
		return false
	}
	
	static void push(final Project project, final def branch) {
		executeGitCommandException(project, "push", "-u", "origin", branch)
	}
	
	static String executeGitCommandException(final Project project, final String... args) {
		final def (success, output) = executeGitCommand(project, args)
		
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
