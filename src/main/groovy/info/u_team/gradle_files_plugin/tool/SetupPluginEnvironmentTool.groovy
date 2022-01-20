package info.u_team.gradle_files_plugin.tool

import org.gradle.plugins.ide.eclipse.EclipsePlugin

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import net.minecraftforge.gradle.userdev.UserDevPlugin

class SetupPluginEnvironmentTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		// Add uteam maven repository
		plugin.project.repositories.maven { maven ->
			maven.name = Constants.U_TEAM_MAVEN_NAME
			maven.url = Constants.U_TEAM_MAVEN_URL
		}
		
		// Apply forge gradle plugin
		plugin.project.pluginManager.apply(UserDevPlugin)
		
		// Apply eclipse gradle plugin
		plugin.project.pluginManager.apply(EclipsePlugin)
	}
}
