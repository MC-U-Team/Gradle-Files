package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class ChangelogUrlImpl {
	
	static def changelogUrl() {
		final def (Project project, config) = GradleFilesUtil.getProjectProperties()
		
		// TODO change hardcoded path to extension in gradlefiles?
		final def url = "https://github.com/MC-U-Team/${config.github.name}/blob/${config.github.branch}/CHANGELOG.md"
		final def mcversion = "${config.forge.mcversion}".replace(".", "")
		final def version = "${project.version}".replace(".", "")
		final def date = new Date().format("yyyy-MM-dd")
		return "${url}#${mcversion}-${version}---${date}"
	}
}
