package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class NeoForgeDependencyExtensionImpl {

	static def neoForgeDependency(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)

		return "net.neoforged:neoforge:${config.neoforge.version}"
	}
}
