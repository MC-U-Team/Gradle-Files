package info.u_team.gradle_files_plugin.tool

import org.gradle.api.plugins.JavaPlugin
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.idea.IdeaPlugin

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class SetupPluginEnvironmentTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		
		// Add uteam maven repository
		project.repositories.maven { maven ->
			maven.name = Constants.U_TEAM_MAVEN_NAME
			maven.url = Constants.U_TEAM_MAVEN_URL
		}
		
		// Apply java gradle plugin
		project.pluginManager.apply(JavaPlugin)
		
		// Apply eclipse gradle plugin
		project.pluginManager.apply(EclipsePlugin)
		
		// Apply intellij gradle plugin
		project.pluginManager.apply(IdeaPlugin)
		
		// Add value methods
		final def extraProperties = project.extensions.extraProperties
		
		extraProperties.defaultPropertyValue = { name ->
			final def property = extraProperties.propertyValue(name)
			return property != null ? property : "noValue"
		}
		extraProperties.propertyValue = { name ->
			final def env = System.getenv(name)
			return env != null ? env : project.findProperty(name)
		}
	}
}
