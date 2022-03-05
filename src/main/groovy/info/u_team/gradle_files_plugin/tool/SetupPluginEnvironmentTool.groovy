package info.u_team.gradle_files_plugin.tool

import org.gradle.api.plugins.JavaPlugin
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.idea.IdeaPlugin

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class SetupPluginEnvironmentTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		// Add uteam maven repository
		plugin.project.repositories.maven { maven ->
			maven.name = Constants.U_TEAM_MAVEN_NAME
			maven.url = Constants.U_TEAM_MAVEN_URL
		}
		
		// Apply java gradle plugin
		plugin.project.pluginManager.apply(JavaPlugin)
		
		// Apply eclipse gradle plugin
		plugin.project.pluginManager.apply(EclipsePlugin)
		
		// Apply intellij gradle plugin
		plugin.project.pluginManager.apply(IdeaPlugin)
	}
}
