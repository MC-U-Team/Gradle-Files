def directory = buildscript.sourceFile.getParent()

apply from: "$directory/load-config.groovy"
apply from: "$directory/execute.groovy"
apply from: "$directory/rename-run-configuration.groovy"
apply from: "$directory/info.groovy"
apply from: "$directory/build-number.groovy"
apply from: "$directory/project-methods.groovy"
apply from: "$directory/changelog.groovy"
apply from: "$directory/manifest.groovy"
apply from: "$directory/sign-jar.groovy"
apply from: "$directory/tag-release.groovy"

repositories {
	maven {
		url = "https://repo.u-team.info"
	}
}

loadConfig()

println ""
printInfo()
println ""

getBuildNumber()
println ""

tasks.withType(JavaCompile) {
	options.encoding = "UTF-8"
}
