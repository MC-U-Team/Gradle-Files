package info.u_team.gradle_files_plugin.util

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.publish.plugins.PublishingPlugin
import org.gradle.language.base.plugins.LifecycleBasePlugin

import groovy.transform.CompileStatic

@CompileStatic
class DependencyUtil {

	static void assembleDependOn(final Project project, final Object... dependTask) {
		project.tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME) { task ->
			task.dependsOn(dependTask)
		}
	}

	static void allPublishingDependOn(final Project project, final Object... dependTask) {
		project.tasks.matching { Task task ->
			task.group == PublishingPlugin.PUBLISH_TASK_GROUP // Stupid check for publishing, but found no other way
		}.configureEach { task ->
			task.dependsOn(dependTask)
		}
	}

	static void allUploadDependOn(final Project project, final Object... dependTask) {
		project.tasks.matching { Task task ->
			task.group == "upload"
		}.configureEach { task ->
			task.dependsOn(dependTask)
		}
	}

	static void allBuildingDependOn(final Project project, final Object... dependTask) {
		assembleDependOn(project, dependTask)
		allPublishingDependOn(project, dependTask)
		allUploadDependOn(project, dependTask)
	}

	static void findTaskByName(final Project project, final String name, final Action<Task> action) {
		final def task = project.tasks.findByName(name)
		if(task) {
			action.execute(task)
		}
	}
}
