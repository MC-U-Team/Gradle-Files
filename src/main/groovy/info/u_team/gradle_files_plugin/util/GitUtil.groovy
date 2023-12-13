package info.u_team.gradle_files_plugin.util

import org.gradle.api.GradleException
import org.gradle.api.Project

import info.u_team.gradle_files_plugin.Constants

class GitUtil {

	static String repositoryPath(final Project project, final def workingDir) {
		return project.file(executeGitCommandException(project, workingDir, "rev-parse", "--show-toplevel")).canonicalPath
	}

	static def branchExists(final Project project, final def workingDir, final def branch) {
		if(!localBranchExists(project, workingDir, branch)) {
			return remoteBranchExists(project, workingDir, branch)
		}
		return true
	}

	static def localBranchExists(final Project project, final def workingDir, final def branch) {
		final def (success, output) = executeGitCommand(project, workingDir, "show-ref", "--verify", "refs/heads/${branch}")
		return success
	}

	static def remoteBranchExists(final Project project, final def workingDir, final def branch) {
		final def (success, output) = executeGitCommand(project, workingDir, "show-ref", "--verify", "refs/remotes/origin/${branch}")
		return success
	}

	static def commit(final Project project, final def workingDir, final String files, final def branch) {
		return commit(project, workingDir, files) {
			if(project.hasProperty(Constants.HEADLESS_BUILD_PROPERTY)) {
				return Constants.COMMIT_FORCE_MESSAGE
			} else {
				project.logger.quiet("> Enter commit message for branch {}", branch)
				return System.in.newReader().readLine()
			}
		}
	}

	static def commit(final Project project, final def workingDir, final String files, Closure closure) {
		if(!executeGitCommandException(project, workingDir, "status", "--porcelain").empty) {
			alwaysCommit(project, workingDir, files, closure)
			return true
		}
		return false
	}

	static def alwaysCommit(final Project project, final def workingDir, final String files, Closure closure) {
		executeGitCommandException(project, workingDir, "add", files)
		executeGitCommandException(project, workingDir, "commit", "-m", closure.call())
	}

	static void fetch(final Project project, final def workingDir) {
		executeGitCommandException(project, workingDir, "fetch")
	}

	static void pullAndPush(final Project project, final def workingDir, final def branch) {
		pull(project, workingDir)
		push(project, workingDir, branch)
	}

	static void pull(final Project project, final def workingDir) {
		executeGitCommandException(project, workingDir, "pull")
	}

	static void push(final Project project, final def workingDir, final def branch) {
		executeGitCommandException(project, workingDir, "push", "-u", "origin", branch)
	}

	static String executeGitCommandException(final Project project, final def workingDir, final String... args) {
		final def (success, output) = executeGitCommand(project, workingDir, args)

		if(!success) {
			throw new GradleException("Cannot execute git because: ${output}")
		}

		return output
	}

	static def executeGitCommand(final Project project, final def workingDir, final String... args) {
		final def (success, output) = ExecuteUtil.executeCommand(project, workingDir, "git", args)
		return [success, output.trim()]
	}
}
