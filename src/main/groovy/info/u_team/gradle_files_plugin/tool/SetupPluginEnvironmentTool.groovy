package info.u_team.gradle_files_plugin.tool

import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.idea.IdeaPlugin

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class SetupPluginEnvironmentTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		
		// Add uteam maven repository
		project.repositories.maven { maven ->
			maven.name = Constants.U_TEAM_MAVEN_NAME
			maven.url = Constants.U_TEAM_MAVEN_URL
		}
		
		// Apply base gradle plugin
		project.pluginManager.apply(BasePlugin)
		
		// Apply eclipse and idea plugin only for subprojects or non multi loader projects
		if(!GradleFilesUtil.isMultiLoaderProject(project) || !GradleFilesUtil.isMainProject(project)) {
			// Apply java gradle plugin
			project.pluginManager.apply(JavaPlugin)
			
			// Apply eclipse gradle plugin
			project.pluginManager.apply(EclipsePlugin)
			
			// Apply intellij gradle plugin
			project.pluginManager.apply(IdeaPlugin)
		}
		
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
		
		// Copy extension values from main project
		if(!GradleFilesUtil.isMainProject(project)) {
			final def mainProject = GradleFilesUtil.getMainProject(project)
			plugin.extension.apply(mainProject.extensions."${Constants.EXTENSION_NAME}")
		}
	}
}
