package info.u_team.gradle_files_plugin.test

import org.gradle.testfixtures.ProjectBuilder

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesPluginTest {
	
	static void main(String[] args) {
		def project = ProjectBuilder.builder().withName("Gradle Files Plugin Test").withProjectDir(new File("D:\\Programmieren\\Java\\Forge\\U Team\\Gradle Files\\Gradle-Files\\test")).build();
		project.getPluginManager().apply(GradleFilesPlugin)
		
		project.logger.lifecycle(project.config.test)
		
		project.logger.lifecycle("Project finished")
	}
}
