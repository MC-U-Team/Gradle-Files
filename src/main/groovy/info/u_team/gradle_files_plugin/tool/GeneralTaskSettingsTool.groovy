package info.u_team.gradle_files_plugin.tool

import javax.tools.JavaCompiler

import org.gradle.api.tasks.compile.JavaCompile

import info.u_team.gradle_files_plugin.GradleFilesPlugin

class GeneralTaskSettingsTool {
	
	static void setup(final GradleFilesPlugin plugin) {
		//
		plugin.project.tasks.withType(JavaCompile) { task ->
			println(task)
		}
	}
}
