package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class DefaultManifestExtensionImpl {
	
	static def defaultManifest() {
		final def (Project project, config, extension) = GradleFilesUtil.getProjectProperties()
		
		final def timestamp = new Date().format("yyyy-MM-dd'T'HH:mm:ssZ")
		
		return {
			attributes(
					"Specification-Title": config.mod.name,
					"Specification-Vendor": extension.vendor,
					"Specification-Version": config.mod.version,
					
					"Implementation-Title": config.mod.name,
					"Implementation-Version": project.version,
					"Implementation-Vendor": extension.vendor,
					"Implementation-Timestamp": timestamp,
					
					"Built-On": "Minecraft ${config.minecraft.version}",
					"Built-Timestamp": timestamp,
					"Built-Jdk": project.extensions.getByType(JavaPluginExtension).toolchain.getDisplayName(),
					
					"Fingerprint": project.findProperty(Constants.KEYSTORE_FINGERPRINT) ?: "NONE",
					
					"Automatic-Module-Name": "${project.group}.${config.mod.modid}".replace("-", "_")
					)
		}
	}
}
