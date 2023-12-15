package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project
import org.gradle.api.java.archives.Manifest

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.GradleFilesProjectExtension
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

class DefaultManifestExtensionImpl {

	static Closure<Manifest> defaultManifest(final Project project, final GradleFilesProjectExtension extension) {
		final def config = GradleFilesUtil.getProjectConfig(project)

		final def timestamp = new Date().format("yyyy-MM-dd'T'HH:mm:ssZ")

		return {
			attributes(
					"Specification-Title": config.mod.name,
					"Specification-Vendor": extension.vendor.get(),
					"Specification-Version": config.mod.version,

					"Implementation-Title": extension.displayName(),
					"Implementation-Version": project.version,
					"Implementation-Vendor": extension.vendor.get(),
					"Implementation-Timestamp": timestamp,

					"Built-On": "Minecraft ${config.minecraft.version}",
					"Built-Timestamp": timestamp,

					"Fingerprint": project.findProperty(Constants.KEYSTORE_FINGERPRINT) ?: "NONE",

					"Automatic-Module-Name": "${project.group}.${config.mod.modid}".replace("-", "_")
					)
		}
	}
}
