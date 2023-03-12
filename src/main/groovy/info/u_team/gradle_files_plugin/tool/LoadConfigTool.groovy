package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class LoadConfigTool {
	
	static void load(final GradleFilesPlugin plugin) {
		plugin.logger.lifecycle(GradleFilesUtil.getRootProject().toString())
		if(!GradleFilesUtil.isRootProject()) {
			return
		}
		
		final def propertiesFile = plugin.project.file(Constants.CONFIG_FILE)
		
		propertiesFile.withReader { reader ->
			final def properties = new Properties()
			properties.load(reader)
			
			final def config = new ConfigSlurper().parse(properties)
			plugin.project.extensions.extraProperties.config = config
		}
	}
}
