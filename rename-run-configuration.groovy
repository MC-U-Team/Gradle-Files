getGradle().projectsEvaluated {
	def extension = project.getExtensions().getByName("minecraft")
	extension.getRuns().each { run ->
		run.setTaskName(name + "_" + run.getTaskName())
	}
}