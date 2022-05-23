package info.u_team.gradle_files_plugin

import org.gradle.api.Task
import org.gradle.jvm.tasks.Jar

import info.u_team.gradle_files_plugin.extension.ArchiveBaseNameExtensionImpl
import info.u_team.gradle_files_plugin.extension.ChangelogUrlImpl
import info.u_team.gradle_files_plugin.extension.CreateReobfJarExtensionImpl
import info.u_team.gradle_files_plugin.extension.DefaultJarExtensionImpl
import info.u_team.gradle_files_plugin.extension.DefaultManifestExtensionImpl
import info.u_team.gradle_files_plugin.extension.FabricDpendenciesExtensionImpl
import info.u_team.gradle_files_plugin.extension.ForgeDependencyExtensionImpl
import info.u_team.gradle_files_plugin.extension.VersionExtensionImpl

class GradleFilesExtension {
	
	/**
	 * Should be set to configure the vendor of the build
	 */
	String vendor
	
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
	 * Add a sign task to a jar task. Also sets the dependency for publishing and assemble
	 * @param task Jar task to sign
	 * @return The sign task
	 */
	Task signJar(Jar task) {
		SignJarExtensionImpl.signJar(task)
	}
	
	/**
	 * Return the forge dependency
	 * @return Forge dependency
	 */
	def forgeDependency() {
		ForgeDependencyExtensionImpl.forgeDependency()
	}
	
	/**
	 * Return the fabric minecraft dependency
	 * @return Fabric minecraft dependency
	 */
	def fabricMinecraftDependency() {
		FabricDpendenciesExtensionImpl.fabricMinecraftDependency()
	}
	
	/**
	 * Return the fabric loader dependency
	 * @return Fabric loader dependency
	 */
	def fabricLoaderDependency() {
		FabricDpendenciesExtensionImpl.fabricLoaderDependency()
	}
	
	/**
	 * Return the fabric api dependency
	 * @return Fabric api dependency
	 */
	def fabricApiDependency() {
		FabricDpendenciesExtensionImpl.fabricApiDependency()
	}
	
	/**
	 * Create custom reobf Task (forge gradle) for a jar task
	 * @param task Jar task
	 * @return Reobf task
	 */
	def createReobfJar(Jar task) {
		CreateReobfJarExtensionImpl.createReobfJar(task)
	}
	
	/**
	 * Return the archive base name
	 * @return Archive base name
	 */
	def archivesBaseName() {
		ArchiveBaseNameExtensionImpl.archivesBaseName()
	}
	
	/**
	 * Return the version
	 * @return Version
	 */
	def version() {
		VersionExtensionImpl.version()
	}
	
	/**
	 * Return the changelog url
	 * @return Changelog url
	 */
	def changelogUrl() {
		ChangelogUrlImpl.changelogUrl()
	}
}