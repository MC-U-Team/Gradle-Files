package info.u_team.gradle_files_plugin

import org.gradle.jvm.tasks.Jar

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.extension.ArchiveBaseNameExtensionImpl
import info.u_team.gradle_files_plugin.extension.ChangelogUrlImpl
import info.u_team.gradle_files_plugin.extension.CreateReobfJarExtensionImpl
import info.u_team.gradle_files_plugin.extension.DefaultJarExtensionImpl
import info.u_team.gradle_files_plugin.extension.DefaultManifestExtensionImpl
import info.u_team.gradle_files_plugin.extension.DependsOnImpl
import info.u_team.gradle_files_plugin.extension.FabricDependenciesExtensionImpl
import info.u_team.gradle_files_plugin.extension.ForgeDependencyExtensionImpl
import info.u_team.gradle_files_plugin.extension.SignJarExtensionImpl
import info.u_team.gradle_files_plugin.extension.VersionExtensionImpl

@CompileStatic
class GradleFilesExtension {
	
	private final GradleFilesPlugin plugin
	
	GradleFilesExtension(GradleFilesPlugin plugin) {
		this.plugin = plugin
	}
	
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
	 * @param task Jar task name to sign
	 * @return The sign task name
	 */
	def signJar(String taskName) {
		SignJarExtensionImpl.signJar(plugin.project, taskName)
	}
	
	/**
	 * Add sign task to default forge ouput jar
	 * @return The sign task name
	 */
	def signDefaultForgeJar() {
		SignJarExtensionImpl.signDefaultForgeJar(plugin.project)
	}
	
	/**
	 * Add sign task to default fabric output jar
	 * @return The sign task name
	 */
	def signDefaultFabricJar() {
		SignJarExtensionImpl.signDefaultFabricJar(plugin.project)
	}
	
	/**
	 * Return the forge dependency
	 * @return Forge dependency
	 */
	def forgeDependency() {
		ForgeDependencyExtensionImpl.forgeDependency(plugin.project)
	}
	
	/**
	 * Return the fabric minecraft dependency
	 * @return Fabric minecraft dependency
	 */
	def fabricMinecraftDependency() {
		FabricDependenciesExtensionImpl.fabricMinecraftDependency(plugin.project)
	}
	
	/**
	 * Return the fabric loader dependency
	 * @return Fabric loader dependency
	 */
	def fabricLoaderDependency() {
		FabricDependenciesExtensionImpl.fabricLoaderDependency(plugin.project)
	}
	
	/**
	 * Return the fabric api dependency
	 * @return Fabric api dependency
	 */
	def fabricApiDependency() {
		FabricDependenciesExtensionImpl.fabricApiDependency(plugin.project)
	}
	
	/**
	 * Create custom reobf Task (forge gradle) for a jar task
	 * @param task Jar task
	 * @return Reobf task
	 */
	def createReobfJar(final Jar task) {
		CreateReobfJarExtensionImpl.createReobfJar(plugin.project, task)
	}
	
	/**
	 * Return the archive base name
	 * @return Archive base name
	 */
	def archivesBaseName() {
		ArchiveBaseNameExtensionImpl.archivesBaseName(plugin.project)
	}
	
	/**
	 * Return the version
	 * @return Version
	 */
	def version() {
		VersionExtensionImpl.version(plugin.project)
	}
	
	/**
	 * Return the changelog url
	 * @return Changelog url
	 */
	def changelogUrl() {
		ChangelogUrlImpl.changelogUrl(plugin.project)
	}
	
	/**
	 * Mark the tasks as a dependency for the assemble task
	 * @param dependTask Tasks
	 */
	void assembleDependOn(final Object... dependTask) {
		DependsOnImpl.assembleDependOn(plugin.project, dependTask)
	}
	
	/**
	 * Mark the tasks as a dependency for all publishing task
	 * @param dependTask
	 */
	void allPublishingDependOn(final Object... dependTask) {
		DependsOnImpl.allPublishingDependOn(plugin.project, dependTask)
	}
}