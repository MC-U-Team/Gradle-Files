package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class ForgeDependencyExtensionImpl {
	
	static def forgeDependency() {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		return "net.minecraftforge:forge:${config.forge.mcversion}-${config.forge.version}"
	}
}
