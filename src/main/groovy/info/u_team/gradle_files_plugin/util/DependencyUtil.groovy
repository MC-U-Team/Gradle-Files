package info.u_team.gradle_files_plugin.util

import org.gradle.api.Project
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.language.base.plugins.LifecycleBasePlugin

class DependencyUtil {
	
	static def assembleDependOn(final Project project, final Object... dependTask) {
		project.tasks.findByName(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).dependsOn(dependTask)
	}
	
	static def allPublishingDependOn(final Project project, final Object... dependTask) {
		project.tasks.matching { task ->
			task.group == PublishingPlugin.PUBLISH_TASK_GROUP // Stupid check for publishing, but found no other way
		}.each { task ->
			task.dependsOn(dependTask)
		}
	}
	
	static def allUploadDependOn(final Project project, final Object... dependTask) {
		project.tasks.matching { task ->
			task.group == "upload"
		}.each { task ->
			task.dependsOn(dependTask)
		}
	}
}
