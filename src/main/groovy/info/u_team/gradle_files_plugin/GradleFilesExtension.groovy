package info.u_team.gradle_files_plugin

import info.u_team.gradle_files_plugin.extension.DefaultManifestExtensionImpl
import info.u_team.gradle_files_plugin.util.GradleFilesUtil
import org.gradle.api.Project

class GradleFilesExtension {
	
	String vendor
	
	def defaultManifest() {
		return DefaultManifestExtensionImpl.defaultManifest()
	}
}