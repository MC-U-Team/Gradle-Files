package info.u_team.gradle_files_plugin.project.tool

import org.gradle.api.Project

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@CompileStatic
class LoadConfigTool {

	static void load(final Project project) {
		if(GradleFilesUtil.isMainProject(project)) {
			handleMainProject(project)
		} else {
			handleLoaderProject(project)
		}
	}

	private static void handleMainProject(final Project project) {
		project.file(Constants.CONFIG_FILE).withReader { reader ->
			final def properties = new Properties()
			properties.load(reader)

			final def config = new ConfigSlurper().parse(properties)
			GradleFilesUtil.getExtraProperties(project).set("config", config)
		}
	}

	private static void handleLoaderProject(final Project project) {
		GradleFilesUtil.getExtraProperties(project).set("config", GradleFilesUtil.getProjectConfig(GradleFilesUtil.getMainProject(project)))
	}
}
