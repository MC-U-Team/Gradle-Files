ext.normalManifest = {
	attributes(
			"Specification-Title": config.mod.name,
			"Specification-Vendor": "Team UTeam",
			"Specification-Version": config.mod.version,
			"Built-On": config.forge.mcversion,
			"Implementation-Title": config.mod.name,
			"Implementation-Version": project.version,
			"Implementation-Vendor": "Team UTeam",
			"Implementation-Timestamp": new Date().format("yyyy-MM-dd'T'HH:mm:ssZ"),
			"Fingerprint": project.findProperty("keystore.fingerprint") ?: "NONE",
			"Automatic-Module-Name": "${project.group}.${config.mod.modid}"
			)
}