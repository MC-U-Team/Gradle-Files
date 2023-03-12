package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.GradleFilesExtension

class DefaultJarExtensionImpl {
	
	static void defaultJar(final Project project, final GradleFilesExtension extension, Jar task) {
		task.from("LICENSE")
		task.exclude(".cache")
		
		task.manifest(DefaultManifestExtensionImpl.defaultManifest(project, extension))
	}
}
