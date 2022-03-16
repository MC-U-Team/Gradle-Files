package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.DependencyUtil
import net.minecraftforge.gradle.common.tasks.SignJar
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace
import org.apache.commons.lang3.StringUtils
import org.gradle.api.plugins.BasePlugin
import org.gradle.jvm.tasks.Jar

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
		
		tasks.withType(org.gradle.api.tasks.bundling.Jar) { jarTask ->
			if(!jarTask.enabled) {
				return;
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
				task.enabled = project.hasProperty(Constants.BUILD_PROPERTY)
				
				task.dependsOn(jarTask)
			}
			
			tasks.withType(RenameJarInPlace) { reobfTask ->
				signJarTaks.configure { task ->
					task.dependsOn(reobfTask)
				}
			}

			tasks.matching { remapTask ->
				remapTask.name.startsWith("remap")
			}.each { remapTask ->
				signJarTaks.configure { task ->
					task.dependsOn(remapTask)
					if(remapTask instanceof Jar) {
						if(task.inputFile.getAsFile().get().canonicalPath == remapTask.inputFile.getAsFile().get().canonicalPath) {
							task.inputFile = remapTask.archiveFile.get()
							task.outputFile = remapTask.archiveFile.get()
						}
					}
				}
			}
			
			// TODO refactor to not add all sign task to publishing and assemble dependencies
			DependencyUtil.assembleDependOn(project, signJarTaks)
			DependencyUtil.allPublishingDependOn(project, signJarTaks)
		}
	}
}
