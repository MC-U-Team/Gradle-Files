package info.u_team.gradle_files_plugin.project.tool

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.util.DependencyUtil

@CompileStatic
class SetupSpecialModLoaderSettingsTool {

	static void setup(final Project project) {
		setupForge(project)
	}

	private static void setupForge(final Project project) {
		setupForgeReobf(project)
		setupForgeReobfDependencies(project)
		setupForgeRenameRuns(project)
	}

	@CompileDynamic
	private static void setupForgeReobf(final Project project) {
		// Eagerly create reobfJar task
		final def reobfExtension = project.extensions.findByName("reobf")
		if(reobfExtension) {
			reobfExtension.create(JavaPlugin.JAR_TASK_NAME);
		}
	}

	private static void setupForgeReobfDependencies(final Project project) {
		// Set all publish tasks depend on reobfJar. This is necessary for correct metadata generation
		project.afterEvaluate {
			final def reobfClass = findClass(project, "net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace")
			if(reobfClass) {
				project.tasks.withType(reobfClass).each { task ->
					DependencyUtil.allBuildingDependOn(project, task)
				}
			}
		}
	}

	@CompileDynamic
	private static void setupForgeRenameRuns(final Project project) {
		// Rename run configurations to include project name
		project.afterEvaluate {
			final def minecraftExtensionClass = findClass(project, "net.minecraftforge.gradle.common.util.MinecraftExtension")
			if(minecraftExtensionClass) {
				final def minecraftExtension = project.extensions.findByType(minecraftExtensionClass)

				minecraftExtension?.runs?.each { run ->
					run.taskName = "${project.name}_${run.taskName}"
				}
			}
		}
	}

	private static Class<?> findClass(final Project project, final String name) {
		try {
			return Class.forName(name, false, project.buildscript.classLoader)
		} catch(Exception ex) {
			return null
		}
	}
}
