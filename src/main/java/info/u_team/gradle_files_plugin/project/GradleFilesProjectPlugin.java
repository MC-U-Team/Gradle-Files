package info.u_team.gradle_files_plugin.project;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import info.u_team.gradle_files_plugin.project.tool.FetchGitBuildNumber;
import info.u_team.gradle_files_plugin.project.tool.GeneralTaskSettingsTool;
import info.u_team.gradle_files_plugin.project.tool.LoadConfigTool;
import info.u_team.gradle_files_plugin.project.tool.PrintModInformationTool;
import info.u_team.gradle_files_plugin.project.tool.RegisterReleaseTasks;
import info.u_team.gradle_files_plugin.project.tool.SetupPluginEnvironmentTool;
import info.u_team.gradle_files_plugin.project.tool.SetupSpecialModLoaderSettingsTool;

public abstract class GradleFilesProjectPlugin implements Plugin<Project> {

	@Override
	public void apply(Project project) {
		project.getExtensions().create(Constants.EXTENSION_NAME, GradleFilesExtension.class, project);

		SetupPluginEnvironmentTool.setup(project);
		LoadConfigTool.load(project);
		PrintModInformationTool.print(project);
		FetchGitBuildNumber.fetch(project);
		RegisterReleaseTasks.register(project);
		GeneralTaskSettingsTool.setup(project);

		SetupSpecialModLoaderSettingsTool.setup(project);
	}

}
