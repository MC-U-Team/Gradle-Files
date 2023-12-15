package info.u_team.gradle_files_plugin.project.tool

import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.javadoc.Javadoc

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.Constants
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@CompileStatic
class GeneralTaskSettingsTool {

	static void setup(final Project project) {
		final def tasks = project.tasks

		// Set utf 8 as default encoding for compile tasks
		tasks.withType(JavaCompile).configureEach { task ->
			task.options.encoding = "UTF-8"
		}

		// Set utf 8 as default encoding for javadoc tasks
		tasks.withType(Javadoc).configureEach { task ->
			task.options.encoding = "UTF-8"
		}

		// Check when artifact is published if build property is set
		if(GradleFilesUtil.isMainProject(project)) {
			project.gradle.taskGraph.whenReady { TaskExecutionGraph graph ->
				final def hasPublishTask = graph.allTasks.any { task ->
					task.name.startsWith(Constants.PUBLISH_EXTERNAL_TASK) || task.name.startsWith(Constants.CURSEFORGE_EXTERNAL_TASK)
				}

				if(hasPublishTask) {
					if(!project.hasProperty(Constants.BUILD_PROPERTY)) {
						throw new GradleException("Publishing artifacts is only allowed when property ${Constants.BUILD_PROPERTY} is supplied.")
					}
				}
			}
		}
	}
}
