package info.u_team.gradle_files_plugin.project.util

import org.gradle.api.GradleException

import info.u_team.gradle_files_plugin.Constants

class GitUtil {

	static String repositoryPath(final ExecuteUtil executor, final def workingDir) {
		return new File(executeGitCommandException(executor, workingDir, "rev-parse", "--show-toplevel")).canonicalPath
	}

	static def branchExists(final ExecuteUtil executor, final def workingDir, final def branch) {
		if(!localBranchExists(executor, workingDir, branch)) {
			return remoteBranchExists(executor, workingDir, branch)
		}
		return true
	}

	static def localBranchExists(final ExecuteUtil executor, final def workingDir, final def branch) {
		final def (success, output) = executeGitCommand(executor, workingDir, "show-ref", "--verify", "refs/heads/${branch}")
		return success
	}

	static def remoteBranchExists(final ExecuteUtil executor, final def workingDir, final def branch) {
		final def (success, output) = executeGitCommand(executor, workingDir, "show-ref", "--verify", "refs/remotes/origin/${branch}")
		return success
	}

	static def commit(final ExecuteUtil executor, final def workingDir, final String files, final def branch, final boolean headless) {
		return commit(executor, workingDir, files) {
			if(headless) {
				return Constants.COMMIT_FORCE_MESSAGE
			} else {
				executor.logger.quiet("> Enter commit message for branch {}", branch)
				return System.in.newReader().readLine()
			}
		}
	}

	static def commit(final ExecuteUtil executor, final def workingDir, final String files, Closure closure) {
		if(!executeGitCommandException(executor, workingDir, "status", "--porcelain").empty) {
			alwaysCommit(executor, workingDir, files, closure)
			return true
		}
		return false
	}

	static def alwaysCommit(final ExecuteUtil executor, final def workingDir, final String files, Closure closure) {
		executeGitCommandException(executor, workingDir, "add", files)
		executeGitCommandException(executor, workingDir, "commit", "-m", closure.call())
	}

	static void setOrigin(final ExecuteUtil executor, final def workingDir, final String url) {
		final def (success, output) = executeGitCommand(executor, workingDir, "remote", "get-url", "origin")
		if(!success) {
			executeGitCommandException(executor, workingDir, "remote", "add", "origin", url)
		} else if(output != url) {
			executeGitCommandException(executor, workingDir, "remote", "set-url", "origin", url)
		}
	}

	static void fetch(final ExecuteUtil executor, final def workingDir) {
		executeGitCommandException(executor, workingDir, "fetch")
	}

	static void pullAndPush(final ExecuteUtil executor, final def workingDir, final def branch) {
		pull(executor, workingDir)
		push(executor, workingDir, branch)
	}

	static void pull(final ExecuteUtil executor, final def workingDir) {
		executeGitCommandException(executor, workingDir, "pull")
	}

	static void push(final ExecuteUtil executor, final def workingDir, final def branch) {
		executeGitCommandException(executor, workingDir, "push", "-u", "origin", branch)
	}

	static String executeGitCommandException(final ExecuteUtil executor, final def workingDir, final String... args) {
		final def (success, output) = executeGitCommand(executor, workingDir, args)

		if(!success) {
			throw new GradleException("Cannot execute git because: ${output}")
		}

		return output
	}

	static def executeGitCommand(final ExecuteUtil executor, final def workingDir, final String... args) {
		final def (success, output) = executor.executeCommand(workingDir, "git", args)
		return [success, output.trim()]
	}
}
