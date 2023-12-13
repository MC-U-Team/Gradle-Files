package info.u_team.sign_jar_plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import info.u_team.gradle_files_plugin.Constants;

public class SignJarPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getExtensions().create(Constants.SIGN_JAR_EXTENSION_NAME, SignJarExtension.class, project);
	}

}
