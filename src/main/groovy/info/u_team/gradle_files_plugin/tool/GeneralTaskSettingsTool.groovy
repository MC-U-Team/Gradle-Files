package info.u_team.gradle_files_plugin.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.DependencyUtil

class GeneralTaskSettingsTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		final def tasks = plugin.project.tasks
		
		// Set utf 8 as default encoding for compile tasks
		tasks.withType(JavaCompile) { task ->
			task.options.encoding = "UTF-8"
		}
		
		// Set utf 8 as default encoding for javadoc tasks
		tasks.withType(Javadoc) { task ->
			task.options.encoding = "UTF-8"
		}
		
		// Set all publish tasks depend on reobfJar. This is necessary for correct metadata generation
		final def reobfTask = tasks.findByName("reobf" + StringUtils.capitalize(JavaPlugin.JAR_TASK_NAME))
		if(reobfTask.enabled) {
			DependencyUtil.allPublishingDependOn(plugin.project, reobfTask)
		}
	}
}
