package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

class ForgeDependencyExtensionImpl {
	
	static def forgeDependency(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		return "net.minecraftforge:forge:${config.minecraft.version}-${config.forge.version}"
	}
}
