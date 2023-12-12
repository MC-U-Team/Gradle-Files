package info.u_team.gradle_files_plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property

import groovy.transform.CompileStatic

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
}
