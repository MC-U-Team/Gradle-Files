package info.u_team.gradle_files_plugin.project.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.ConfigurablePublishArtifact
import org.gradle.api.plugins.JavaPlugin

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.ClassUtil
import info.u_team.sign_jar_plugin.extension.CreateSignJarTaskExtensionImpl

@CompileStatic
class SetupSpecialModLoaderSettingsTool {
	
	private static final String RENAMER_PLUGIN = "net.minecraftforge.renamer"
	private static final String RENAME_JAR_CLASS = "net.minecraftforge.renamer.gradle.RenameJar"
	
	private static final Closure RENAME_JAR_ARCHIVE_MAPPING = renameJarArchiveMapping()
	
	static void setup(final Project project) {
		setupForge(project)
	}
	
	private static void setupForge(final Project project) {
		setupForgeJarSigning(project)
		setupForgeRenamedJarPublishing(project)
	}
	
	private static void setupForgeJarSigning(final Project project) {
		// Add renamed jar (renamer plugin) as input option to jar signing
		if(!CreateSignJarTaskExtensionImpl.CUSTOM_ARCHIVE_MAPPING.contains(RENAME_JAR_ARCHIVE_MAPPING)) {
			CreateSignJarTaskExtensionImpl.CUSTOM_ARCHIVE_MAPPING.add(RENAME_JAR_ARCHIVE_MAPPING)
		}
	}
	
	private static void setupForgeRenamedJarPublishing(final Project project) {
		// Use the jar renamed by the renamer plugin as the main jar, as the original jar only works in the development environment
		project.pluginManager.withPlugin(RENAMER_PLUGIN) {
			project.afterEvaluate {
				final def renameJarName = "rename" + StringUtils.capitalize(JavaPlugin.JAR_TASK_NAME)
				if(!project.tasks.names.contains(renameJarName)) {
					return
				}
				final def renameJar = project.tasks.named(renameJarName)
				[JavaPlugin.API_ELEMENTS_CONFIGURATION_NAME, JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME].each { name ->
					project.configurations.named(name) { configuration ->
						configuration.outgoing.artifacts.clear()
						configuration.outgoing.artifact(renameJar) { ConfigurablePublishArtifact artifact ->
							artifact.classifier = null
						}
					}
				}
			}
		}
	}
	
	@CompileDynamic
	private static Closure renameJarArchiveMapping() {
		return { Task task ->
			final def renameJarClass = ClassUtil.findClass(task.project, RENAME_JAR_CLASS)
			if(renameJarClass && renameJarClass.isInstance(task)) {
				return task.output
			}
		}
	}
}
