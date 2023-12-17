package info.u_team.gradle_files_plugin.project.tool

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.JavaPlugin

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.ClassUtil
import info.u_team.sign_jar_plugin.extension.CreateSignJarTaskExtensionImpl

@CompileStatic
class SetupSpecialModLoaderSettingsTool {

	static void setup(final Project project) {
		setupForge(project)
	}

	private static void setupForge(final Project project) {
		setupForgeReobf(project)
		setupForgeReobfDependencies(project)
		setupForgeRenameRuns(project)
		setupForgeJarSigning(project)
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
			final def reobfClass = ClassUtil.findClass(project, "net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace")
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
			final def minecraftExtensionClass = ClassUtil.findClass(project, "net.minecraftforge.gradle.common.util.MinecraftExtension")
			if(minecraftExtensionClass) {
				final def minecraftExtension = project.extensions.findByType(minecraftExtensionClass)

				minecraftExtension?.runs?.each { run ->
					run.taskName = "${project.name}_${run.taskName}"
				}
			}
		}
	}

	@CompileDynamic
	private static void setupForgeJarSigning(final Project project) {
		// Add reobf as input option to jar signing
		CreateSignJarTaskExtensionImpl.CUSTOM_ARCHIVE_MAPPING.add({ Task task ->
			final def reobfClass = ClassUtil.findClass(project, "net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace")
			if(reobfClass && reobfClass.isInstance(task)) {
				return task.input
			}
		});
	}
}
