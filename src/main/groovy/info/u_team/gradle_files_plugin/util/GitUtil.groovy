package info.u_team.gradle_files_plugin.util

import org.gradle.api.GradleException
import org.gradle.api.Project

class GitUtil {
	
	static def executeGitCommandException(final Project project, final String... args) {
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
