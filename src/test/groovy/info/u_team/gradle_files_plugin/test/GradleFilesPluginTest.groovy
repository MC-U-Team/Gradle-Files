package info.u_team.gradle_files_plugin.test

import javax.tools.JavaCompiler

import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.testfixtures.ProjectBuilder

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import net.minecraftforge.gradle.common.util.MinecraftExtension

class GradleFilesPluginTest {
	
	static void main(String[] args) {
		final def project = ProjectBuilder.builder().withName("Gradle Files Plugin Test").withProjectDir(new File(System.properties."gradle.project_dir")).build();
		
		// Apply plugin
		project.pluginManager.apply(GradleFilesPlugin)
		
		// Setup extension
		project.gradlefiles.configFile = project.file("build2.properties")
		
		// Setup forge gradle stuff
		project.dependencies.add("minecraft", "net.minecraftforge:forge:1.18.1-39.0.9")
		project.minecraft {
			mappings channel: "official", version: "1.18.1"
		}
		
		// Test stuff
		project.afterEvaluate {
			// Print value of loaded config
			project.logger.lifecycle("Config value is {}", project.config.myvalue)
			
			// Finished test
			project.logger.lifecycle("Test script evaluated")
		}
		
		//project.evaluate()
		println(project.tasks.getByName("build")) 
		project.defaultTasks("build")
		
		println(project.defaultTasks)
		
		println(project.extensions.extraProperties.properties)
		
		project.evaluate()
		
		project.logger.lifecycle("Finished plugin test")
	}
}
