package info.u_team.gradle_files_plugin.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.jvm.internal.JvmPluginExtension
import org.gradle.api.tasks.bundling.Jar
import org.gradle.internal.impldep.org.jetbrains.annotations.Nls.Capitalization

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import net.minecraftforge.gradle.common.tasks.SignJar
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace

class SignJarTaskTool {
	
	private static final def requiredProperties = [
		Constants.KEYSTORE,
		Constants.KEYSTORE_ALIAS,
		Constants.KEYSTORE_PASSWORD
	]
	
	static void add(final GradleFilesPlugin plugin) {
		if(!plugin.extension.signJars) {
			return
		}
		final def project = plugin.project
		
		if(!project.properties.keySet().containsAll(requiredProperties)) {
			project.logger.warn("Signing of jars was requested, but required properties are missing")
			return
		}
		
		//		project.configurations.getByName("archives").allArtifacts.first()
		//
		//		project.components.getByName("java").art
		//
		//		NamedDomainObjectContainer<RenameJarInPlace> reobf = null
		//		reobf.create("")
		
		project.tasks.withType(Jar) { jarTask ->
			final def signJarTask = project.tasks.register("sign" + StringUtils.capitalize(jarTask.name), SignJar) { task ->
				task.description = "Sign the jar ${jarTask.name}"
				task.group = BasePlugin.BUILD_GROUP
				task.keyStore = project.property(Constants.KEYSTORE)
				task.alias = project.property(Constants.KEYSTORE_ALIAS)
				task.storePass = project.property(Constants.KEYSTORE_PASSWORD)
				task.keyPass = project.property(Constants.KEYSTORE_PASSWORD)
				task.inputFile = jarTask.archivePath
				task.outputFile = jarTask.archivePath
			}
			jarTask.finalizedBy(signJarTask)
		}
	}
}
