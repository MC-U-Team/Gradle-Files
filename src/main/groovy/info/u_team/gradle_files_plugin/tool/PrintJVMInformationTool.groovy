package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class PrintJVMInformationTool {
	
	static void print(final GradleFilesPlugin plugin) {
		final def logger = plugin.logger
		
		final def javaVersion = System.properties."java.version"
		final def javaVendor = System.properties."java.vendor"
		final def jvmVersion = System.properties."java.vm.version"
		final def arch = System.properties."os.arch"
		
		final def javaString = "Java: ${javaVersion}"
		final def jvmString = "JVM: ${jvmVersion} (${javaVendor})"
		final def archString = "Arch: ${arch}"
		
		final int length = [
			javaString,
			jvmString,
			archString
		]*.length().max()
		
		logger.lifecycle("")
		logger.lifecycle("-"*length)
		logger.lifecycle(javaString)
		logger.lifecycle(jvmString)
		logger.lifecycle(archString)
		logger.lifecycle("-"*length)
		logger.lifecycle("")
	}
}
