package info.u_team.gradle_files_plugin.project.util

import java.nio.charset.StandardCharsets

import org.gradle.api.Action
import org.gradle.api.GradleException
import org.gradle.api.logging.Logger
import org.gradle.api.provider.ProviderFactory
import org.gradle.process.ExecOperations
import org.gradle.process.ExecSpec

import groovy.transform.CompileStatic

@CompileStatic
class ExecuteUtil {
	
	final Logger logger
	
	private final Closure<List> executor
	
	private ExecuteUtil(final Logger logger, final Closure<List> executor) {
		this.logger = logger
		this.executor = executor
	}
	
	/**
	 * Executor for the configuration phase. The commands are tracked as configuration cache inputs
	 */
	static ExecuteUtil fromProviders(final Logger logger, final ProviderFactory providers) {
		return new ExecuteUtil(logger, { Action<ExecSpec> action ->
			final def execOutput = providers.exec(action)
			return [execOutput.result.get().exitValue, execOutput.standardOutput.asText.get(), execOutput.standardError.asText.get()]
		})
	}
	
	/**
	 * Executor for the execution phase
	 */
	static ExecuteUtil fromExecOperations(final Logger logger, final ExecOperations execOperations) {
		return new ExecuteUtil(logger, { Action<ExecSpec> action ->
			def output = new ByteArrayOutputStream(), error = new ByteArrayOutputStream()
			
			final def exitValue = execOperations.exec { ExecSpec spec ->
				action.execute(spec)
				spec.setErrorOutput(error)
				spec.setStandardOutput(output)
			}.exitValue
			
			return [exitValue, output.toString(StandardCharsets.UTF_8.name()), error.toString(StandardCharsets.UTF_8.name())]
		})
	}
	
	def executeCommand(final def workingDir, final String executable, final String... args) {
		logEdge("Start")
		log("Exec command '${executable}' with arguments ${args}")
		
		try {
			final def result = executor.call({ ExecSpec spec ->
				spec.workingDir(workingDir)
				spec.executable(executable)
				spec.args(args)
				spec.ignoreExitValue = true
			} as Action<ExecSpec>)
			
			final def exitValue = result[0] as int
			final def outputString = result[1] as String
			final def errorString = result[2] as String
			
			def returnString = outputString
			if(exitValue != 0) {
				returnString += errorString
			}
			
			log("Output: ${outputString}")
			log("Error: ${errorString}")
			logEdge("Finished")
			
			return [exitValue == 0, returnString]
		} catch(final GradleException ex) {
			final def writer = new StringWriter();
			final def printWriter = new PrintWriter(writer)
			
			ex.printStackTrace(printWriter)
			
			log("Error: ${writer.toString()}")
			logEdge("Finished")
			
			return [false, writer.toString()]
		}
	}
	
	private void logEdge(final String message) {
		final def length = (80 - message.length()) / 2
		log("-"*length + " " + message + " " + "-"*length)
	}
	
	private void log(final String message) {
		logger.info("[Execute Command]: " + message.trim())
	}
}
