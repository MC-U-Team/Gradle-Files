package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

class NeoForgeDependencyExtensionImpl {

	static def neoForgeDependency(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)

		return "net.neoforged:neoforge:${config.neoforge.version}"
	}
}
