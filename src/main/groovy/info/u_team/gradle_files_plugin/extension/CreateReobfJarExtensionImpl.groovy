package info.u_team.gradle_files_plugin.extension

import org.apache.commons.lang3.StringUtils
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.jvm.tasks.Jar

import info.u_team.gradle_files_plugin.util.GradleFilesUtil
import net.minecraftforge.gradle.userdev.tasks.RenameJarInPlace

class CreateReobfJarExtensionImpl {
	
	static def createReobfJar(Jar task) {
		final def (Project project) = GradleFilesUtil.getProjectProperties()
		
		final def reobfTask = project.extensions.findByName("reobf").create(task.name)
		allPublishingDependOn(project, reobfTask)
	}
	
	static def allPublishingDependOn(final Project project, final Task reobfTask) {
		project.tasks.matching { task ->
			task.group == PublishingPlugin.PUBLISH_TASK_GROUP // Stupid check for publishing, but found no other way
		}.each { task ->
			task.dependsOn(reobfTask)
		}
	}
}
