package info.u_team.gradle_files_plugin.tool

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class LoadConfigTool {
	
	@CompileStatic
	static void load(final GradleFilesPlugin plugin) {
		if(GradleFilesUtil.isMainProject(plugin.project)) {
			handleMainProject(plugin)
		} else {
			handleLoaderProject(plugin)
		}
	}
	
	private static void handleMainProject(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def propertiesFile = project.file(Constants.CONFIG_FILE)
		
		propertiesFile.withReader { reader ->
			final def properties = new Properties()
			properties.load(reader)
			
			final def config = new ConfigSlurper().parse(properties)
			GradleFilesUtil.getExtraProperties(project).config = config
		}
	}
	
	private static void handleLoaderProject(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		GradleFilesUtil.getExtraProperties(project).config = GradleFilesUtil.getProjectConfig(GradleFilesUtil.getMainProject(project))
	}
}
