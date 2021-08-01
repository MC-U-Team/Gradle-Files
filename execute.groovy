ext.execute = { command ->
	def process = command.execute()
	def output = new StringWriter(), error = new StringWriter()
	process.waitForProcessOutput(output, error)
	project.logger.debug "---------------------------"
	project.logger.debug "Using command: ${command}"
	project.logger.debug "Output: ${output}"
	project.logger.debug "Error: ${error}"
	project.logger.debug "---------------------------"
	return output.toString()
}