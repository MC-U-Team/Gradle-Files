ext.loadConfig = {
	->
	ext.buildProps = file "build.properties"
	
	buildProps.withReader {
		def prop = new Properties()
		prop.load(it)
		ext.config = new ConfigSlurper().parse prop
	}
}