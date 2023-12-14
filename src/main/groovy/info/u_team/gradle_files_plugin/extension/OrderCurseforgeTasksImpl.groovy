package info.u_team.gradle_files_plugin.extension

import org.gradle.api.Project
import org.gradle.api.Task

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants

@CompileStatic
class OrderCurseforgeTasksImpl {

	static void orderCurseforgeTasks(final Project project, final Project ... orderProjects) {
		orderTasks(project, Constants.CURSEFORGE_EXTERNAL_TASK, orderProjects)
	}

	static void orderTasks(final Project project, String taskStartName, final Project ... orderProjects) {
		project.gradle.projectsEvaluated {
			final def tasksInProjects = orderProjects.collect { orderProject ->
				orderProject.tasks.matching { Task task ->
					task.name.startsWith(taskStartName)
				}
			}

			for(int index = 0; index < tasksInProjects.size(); index ++) {
				if(index + 1 < tasksInProjects.size()) {
					final def runAfter = tasksInProjects.get(index);
					final def runBefore = tasksInProjects.get(index + 1);

					runAfter.configureEach { runAfterTask ->
						runBefore.configureEach { runBeforeTask ->
							runAfterTask.mustRunAfter(runBeforeTask)
						}
					}
				}
			}
		}
	}
}
