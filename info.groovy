ext.printInfo = {
	->
	println "Java: " + System.getProperty("java.version")
	println "JVM: " + System.getProperty("java.vm.version") + "(" + System.getProperty("java.vendor") + ")"
	println "Arch: " + System.getProperty("os.arch")
}
