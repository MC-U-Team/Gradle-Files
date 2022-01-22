package info.u_team.gradle_files_plugin

import org.gradle.jvm.tasks.Jar

import info.u_team.gradle_files_plugin.extension.CreateReobfJarExtensionImpl
import info.u_team.gradle_files_plugin.extension.DefaultJarExtensionImpl
import info.u_team.gradle_files_plugin.extension.DefaultManifestExtensionImpl
import info.u_team.gradle_files_plugin.extension.ForgeDependencyExtensionImpl

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
	 * If dependencies with a mapped component should be stripped from maven pom and gradle meta data
	 */
	boolean stripMappedDependencies = true
	
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
	 * Also add the defaultManifest to the jar
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
	
	/**
	 * Create custom reobf Task for a jar task
	 * @param task Jar Task
	 */
	void createReobfJar(Jar task) {
		CreateReobfJarExtensionImpl.createReobfJar(task)
	}
}