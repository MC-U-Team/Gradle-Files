package info.u_team.gradle_files_plugin.test

import org.gradle.testfixtures.ProjectBuilder

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesPluginTest {
	
	static void main(String[] args) {
		def project = ProjectBuilder.builder().withName("Gradle Files Plugin Test").withProjectDir(new File(System.properties."gradle.project_dir")).build();
		project.getPluginManager().apply(GradleFilesPlugin)
		
		project.logger.lifecycle(project.config.myvalue)
		
		println("Project script finished")
	}
}
