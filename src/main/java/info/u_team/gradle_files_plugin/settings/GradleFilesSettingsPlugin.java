package info.u_team.gradle_files_plugin.settings;

import org.gradle.api.Plugin;
import org.gradle.api.initialization.Settings;

import info.u_team.gradle_files_plugin.Constants;

public abstract class GradleFilesSettingsPlugin implements Plugin<Settings> {

	@Override
	public void apply(Settings settings) {
		settings.getExtensions().create(Constants.EXTENSION_NAME, GradleFilesSettingsExtension.class, settings);
	}

}
