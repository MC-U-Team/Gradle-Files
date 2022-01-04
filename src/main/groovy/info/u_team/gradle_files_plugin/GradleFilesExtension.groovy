package info.u_team.gradle_files_plugin

import info.u_team.gradle_files_plugin.extension.DefaultManifestExtensionImpl
import info.u_team.gradle_files_plugin.extension.ForgeDependencyExtensionImpl

class GradleFilesExtension {
	
	String vendor
	
	def defaultManifest() {
		DefaultManifestExtensionImpl.defaultManifest()
	}
	
	def forgeDependency() {
		ForgeDependencyExtensionImpl.forgeDependency()
	}
}