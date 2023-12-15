package info.u_team.gradle_files_plugin.settings

import org.gradle.api.initialization.Settings

import groovy.transform.CompileStatic

@CompileStatic
abstract class GradleFilesSettingsExtension {

	private final Settings settings

	GradleFilesSettingsExtension(final Settings settings) {
		this.settings = settings;
	}
}
