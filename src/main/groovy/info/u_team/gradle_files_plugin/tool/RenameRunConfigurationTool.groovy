package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import net.minecraftforge.gradle.common.util.MinecraftExtension

class RenameRunConfigurationTool {
	
	static void rename(final GradleFilesPlugin plugin) {
		final def minecraftExtension = plugin.project.extensions.getByType(MinecraftExtension)
		minecraftExtension.runs.each { run ->
			run.taskName = "${plugin.project.name}_${run.taskName}"
			println(run.taskName)
		}
	}
}
