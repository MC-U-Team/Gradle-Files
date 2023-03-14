package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class RenameRunConfigurationTool {
	
	static void rename(final GradleFilesPlugin plugin) {
		final def minecraftExtension = plugin.project.extensions.findByName("minecraft")
		minecraftExtension?.runs?.each { run ->
			run.taskName = "${plugin.project.name}_${run.taskName}"
		}
	}
}
