package info.u_team.gradle_files_plugin.tool

import org.gradle.api.artifacts.repositories.MavenArtifactRepository

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin

class AddRepositoryTool {
	
	static void add(final GradleFilesPlugin plugin) {
		plugin.project.repositories.maven { maven ->
			maven.name = Constants.U_TEAM_MAVEN_NAME
			maven.url = Constants.U_TEAM_MAVEN_URL
		}
	}
}
