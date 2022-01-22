package info.u_team.gradle_files_plugin.extension

import org.gradle.jvm.tasks.Jar

class DefaultJarExtensionImpl {
	
	static void defaultJar(Jar task) {
		task.from("LICENSE")
		task.exclude(".cache")
		
		task.manifest(DefaultManifestExtensionImpl.defaultManifest())
	}
}
