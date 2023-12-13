package info.u_team.gradle_files_plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import info.u_team.gradle_files_plugin.tool.FetchGitBuildNumber;
import info.u_team.gradle_files_plugin.tool.LoadConfigTool;
import info.u_team.gradle_files_plugin.tool.PrintModInformationTool;
import info.u_team.gradle_files_plugin.tool.SetupPluginEnvironmentTool;

public class GradleFilesPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getExtensions().create(Constants.EXTENSION_NAME, GradleFilesExtension.class, project);

		SetupPluginEnvironmentTool.setup(project);
		LoadConfigTool.load(project);
		PrintModInformationTool.print(project);
		FetchGitBuildNumber.fetch(project);
	}

}
