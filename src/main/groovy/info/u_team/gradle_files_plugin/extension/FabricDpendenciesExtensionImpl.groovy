package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class FabricDpendenciesExtensionImpl {
	
	static def fabricMinecraftDependency() {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		return "com.mojang:minecraft:${config.minecraft.version}"
	}
	
	static def fabricLoaderDependency() {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		return "net.fabricmc:fabric-loader:${config.fabric.loader.version}"
	}
	
	static def fabricApiDependency() {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		return "net.fabricmc.fabric-api:fabric-api:${config.fabric.loader.version}"
	}
}
