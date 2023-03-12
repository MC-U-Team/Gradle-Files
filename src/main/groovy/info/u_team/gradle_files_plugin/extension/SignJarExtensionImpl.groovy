package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.plugins.BasePlugin
import org.gradle.jvm.tasks.Jar
import codechicken.repack.org.apache.commons.lang3.StringUtils
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.DependencyUtil
import net.minecraftforge.gradle.common.tasks.SignJar
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace

class SignJarExtensionImpl {
	
	private static final def requiredProperties = [
		Constants.KEYSTORE,
		Constants.KEYSTORE_ALIAS,
		Constants.KEYSTORE_PASSWORD,
		Constants.KEYSTORE_KEY_PASSWORD
	]
	
	static def signJar(final Project project, String taskName) {
		final boolean canSign = project.properties.keySet().containsAll(requiredProperties)
		
		if(!canSign) {
			project.logger.warn("Signing of jars was requested, but required properties are missing")
		}
		
		final def signJarTaskName = "sign" + StringUtils.capitalize(taskName)
		
		project.afterEvaluate {
			final tasks = project.tasks
			
			final def jarTask = tasks.getByName(taskName)
			
			RegularFile archiveFile
			if(jarTask instanceof Jar) {
				archiveFile = jarTask.archiveFile.get()
			} else if(jarTask instanceof RenameJarInPlace) {
				archiveFile = jarTask.input.get()
			} else {
				throw new IllegalArgumentException("Task for signing must be a jar or remap / reobf task")
			}
			
			final def signJarTask = project.tasks.register(signJarTaskName, SignJar) { task ->
				task.description = "Sign the jar ${jarTask.name}"
				task.group = BasePlugin.BUILD_GROUP
				task.keyStore = project.findProperty(Constants.KEYSTORE)
				task.alias = project.findProperty(Constants.KEYSTORE_ALIAS)
				task.storePass = project.findProperty(Constants.KEYSTORE_PASSWORD)
				task.keyPass = project.findProperty(Constants.KEYSTORE_KEY_PASSWORD)
				task.inputFile = archiveFile
				task.outputFile = archiveFile
				task.enabled = canSign && project.hasProperty(Constants.BUILD_PROPERTY)
				
				task.dependsOn(jarTask)
				task.mustRunAfter(jarTask)
			}
			
			DependencyUtil.assembleDependOn(project, signJarTask)
			DependencyUtil.allPublishingDependOn(project, signJarTask)
		}
		
		return signJarTaskName
	}
	
	static def signDefaultForgeJar(final Project project) {
		signJar(project, "reobfJar")
	}
	
	static def signDefaultFabricJar(final Project project) {
		signJar(project, "remapJar")
	}
}
