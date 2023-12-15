package info.u_team.gradle_files_plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.initialization.Settings;

import info.u_team.gradle_files_plugin.project.GradleFilesProjectPlugin;
import info.u_team.gradle_files_plugin.settings.GradleFilesSettingsPlugin;

public abstract class GradleFilesPlugin implements Plugin<Object> {

	@Override
	public void apply(Object object) {
		if (object instanceof Project) {
			final Project project = (Project) object;
			project.getPluginManager().apply(GradleFilesProjectPlugin.class);
		} else if (object instanceof Settings) {
			final Settings settings = (Settings) object;
			settings.getPluginManager().apply(GradleFilesSettingsPlugin.class);
		}
	}

}
