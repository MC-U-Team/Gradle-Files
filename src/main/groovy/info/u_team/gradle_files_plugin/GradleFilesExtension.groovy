package info.u_team.gradle_files_plugin

import org.gradle.jvm.tasks.Jar

class GradleFilesExtension {
	
	/**
	 * Should be set to configure the vendor of the build
	 */
	String vendor
	
	/**
	 * If all jar files should be signed
	 */
	boolean signJars
	
	/**
	 * Returns a manifest default file
	 * @return Manifest
	 */
	def defaultManifest() {
		DefaultManifestExtensionImpl.defaultManifest()
	}
	
	/**
	 * Include license file to build
	 * Exclude .cache file	
	 * @param task Jar task
	 */
	void defaultJar(Jar task) {
		DefaultJarExtensionImpl.defaultJar(task)
	}
	
	/**
	 * Return the forge dependency
	 * @return Forge dependency
	 */
	def forgeDependency() {
		ForgeDependencyExtensionImpl.forgeDependency()
	}
}