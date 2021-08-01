task tagRelease {
	doLast {
		validateBuild()
		def tagName = "${config.forge.mcversion}-${project.version}"
		println "Tag release commit with tag ${tagName}"
		execute("git tag ${tagName}")
		execute("git push origin ${tagName}")
	}
}