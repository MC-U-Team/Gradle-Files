package info.u_team.gradle_files_plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GradleFilesPlugin implements Plugin<Project> {
	
	@Override
	public void apply(Project project) {
		System.out.println(project);
	}
	
}
