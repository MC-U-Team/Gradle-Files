package info.u_team.gradle_files_plugin.tool

import javax.tools.JavaCompiler

import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc

import groovy.json.JsonGenerator.Options
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GeneralTaskSettingsTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		// Set utf 8 as default encoding for compile tasks
		plugin.project.tasks.withType(JavaCompile) { task ->
			task.options.encoding = "UTF-8"
		}
		
		// Set utf 8 as default encoding for javadoc tasks
		plugin.project.tasks.withType(Javadoc) { task ->
			task.options.encoding = "UTF-8"
		}
	}
}
