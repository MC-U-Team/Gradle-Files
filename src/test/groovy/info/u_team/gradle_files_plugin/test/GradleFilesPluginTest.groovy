package info.u_team.gradle_files_plugin.test

import org.gradle.testfixtures.ProjectBuilder

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GradleFilesPluginTest {
	
	static void main(String[] args) {
		def project = ProjectBuilder.builder().withName("Gradle Files Plugin Test").withProjectDir(new File(System.properties."gradle.project_dir")).build();
		
		// Apply plugin
		project.pluginManager.apply(GradleFilesPlugin)
		
		// Setup extension
		project.extensions.gradlefiles.configFile = project.file("build2.properties")
		
		// Test stuff
		project.afterEvaluate {
			// Print value of loaded config
			project.logger.lifecycle("Config value is {}", project.config.myvalue)
			
			// Finished test
			project.logger.lifecycle("Test script evaluated")
		}
		
		project.evaluate()
		
		project.logger.lifecycle("Finished plugin test")
	}
}
