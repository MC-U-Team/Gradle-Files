package info.u_team.gradle_files_plugin.project.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.plugins.ide.eclipse.EclipsePlugin
import org.gradle.plugins.ide.eclipse.model.EclipseModel
import org.gradle.plugins.ide.idea.IdeaPlugin

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.GradleFilesProjectExtension
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil
import info.u_team.sign_jar_plugin.SignJarPlugin

@CompileStatic
class SetupPluginEnvironmentTool {

	static void setup(final Project project) {
		// Add uteam maven repository
		project.repositories.maven { MavenArtifactRepository maven ->
			maven.name = Constants.U_TEAM_MAVEN_NAME
			maven.url = Constants.U_TEAM_MAVEN_URL
		}

		// Apply base gradle plugin
		final def pluginManager = project.pluginManager;
		pluginManager.apply(BasePlugin)

		// Apply java, eclipse and idea plugin only for subprojects or non multi loader projects
		if(!GradleFilesUtil.isMultiLoaderProject(project) || !GradleFilesUtil.isMainProject(project)) {
			pluginManager.apply(JavaPlugin)
			pluginManager.apply(EclipsePlugin)
			pluginManager.apply(IdeaPlugin)
			pluginManager.apply(SignJarPlugin)
		}

		// Add value methods
		final def extraProperties = GradleFilesUtil.getExtraProperties(project)
		extraProperties.set("propertyValue", { String name ->
			final def environment = System.getenv(name)
			return environment != null ? environment : project.findProperty(name)
		})
		extraProperties.set("defaultPropertyValue", { String name ->
			final def property = (extraProperties.get("propertyValue") as Closure<String>)(name)
			return property != null ? property : "noValue"
		})

		// Copy extension values from main project
		if(!GradleFilesUtil.isMainProject(project)) {
			final def mainProject = GradleFilesUtil.getMainProject(project)
			project.extensions.getByType(GradleFilesProjectExtension).apply(mainProject.extensions.getByType(GradleFilesProjectExtension))
		}

		// Set eclipse project name for sub projects to avoid collision
		if(!GradleFilesUtil.isMainProject(project)) {
			final def mainProject = GradleFilesUtil.getMainProject(project)

			final def eclipse = project.extensions.getByType(EclipseModel)
			eclipse.project.name = "${mainProject.name}_${StringUtils.capitalize(project.name)}"
		}
	}
}
