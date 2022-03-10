package info.u_team.gradle_files_plugin.tool

import org.apache.commons.lang3.StringUtils
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.tasks.bundling.Jar

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.DependencyUtil
import net.minecraftforge.gradle.common.tasks.SignJar
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace

class SignJarTaskTool {
	
	private static final def requiredProperties = [
		Constants.KEYSTORE,
		Constants.KEYSTORE_ALIAS,
		Constants.KEYSTORE_PASSWORD,
		Constants.KEYSTORE_KEY_PASSWORD
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
		
		final def tasks = project.tasks
		
		tasks.withType(Jar) { jarTask ->
			final def signJarTaks = project.tasks.register("sign" + StringUtils.capitalize(jarTask.name), SignJar) { task ->
				task.description = "Sign the jar ${jarTask.name}"
				task.group = BasePlugin.BUILD_GROUP
				task.keyStore = project.property(Constants.KEYSTORE)
				task.alias = project.property(Constants.KEYSTORE_ALIAS)
				task.storePass = project.property(Constants.KEYSTORE_PASSWORD)
				task.keyPass = project.property(Constants.KEYSTORE_KEY_PASSWORD)
				task.inputFile = jarTask.archivePath
				task.outputFile = jarTask.archivePath
				task.enabled = project.hasProperty(Constants.BUILD_PROPERTY)
				
				task.dependsOn(jarTask)
			}
			
			tasks.withType(RenameJarInPlace) { reobfTask ->
				signJarTaks.configure { task ->
					task.dependsOn(reobfTask)
				}
			}
			
			// TODO refactor to not add all sign task to publishing and assemble dependencies
			DependencyUtil.assembleDependOn(project, signJarTaks)
			DependencyUtil.allPublishingDependOn(project, signJarTaks)
		}
	}
}
