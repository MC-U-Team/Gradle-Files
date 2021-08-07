ext.generateArchivesBaseName = {
	->
	return "${config.mod.filename}-${config.forge.mcversion}"
}

ext.generateVersion = {
	->
	return "${config.mod.version}.${config.buildnumber}${getAppendix()}"
}

def getAppendix() {
	if("${config.mod.snapshot}".toBoolean()) {
		return "-SNAPSHOT"
	} else {
		return ""
	}
}

ext.getValue = { name ->
	def envVariable = System.getenv(name)
	if(envVariable != null) {
		return envVariable
	} else {
		if (project.hasProperty(name)) {
			return project.getProperty(name)
		}
	}
	return null
}

ext.getValueDefault = { name ->
	def value = getValue(name)
	if(value == null) {
		return "noValue"
	}
	return value
}
