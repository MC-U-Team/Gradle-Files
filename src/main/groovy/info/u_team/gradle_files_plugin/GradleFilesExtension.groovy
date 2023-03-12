package info.u_team.gradle_files_plugin

import org.gradle.api.Project
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
	private final Project project
	
	GradleFilesExtension(GradleFilesPlugin plugin) {
		this.plugin = plugin
		this.project = plugin.project
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
		DefaultManifestExtensionImpl.defaultManifest(project, this)
	}
	
	/**
	 * Include license file to build
	 * Exclude .cache file
	 * Also add the defaultManifest to the jar
	 * @param task Jar task
	 */
	void defaultJar(Jar task) {
		DefaultJarExtensionImpl.defaultJar(project, this, task)
	}
	
	/**
	 * Add a sign task to a jar task. Also sets the dependency for publishing and assemble
	 * @param task Jar task name to sign
	 * @return The sign task name
	 */
	def signJar(String taskName) {
		SignJarExtensionImpl.signJar(project, taskName)
	}
	
	/**
	 * Add sign task to default forge ouput jar
	 * @return The sign task name
	 */
	def signDefaultForgeJar() {
		SignJarExtensionImpl.signDefaultForgeJar(project)
	}
	
	/**
	 * Add sign task to default fabric output jar
	 * @return The sign task name
	 */
	def signDefaultFabricJar() {
		SignJarExtensionImpl.signDefaultFabricJar(project)
	}
	
	/**
	 * Return the forge dependency
	 * @return Forge dependency
	 */
	def forgeDependency() {
		ForgeDependencyExtensionImpl.forgeDependency(project)
	}
	
	/**
	 * Return the fabric minecraft dependency
	 * @return Fabric minecraft dependency
	 */
	def fabricMinecraftDependency() {
		FabricDependenciesExtensionImpl.fabricMinecraftDependency(project)
	}
	
	/**
	 * Return the fabric loader dependency
	 * @return Fabric loader dependency
	 */
	def fabricLoaderDependency() {
		FabricDependenciesExtensionImpl.fabricLoaderDependency(project)
	}
	
	/**
	 * Return the fabric api dependency
	 * @return Fabric api dependency
	 */
	def fabricApiDependency() {
		FabricDependenciesExtensionImpl.fabricApiDependency(project)
	}
	
	/**
	 * Create custom reobf Task (forge gradle) for a jar task
	 * @param task Jar task
	 * @return Reobf task
	 */
	def createReobfJar(final Jar task) {
		CreateReobfJarExtensionImpl.createReobfJar(project, task)
	}
	
	/**
	 * Return the archive base name
	 * @return Archive base name
	 */
	def archivesBaseName() {
		ArchiveBaseNameExtensionImpl.archivesBaseName(project)
	}
	
	/**
	 * Return the version
	 * @return Version
	 */
	def version() {
		VersionExtensionImpl.version(project)
	}
	
	/**
	 * Return the changelog url
	 * @return Changelog url
	 */
	def changelogUrl() {
		ChangelogUrlImpl.changelogUrl(project)
	}
	
	/**
	 * Mark the tasks as a dependency for the assemble task
	 * @param dependTask Tasks
	 */
	void assembleDependOn(final Object... dependTask) {
		DependsOnImpl.assembleDependOn(project, dependTask)
	}
	
	/**
	 * Mark the tasks as a dependency for all publishing task
	 * @param dependTask
	 */
	void allPublishingDependOn(final Object... dependTask) {
		DependsOnImpl.allPublishingDependOn(project, dependTask)
	}
}