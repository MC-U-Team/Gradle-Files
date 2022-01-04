package info.u_team.gradle_files_plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import info.u_team.gradle_files_plugin.tool.GeneralTaskSettingsTool;
import info.u_team.gradle_files_plugin.tool.LoadConfigTool;
import info.u_team.gradle_files_plugin.tool.PrintJVMInformationTool;
import info.u_team.gradle_files_plugin.tool.SetupPluginEnvironmentTool;

public class GradleFilesPlugin implements Plugin<Project> {
	
	private Project project;
	private Logger logger;
	private GradleFilesExtension extension;
	
	@Override
	public void apply(Project project) {
		this.project = project;
		this.logger = project.getLogger();
		this.extension = project.getExtensions().create(Constants.EXTENSION_NAME, GradleFilesExtension.class);
		
		SetupPluginEnvironmentTool.setup(this);
		PrintJVMInformationTool.print(this);
		LoadConfigTool.load(this);
		
		project.afterEvaluate((unused_) -> {
			GeneralTaskSettingsTool.setup(this);
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
