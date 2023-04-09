package info.u_team.gradle_files_plugin.tool

import org.gradle.plugins.ide.eclipse.model.EclipseModel

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class RenameRunConfigurationTool {
	
	static void rename(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def minecraftExtension = project.extensions.findByName("minecraft")
		minecraftExtension?.runs?.each { run ->
			def projectName = (project.extensions.findByName("eclipse") as EclipseModel)?.project?.name
			if(!projectName) {
				projectName = project.name
			}
			run.taskName = "${projectName}_${run.taskName}"
		}
	}
}
