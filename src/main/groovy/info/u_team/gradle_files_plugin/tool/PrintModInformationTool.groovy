package info.u_team.gradle_files_plugin.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

@CompileStatic
class PrintModInformationTool {

	static void print(final Project project) {
		if(GradleFilesUtil.isMainProject(project)) {
			handleMainProject(project)
		}
	}

	private static void handleMainProject(final Project project) {
		final def logger = project.logger
		final def config = GradleFilesUtil.getProjectConfig(project).flatten()

		final String minecraftVersion = config.getOrDefault("minecraft.version", "");
		final String neoForgeVersion = config.getOrDefault("neoforge.version", "");
		final String forgeVersion = config.getOrDefault("forge.version", "");
		final String fabricLoaderVersion = config.getOrDefault("fabric.loader.version", "");
		final String fabricApiVersion = config.getOrDefault("fabric.api.version", "");

		final def minecraftVersionString = "Minecraft Version: ${minecraftVersion}"
		final def neoForgeVersionString = "NeoForge Version: ${neoForgeVersion}"
		final def forgeVersionString = "Forge Version: ${forgeVersion}"
		final def fabricLoaderVersionString = "Fabric Loader Version: ${fabricLoaderVersion}"
		final def fabricApiVersionString = "Fabric API Version: ${fabricApiVersion}"

		final int length = [
			minecraftVersionString,
			neoForgeVersionString,
			forgeVersionString,
			fabricLoaderVersionString,
			fabricApiVersionString
		]*.length().max()

		logger.lifecycle("")
		logger.lifecycle("-"*length)
		if(!StringUtils.isEmpty(minecraftVersion)) {
			logger.lifecycle(minecraftVersionString)
		}
		if(!StringUtils.isEmpty(neoForgeVersion)) {
			logger.lifecycle(neoForgeVersion)
		}
		if(!StringUtils.isEmpty(forgeVersion)) {
			logger.lifecycle(forgeVersionString)
		}
		if(!StringUtils.isEmpty(fabricLoaderVersion)) {
			logger.lifecycle(fabricLoaderVersionString)
		}
		if(!StringUtils.isEmpty(fabricApiVersion)) {
			logger.lifecycle(fabricApiVersionString)
		}
		logger.lifecycle("-"*length)
		logger.lifecycle("")
	}
}
