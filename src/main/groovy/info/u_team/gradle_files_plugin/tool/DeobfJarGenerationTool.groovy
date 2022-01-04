package info.u_team.gradle_files_plugin.tool

import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.bundling.Jar
import org.gradle.language.base.plugins.LifecycleBasePlugin

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class DeobfJarGenerationTool {
	
	static void add(final GradleFilesPlugin plugin) {
		final def javaPluginExtension = plugin.project.extensions.getByType(JavaPluginExtension)
		javaPluginExtension.metaClass.withDeobfJar {
			final def tasks = plugin.project.tasks
			
			final def jarTask = tasks.register("deobfJar", Jar) { task ->
				task.description = "Assemble a jar archive containing the deobfed classes"
				task.group = BasePlugin.BUILD_GROUP
				task.archiveClassifier = "deobf"
				task.from(javaPluginExtension.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).output)
			}
			
			if (tasks.names.contains(LifecycleBasePlugin.ASSEMBLE_TASK_NAME)) {
				tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure { task ->
					task.dependsOn(jarTask)
				}
			}
		}
	}
}
