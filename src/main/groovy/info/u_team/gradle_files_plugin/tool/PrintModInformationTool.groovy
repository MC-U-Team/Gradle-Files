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
		
		final def minecraftVersionString = minecraftVersion != null ? "Minecraft Version: ${minecraftVersion}" : ""
		final def forgeVersionString = forgeVersion != null ? "Forge Version: ${forgeVersion}" : ""
		final def fabricLoaderVersionString = fabricLoaderVersion != null ? "Fabric Loader Version: ${fabricLoaderVersion}" : ""
		final def fabricApiVersionString = fabricApiVersion != null ? "Fabric API Version: ${fabricApiVersion}" : ""
		
		final int length = [
			minecraftVersionString,
			forgeVersionString,
			fabricLoaderVersionString,
			fabricApiVersionString
		]*.length().max()
		
		logger.lifecycle("")
		logger.lifecycle("-"*length)
		if(minecraftVersionString) {
			logger.lifecycle(minecraftVersionString)
		}
		if(forgeVersionString) {
			logger.lifecycle(forgeVersionString)
		}
		if(fabricLoaderVersionString) {
			logger.lifecycle(fabricLoaderVersionString)
		}
		if(fabricApiVersionString) {
			logger.lifecycle(fabricApiVersionString)
		}
		logger.lifecycle("-"*length)
		logger.lifecycle("")
	}
}
