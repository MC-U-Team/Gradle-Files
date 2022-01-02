package info.u_team.gradle_files_plugin.test

import org.gradle.testfixtures.ProjectBuilder

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesPluginTest {
	
	static void main(String[] args) {
		def project = ProjectBuilder.builder().build();
		project.getPluginManager().apply(GradleFilesPlugin)
		
		project.logger.lifecycle("Project finished")
	}
}
