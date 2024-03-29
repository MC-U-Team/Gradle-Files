package info.u_team.gradle_files_plugin.project

import org.gradle.api.Project
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.provider.Property
import org.gradle.jvm.tasks.Jar

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.extension.ArchiveNameExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.ChangelogUrlImpl
import info.u_team.gradle_files_plugin.project.extension.DefaultJarExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.DefaultManifestExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.DependsOnImpl
import info.u_team.gradle_files_plugin.project.extension.DisplayNameExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.FabricDependenciesExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.FilteredComponentExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.ForgeDependencyExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.NeoForgeDependencyExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.OrderTasksImpl
import info.u_team.gradle_files_plugin.project.extension.ProjectExtensionImpl
import info.u_team.gradle_files_plugin.project.extension.VersionExtensionImpl
import info.u_team.gradle_files_plugin.project.util.GradleFilesSoftwareComponent

@CompileStatic
abstract class GradleFilesProjectExtension {
	
	private final Project project
	
	GradleFilesProjectExtension(final Project project) {
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
	void apply(final GradleFilesProjectExtension extension) {
		this.vendor.convention(extension.vendor)
		this.loaderSuffix.convention(extension.loaderSuffix)
	}
	
	/**
	 * Retrieve sub project that is named with the gradle files settings plugin
	 * @param name Original name
	 * @return Project
	 */
	Project project(final String name) {
		ProjectExtensionImpl.project(project, name)
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
	 * Mark the tasks as a dependency for all building tasks
	 * Has the same effect as calling the following methods individually with the same values
	 * @see GradleFilesExtension#assembleDependOn
	 * @see GradleFilesExtension#allPublishingDependOn
	 * @param dependTask Tasks
	 */
	void allBuildingDependOn(final Object... dependTask) {
		DependsOnImpl.allBuildingDependOn(project, dependTask)
	}
	
	/**
	 * Mark all mod publish tasks that start with curseforge or publish to run in the reversed order the projects are supplied.
	 * @param orderProjects Projects
	 */
	void orderModPublishTasks(final Project ... orderProjects) {
		OrderTasksImpl.orderModPublishTasks(project, orderProjects)
	}
	
	/**
	 * Mark all tasks that start with the specified name to run in the reversed order the projects are supplied.
	 * @param taskStartName Tasks start name
	 * @param orderProjects Projects
	 */
	void orderTasks(String taskStartName, final Project ... orderProjects) {
		OrderTasksImpl.orderTasks(project, taskStartName, orderProjects)
	}
	
	/**
	 * Wraps the java component with a filter for dependencies that should not be published in pom and gradle metadata.
	 * Return true in filterDependency predicate to remove the dependency, else false
	 */
	GradleFilesSoftwareComponent filteredJavaComponent() {
		FilteredComponentExtensionImpl.filteredJavaComponent(project) {}
	}
	
	/**
	 * Wraps the java component with a filter for dependencies that should not be published in pom and gradle metadata.
	 * Return true in filterDependency predicate to remove the dependency, else false
	 * @param configureClosure Closure
	 */
	GradleFilesSoftwareComponent filteredJavaComponent(final @DelegatesTo(GradleFilesSoftwareComponent.class) Closure configureClosure) {
		FilteredComponentExtensionImpl.filteredJavaComponent(project, configureClosure)
	}
	
	/**
	 * Wraps a software component with a filter for dependencies that should not be published in pom and gradle metadata.
	 * Return true in filterDependency predicate to remove the dependency, else false
	 * @param component SoftwareComponent
	 */
	GradleFilesSoftwareComponent filteredComponent(final SoftwareComponent component) {
		FilteredComponentExtensionImpl.filteredComponent(component) {}
	}
	
	/**
	 * Wraps a software component with a filter for dependencies that should not be published in pom and gradle metadata.
	 * Return true in filterDependency predicate to remove the dependency, else false
	 * @param component SoftwareComponent
	 * @param configureClosure Closure
	 */
	GradleFilesSoftwareComponent filteredComponent(final SoftwareComponent component, final @DelegatesTo(GradleFilesSoftwareComponent.class) Closure configureClosure) {
		FilteredComponentExtensionImpl.filteredComponent(component, configureClosure)
	}
}
