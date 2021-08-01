apply from: "./load-config.groovy"
apply from: "./execute.groovy"
apply from: "./rename-run-configuration.groovy"
apply from: "./info.groovy"
apply from: "./build-number.groovy"
apply from: "./project-methods.groovy"
apply from: "./changelog.groovy"
apply from: "./manifest.groovy"
apply from: "./sign-jar.groovy"
apply from: "./tag-release.groovy"

repositories {
	maven { url = "https://repo.u-team.info" }
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
