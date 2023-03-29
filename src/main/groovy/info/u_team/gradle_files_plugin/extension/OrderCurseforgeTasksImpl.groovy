package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project
import org.gradle.api.Task
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.util.DependencyUtil

@CompileStatic
class OrderCurseforgeTasksImpl {
	
	static def orderCurseforgeTasks(final Project project, final Project runLastProject, final Project runFirstProject) {
		orderTasks(runLastProject, runLastProject, runFirstProject, Constants.CURSEFORGE_EXTERNAL_TASK)
	}
	
	static def orderTasks(final Project project, final Project runLastProject, final Project runFirstProject, String taskStartName) {
		project.gradle.projectsEvaluated {
			final def runLast = runLastProject.tasks.findAll { Task task ->
				task.name.startsWith(taskStartName)
			}
			final def runFirst = runFirstProject.tasks.findAll { Task task ->
				task.name.startsWith(taskStartName)
			}
			runLast.each { lastTask ->
				runFirst.each { firstTask ->
					lastTask.mustRunAfter(firstTask)
				}
			}
		}
	}
}
