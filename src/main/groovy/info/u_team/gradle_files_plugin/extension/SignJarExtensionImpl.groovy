package info.u_team.gradle_files_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.file.RegularFile
import org.gradle.api.plugins.BasePlugin
import org.gradle.jvm.tasks.Jar

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.GradleFilesUtil
import net.minecraftforge.gradle.common.tasks.SignJar
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace

class SignJarExtensionImpl {
	
	private static final def requiredProperties = [
		Constants.KEYSTORE,
		Constants.KEYSTORE_ALIAS,
		Constants.KEYSTORE_PASSWORD,
		Constants.KEYSTORE_KEY_PASSWORD
	]
	
	static def signJar(String taskName) {
		final def (Project project) = GradleFilesUtil.getProjectProperties()
		
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
				task.keyStore = project.property(Constants.KEYSTORE)
				task.alias = project.property(Constants.KEYSTORE_ALIAS)
				task.storePass = project.property(Constants.KEYSTORE_PASSWORD)
				task.keyPass = project.property(Constants.KEYSTORE_KEY_PASSWORD)
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
	
	static def signDefaultForgeJar() {
		signJar("reobfJar")
	}
	
	static def signDefaultFabricJar() {
		signJar("remapJar")
	}
}
