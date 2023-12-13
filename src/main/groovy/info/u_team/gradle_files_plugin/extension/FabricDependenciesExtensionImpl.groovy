package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class FabricDependenciesExtensionImpl {
	
	static def fabricMinecraftDependency(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		return "com.mojang:minecraft:${config.minecraft.version}"
	}
	
	static def fabricLoaderDependency(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		return "net.fabricmc:fabric-loader:${config.fabric.loader.version}"
	}
	
	static def fabricApiDependency(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		return "net.fabricmc.fabric-api:fabric-api:${config.fabric.api.version}"
	}
}
