package info.u_team.gradle_files_plugin.project.tool

import org.gradle.api.Project
import org.gradle.api.Task

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.ClassUtil
import info.u_team.sign_jar_plugin.extension.CreateSignJarTaskExtensionImpl

@CompileStatic
class SetupSpecialModLoaderSettingsTool {
	
	static final String RENAME_JAR_CLASS = "net.minecraftforge.renamer.gradle.RenameJar"
	
	private static final Closure RENAME_JAR_ARCHIVE_MAPPING = renameJarArchiveMapping()
	
	static void setup(final Project project) {
		setupForge(project)
	}
	
	private static void setupForge(final Project project) {
		setupForgeJarSigning(project)
	}
	
	private static void setupForgeJarSigning(final Project project) {
		// Add renamed jar (renamer plugin) as input option to jar signing
		if(!CreateSignJarTaskExtensionImpl.CUSTOM_ARCHIVE_MAPPING.contains(RENAME_JAR_ARCHIVE_MAPPING)) {
			CreateSignJarTaskExtensionImpl.CUSTOM_ARCHIVE_MAPPING.add(RENAME_JAR_ARCHIVE_MAPPING)
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
