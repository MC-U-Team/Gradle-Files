package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project
import org.gradle.jvm.tasks.Jar

import info.u_team.gradle_files_plugin.util.DependencyUtil

class CreateReobfJarExtensionImpl {
	
	static def createReobfJar(final Project project, final Jar task) {
		final def reobfTask = project.extensions.findByName("reobf").create(task.name)
		DependencyUtil.allPublishingDependOn(project, reobfTask)
		
		return reobfTask
	}
}
