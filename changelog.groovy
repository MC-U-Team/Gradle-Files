ext.generateChangelogUrl = {
	->
	String url = "https://github.com/MC-U-Team/${config.github.name}/blob/${config.github.branch}/CHANGELOG.md"
	String mcversion = "${config.forge.mcversion}".replace(".", "")
	String version = "${project.version}".replace(".", "")
	String date =new Date().format("yyyy-MM-dd");
	return url + "#" + mcversion + "-" + version + "---" + date
}