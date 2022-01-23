package info.u_team.gradle_files_plugin.util

import java.nio.charset.StandardCharsets

import org.gradle.api.Project
import org.gradle.api.logging.Logger
import org.gradle.process.ExecSpec
import org.gradle.process.internal.ExecException

class ExecuteUtil {
	
	static def executeCommand(final Project project, final String executable, final String... args) {
		final def logger = project.logger
		
		logEdge(logger, "Start")
		log(logger, "Exec command '${executable}' with arguments ${args}")
		
		try {
			def output = new ByteArrayOutputStream(), error = new ByteArrayOutputStream()
			
			final def exitValue = project.exec { ExecSpec spec ->
				spec.executable(executable)
				spec.args(args)
				spec.setErrorOutput(error)
				spec.setStandardOutput(output)
				spec.ignoreExitValue = true
			}.exitValue
			
			final def outputString = output.toString(StandardCharsets.UTF_8.name())
			final def errorString = error.toString(StandardCharsets.UTF_8.name())
			
			def returnString = outputString
			if(exitValue != 0) {
				returnString += errorString
			}
			
			log(logger, "Output: ${outputString}")
			log(logger, "Error: ${errorString}")
			logEdge(logger, "Finished")
			
			return [exitValue == 0, returnString]
		} catch(final ExecException ex) {
			final def writer = new StringWriter();
			final def printWriter = new PrintWriter(writer)
			
			ex.printStackTrace(printWriter)
			
			log(logger, "Error: ${writer.toString()}")
			logEdge(logger, "Finished")
			
			return [false, writer.toString()]
		}
	}
	
	private static void logEdge(final Logger logger, final String message) {
		final def length = (80 - message.length()) / 2
		log(logger, "-"*length + " " + message + " " + "-"*length)
	}
	
	private static void log(final Logger logger, final String message) {
		logger.info("[Execute Command]: " + message.trim())
	}
}
