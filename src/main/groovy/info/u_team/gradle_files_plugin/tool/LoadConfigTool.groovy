package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class LoadConfigTool {
	
	static void load(final GradleFilesPlugin plugin) {
		final def propertiesFile = plugin.project.file(plugin.extension.configFile)
		
		final def properties = new Properties()
		
		propertiesFile.withReader { reader ->
			properties.load(reader)
		}
		
		plugin.project.extensions.extraProperties.config = properties
	}
}
