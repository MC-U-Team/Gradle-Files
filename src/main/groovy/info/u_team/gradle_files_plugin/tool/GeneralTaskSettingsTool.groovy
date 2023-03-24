package info.u_team.gradle_files_plugin.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.GradleException
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class GeneralTaskSettingsTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def tasks = project.tasks
		
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
		if(reobfTask?.enabled) {
			DependencyUtil.allPublishingDependOn(plugin.project, reobfTask)
		}
		
		// Check when artifact is published if build property is set
		if(GradleFilesUtil.isMainProject(project)) {
			project.gradle.taskGraph.whenReady { graph ->
				final def hasPublishTask = graph.allTasks.any { task ->
					task.name.startsWith(Constants.PUBLISH_EXTERNAL_TASK) || task.name.startsWith(Constants.CURSEFORGE_EXTERNAL_TASK)
				}
				
				if(hasPublishTask) {
					if(!project.hasProperty(Constants.BUILD_PROPERTY)) {
						throw new GradleException("Publishing artifacts is only allowed when property ${Constants.BUILD_PROPERTY} is supplied.")
					}
				}
			}
		}
	}
}
