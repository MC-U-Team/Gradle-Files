package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class PrintJVMInformationTool implements Tool {
	
	@Override
	void run(final GradleFilesPlugin plugin) {
		final def logger = plugin.logger
		
		final def javaVersion = System.properties."java.version"
		final def javaVendor = System.properties."java.vendor"
		final def jvmVersion = System.properties."java.vm.version"
		final def arch = System.properties."os.arch"
		
		logger.lifecycle("Java: ${javaVersion}")
		logger.lifecycle("JVM: ${jvmVersion} (${javaVendor})")
		logger.lifecycle("Arch: ${arch}")
	}
}
