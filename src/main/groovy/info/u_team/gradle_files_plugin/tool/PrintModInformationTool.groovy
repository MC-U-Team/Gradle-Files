package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class PrintModInformationTool {
	
	static void print(final GradleFilesPlugin plugin) {
		final def logger = plugin.logger
		final def config = plugin.project.extensions.extraProperties.config
		
		final def minecraftVersion = config.minecraft.version
		final def forgeVersion = config.forge.version
		final def fabricLoaderVersion = config.fabric.loader.version
		final def fabricApiVersion = config.fabric.api.version
		
		final def minecraftVersionString = "Minecraft Version: ${minecraftVersion}"
		final def forgeVersionString = "Forge Version: ${forgeVersion}"
		final def fabricLoaderVersionString = "Fabric Loader Version: ${fabricLoaderVersion}"
		final def fabricApiVersionString = "Fabric API Version: ${fabricApiVersion}"
		
		final int length = [
			minecraftVersionString,
			forgeVersionString,
			fabricLoaderVersionString,
			fabricApiVersionString
		]*.length().max()
		
		logger.lifecycle("")
		logger.lifecycle("-"*length)
		if(!minecraftVersion.isEmpty()) {
			logger.lifecycle(minecraftVersionString)
		}
		if(!forgeVersion.isEmpty()) {
			logger.lifecycle(forgeVersionString)
		}
		if(!fabricLoaderVersion.isEmpty()) {
			logger.lifecycle(fabricLoaderVersionString)
		}
		if(!fabricApiVersion.isEmpty()) {
			logger.lifecycle(fabricApiVersionString)
		}
		logger.lifecycle("-"*length)
		logger.lifecycle("")
	}
}
