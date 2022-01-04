package info.u_team.gradle_files_plugin

import info.u_team.gradle_files_plugin.util.GradleFilesUtil
import org.gradle.api.Project

class GradleFilesExtension {
	
	String vendor
	
	def defaultManifest() {
		def (Project project, config) = GradleFilesUtil.getConfig()
		
		return {
			attributes(
					"Specification-Title": config.mod.name,
					"Specification-Vendor": vendor,
					"Specification-Version": config.mod.version,
					"Built-On": config.forge.mcversion,
					"Implementation-Title": config.mod.name,
					"Implementation-Version": project.version,
					"Implementation-Vendor": vendor,
					"Implementation-Timestamp": new Date().format("yyyy-MM-dd'T'HH:mm:ssZ"),
					"Fingerprint": project.findProperty("keystore.fingerprint") ?: "NONE",
					"Automatic-Module-Name": "${project.group}.${config.mod.modid}"
					)
		}
	}
}