package info.u_team.gradle_files_plugin.project

import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.api.tasks.TaskProvider
import org.gradle.jvm.tasks.Jar

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.GradleFilesExtension
import info.u_team.gradle_files_plugin.project.extension.ArchiveNameExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.ChangelogUrlImpl
import info.u_team.gradle_files_plugin.project.extension.DefaultJarExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.DefaultManifestExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.DependsOnImpl
import info.u_team.gradle_files_plugin.project.extension.DisplayNameExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.FabricDependenciesExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.ForgeDependencyExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.NeoForgeDependencyExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.OrderCurseforgeTasksImpl
import info.u_team.gradle_files_plugin.project.extension.VersionExtensionImpl

@CompileStatic
abstract class GradleFilesExtension {

	private final Project project

	GradleFilesExtension(final Project project) {
		this.project = project;
		getVendor().convention("No Vendor");
		getLoaderSuffix().convention("");
	}

	/**
	 * Should be set to configure the vendor of the build
	 */
	abstract Property<String> getVendor()

	/**
	 * Mod loader suffix
	 */
	abstract Property<String> getLoaderSuffix()


	/**
	 * Apply values from the extension to this extension as convention
	 * @param extension Extension
	 */
	void apply(final GradleFilesExtension extension) {
		this.vendor.convention(extension.vendor)
		this.loaderSuffix.convention(extension.loaderSuffix)
	}

	/**
	 * Return the archive name
	 * @return Archive name
	 */
	def archivesName() {
		ArchiveNameExtensionImpl.archivesName(project, this)
	}

	/**
	 * Return the version
	 * @return Version
	 */
	def version() {
		VersionExtensionImpl.version(project)
	}

	/**
	 * Return the display version
	 * @return Display name
	 */
	def displayName() {
		DisplayNameExtensionImpl.displayName(project, this)
	}

	/**
	 * Return the changelog url
	 * @return Changelog url
	 */
	def changelogUrl() {
		ChangelogUrlImpl.changelogUrl(project)
	}

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
	void defaultJar(final Jar task) {
		DefaultJarExtensionImpl.defaultJar(project, this, task)
	}

	/**
	 * Return the neoforge dependency
	 * @return Forge dependency
	 */
	def neoForgeDependency() {
		NeoForgeDependencyExtensionImpl.neoForgeDependency(project)
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
	 * Mark the tasks as a dependency for the assemble task
	 * @param dependTask Tasks
	 */
	void assembleDependOn(final Object... dependTask) {
		DependsOnImpl.assembleDependOn(project, dependTask)
	}

	/**
	 * Mark the tasks as a dependency for all publishing tasks
	 * @param dependTask Tasks
	 */
	void allPublishingDependOn(final Object... dependTask) {
		DependsOnImpl.allPublishingDependOn(project, dependTask)
	}

	/**
	 * Mark the tasks as a dependency for all upload tasks
	 * @param dependTask Tasks
	 */
	void allUploadDependOn(final Object... dependTask) {
		DependsOnImpl.allUploadDependOn(project, dependTask)
	}

	/**
	 * Mark the tasks as a dependency for all building tasks
	 * Has the same effect as calling the following methods individually with the same values
	 * @see GradleFilesExtension#assembleDependOn
	 * @see GradleFilesExtension#allPublishingDependOn
	 * @see GradleFilesExtension#allUploadDependOn
	 * @param dependTask Tasks
	 */
	void allBuildingDependOn(final Object... dependTask) {
		DependsOnImpl.allBuildingDependOn(project, dependTask)
	}

	/**
	 * Mark all curseforge tasks that start with the specified name to run in the reversed order the projects are supplied.
	 * @param orderProjects Projects
	 */
	void orderCurseforgeTasks(final Project ... orderProjects) {
		OrderCurseforgeTasksImpl.orderCurseforgeTasks(project, orderProjects)
	}

	/**
	 * Mark all tasks that start with the specified name to run in the reversed order the projects are supplied.
	 * @param taskStartName Tasks start name
	 * @param orderProjects Projects
	 */
	void orderTasks(String taskStartName, final Project ... orderProjects) {
		OrderCurseforgeTasksImpl.orderTasks(project, taskStartName, orderProjects)
	}
}
