package info.u_team.gradle_files_plugin.extension

import info.u_team.gradle_files_plugin.util.GradleFilesUtil
import org.gradle.api.Project

class DefaultManifestExtensionImpl {
	
	static def defaultManifest() {
		final def (Project project, config, extension) = GradleFilesUtil.getProjectProperties()
		
		return {
			attributes(
					"Specification-Title": config.mod.name,
					"Specification-Vendor": extension.vendor,
					"Specification-Version": config.mod.version,
					
					"Implementation-Title": config.mod.name,
					"Implementation-Version": project.version,
					"Implementation-Vendor": extension.vendor,
					"Implementation-Timestamp": new Date().format("yyyy-MM-dd'T'HH:mm:ssZ"),
					
					"Built-On": config.forge.mcversion,
					
					"Fingerprint": project.findProperty("keystore.fingerprint") ?: "NONE",
					
					"Automatic-Module-Name": "${project.group}.${config.mod.modid}"
					)
		}
	}
}
