package info.u_team.gradle_files_plugin.util

import java.nio.charset.StandardCharsets

import org.gradle.api.Project
import org.gradle.process.ExecSpec
import org.gradle.process.internal.ExecException

import com.google.common.base.Charsets

class GitUtil {
	
	static def executeGitCommand(final Project project, final String... args) {
		return executeCommand(project, "git", args)
	}
	
	static def executeCommand(final Project project, final String executable, final String... args) {
		try {
			def output = new ByteArrayOutputStream(), error = new ByteArrayOutputStream()
			
			final def exitValue = project.exec { ExecSpec spec ->
				spec.executable(executable)
				spec.args(args)
				spec.setErrorOutput(error)
				spec.setStandardOutput(output)
				spec.ignoreExitValue = true
			}.exitValue
			
			def returnString = output.toString(StandardCharsets.UTF_8.name())
			if(exitValue != 0) {
				returnString += error.toString(StandardCharsets.UTF_8.name())
			}
			return [exitValue == 0, returnString]
		} catch(final ExecException ex) {
			return [false, ex.message]
		}
	}
}
