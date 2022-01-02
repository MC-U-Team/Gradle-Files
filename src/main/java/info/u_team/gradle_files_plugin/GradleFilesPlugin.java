package info.u_team.gradle_files_plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;

import info.u_team.gradle_files_plugin.tool.PrintJVMInformationTool;

public class GradleFilesPlugin implements Plugin<Project> {
	
	private static GradleFilesPlugin INSTANCE;
	
	public GradleFilesPlugin() {
		INSTANCE = this;
	}
	
	private Logger logger;
	
	@Override
	public void apply(Project project) {
		logger = project.getLogger();
		new PrintJVMInformationTool().run(this);
	}
	
	public static GradleFilesPlugin getInstance() {
		return INSTANCE;
	}
	
	public Logger getLogger() {
		return logger;
	}
	
}
