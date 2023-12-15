package info.u_team.gradle_files_plugin.project.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.util.DependencyUtil

@CompileStatic
class SetupSpecialModLoaderSettingsTool {

	static void setup(final Project project) {
		setupForge(project)
	}

	private static void setupForge(final Project project) {
		project.afterEvaluate {
			// Set all publish tasks depend on reobfJar. This is necessary for correct metadata generation
			final def reobfTask = project.tasks.findByName("reobf" + StringUtils.capitalize(JavaPlugin.JAR_TASK_NAME))
			if(reobfTask?.enabled) {
				DependencyUtil.allBuildingDependOn(project, reobfTask)
			}
		}
	}
}
