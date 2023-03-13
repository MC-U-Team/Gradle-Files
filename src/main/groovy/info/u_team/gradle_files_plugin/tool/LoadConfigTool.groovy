package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class LoadConfigTool {
	
	static void load(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		
		if(GradleFilesUtil.isRootProject(project)) {
			final def propertiesFile = project.file(Constants.CONFIG_FILE)
			
			propertiesFile.withReader { reader ->
				final def properties = new Properties()
				properties.load(reader)
				
				final def config = new ConfigSlurper().parse(properties)
				project.extensions.extraProperties.config = config
			}
		} else {
			project.extensions.extraProperties.config = GradleFilesUtil.getRootProject(plugin.project).extensions.extraProperties.config
		}
	}
}
