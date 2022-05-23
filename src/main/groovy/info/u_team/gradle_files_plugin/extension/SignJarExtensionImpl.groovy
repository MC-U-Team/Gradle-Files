package info.u_team.gradle_files_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.BasePlugin
import org.gradle.jvm.tasks.Jar

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.GradleFilesUtil
import net.minecraftforge.gradle.common.tasks.SignJar

class SignJarExtensionImpl {
	
	private static final def requiredProperties = [
		Constants.KEYSTORE,
		Constants.KEYSTORE_ALIAS,
		Constants.KEYSTORE_PASSWORD,
		Constants.KEYSTORE_KEY_PASSWORD
	]
	
	static Task signJar(Jar jarTask) {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		final boolean canSign = project.properties.keySet().containsAll(requiredProperties)
		
		if(!canSign) {
			project.logger.warn("Signing of jars was requested, but required properties are missing")
		}
		
		final def signJarTaks = project.tasks.register("sign" + StringUtils.capitalize(jarTask.name), SignJar) { task ->
			task.description = "Sign the jar ${jarTask.name}"
			task.group = BasePlugin.BUILD_GROUP
			task.keyStore = project.property(Constants.KEYSTORE)
			task.alias = project.property(Constants.KEYSTORE_ALIAS)
			task.storePass = project.property(Constants.KEYSTORE_PASSWORD)
			task.keyPass = project.property(Constants.KEYSTORE_KEY_PASSWORD)
			task.inputFile = jarTask.archiveFile.get()
			task.outputFile = jarTask.archiveFile.get()
			task.enabled = canSign && project.hasProperty(Constants.BUILD_PROPERTY)
			
			task.dependsOn(jarTask)
			task.mustRunAfter(jarTask)
		}
		
		DependencyUtil.assembleDependOn(project, signJarTaks)
		DependencyUtil.allPublishingDependOn(project, signJarTaks)
	}
}
