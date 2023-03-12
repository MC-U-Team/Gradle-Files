package info.u_team.gradle_files_plugin;

import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import info.u_team.gradle_files_plugin.tool.FetchGitBuildNumber;
import info.u_team.gradle_files_plugin.tool.GeneralTaskSettingsTool;
import info.u_team.gradle_files_plugin.tool.LoadConfigTool;
import info.u_team.gradle_files_plugin.tool.PrintJVMInformationTool;
import info.u_team.gradle_files_plugin.tool.PrintModInformationTool;
import info.u_team.gradle_files_plugin.tool.RegisterReleaseTasks;
import info.u_team.gradle_files_plugin.tool.RemovedMappedDependenciesTool;
import info.u_team.gradle_files_plugin.tool.RenameRunConfigurationTool;
import info.u_team.gradle_files_plugin.tool.SetupPluginEnvironmentTool;
import info.u_team.gradle_files_plugin.util.GradleFilesUtil;

public class GradleFilesPlugin implements Plugin<Project> {
	
	private Project project;
	private Logger logger;
	private GradleFilesExtension extension;
	
	@Override
	public void apply(Project project) {
		this.project = project;
		this.logger = project.getLogger();
		this.extension = project.getExtensions().create(Constants.EXTENSION_NAME, GradleFilesExtension.class, this);
		
		logger.lifecycle("--------------------------------------------------------------------------------------------------");
		logger.lifecycle("isRootProject:" + GradleFilesUtil.isRootProject(project));
		logger.lifecycle("--------------------------------------------------------------------------------------------------");
		
		new GradleException("ERROR!!");
		
		SetupPluginEnvironmentTool.setup(this);
		PrintJVMInformationTool.print(this);
		LoadConfigTool.load(this);
		PrintModInformationTool.print(this);
		FetchGitBuildNumber.fetch(this);
		
		project.afterEvaluate((unused_) -> {
			RegisterReleaseTasks.register(this);
			RenameRunConfigurationTool.rename(this);
			GeneralTaskSettingsTool.setup(this);
			RemovedMappedDependenciesTool.remove(this);
		});
		
	}
	
	public Project getProject() {
		return project;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
	public GradleFilesExtension getExtension() {
		return extension;
	}
	
}
