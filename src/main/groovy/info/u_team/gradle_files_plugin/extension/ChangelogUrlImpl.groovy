package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project

import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class ChangelogUrlImpl {
	
	static def changelogUrl(final Project project) {
		final def config = GradleFilesUtil.getProjectConfig(project)
		
		// TODO change hardcoded path to extension in gradlefiles?
		final def url = "https://github.com/MC-U-Team/${config.github.name}/blob/${config.github.branch}/CHANGELOG.md"
		final def mcversion = "${config.minecraft.version}".replace(".", "")
		final def version = "${project.version}".replace(".", "")
		final def date = new Date().format("yyyy-MM-dd")
		return "${url}#${mcversion}-${version}---${date}"
	}
}
