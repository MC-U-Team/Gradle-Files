package info.u_team.gradle_files_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.jvm.tasks.Jar

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.GradleFilesUtil
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace

class CreateReobfJarExtensionImpl {
	
	static def createReobfJar(final Project project, final Jar task) {
		final def reobfTask = project.extensions.findByName("reobf").create(task.name)
		DependencyUtil.allPublishingDependOn(project, reobfTask)
		
		return reobfTask
	}
}
