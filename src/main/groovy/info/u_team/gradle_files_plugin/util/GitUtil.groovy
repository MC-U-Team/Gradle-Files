package info.u_team.gradle_files_plugin.util

import org.gradle.api.GradleException
import org.gradle.api.Project

class GitUtil {
	
	static def executeGitCommandException(final Project project, final String... args) {
		final def (success, output) = executeGitCommand(project, "status")
		
		if(!success) {
			throw new GradleException("Cannot execute git because: ${output}")
		}
		
		return output
	}
	
	static def executeGitCommand(final Project project, final String... args) {
		return ExecuteUtil.executeCommand(project, "git", args)
	}
}
