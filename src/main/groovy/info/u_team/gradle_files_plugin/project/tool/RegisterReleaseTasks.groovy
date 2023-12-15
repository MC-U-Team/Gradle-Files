package info.u_team.gradle_files_plugin.project.tool

import org.gradle.api.Project

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.Constants
import info.u_team.gradle_files_plugin.project.task.CheckGitTask
import info.u_team.gradle_files_plugin.project.task.CreateGitTagTask
import info.u_team.gradle_files_plugin.project.task.ReleaseModTask
import info.u_team.gradle_files_plugin.project.task.UpdateGitBuildNumberTask
import info.u_team.gradle_files_plugin.project.util.DependencyUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@CompileStatic
class RegisterReleaseTasks {

	static void register(final Project project) {
		if(GradleFilesUtil.isMultiLoaderProject(project)) {
			if(GradleFilesUtil.isMainProject(project)) {
				handleMultiLoaderMainProject(project)
			} else {
				handleMultiLoaderLoaderProject(project)
			}
		} else {
			handleNormalProject(project)
		}
	}

	private static void handleNormalProject(final Project project) {
		final def tasks = project.tasks

		// Register checkGit task
		DependencyUtil.allBuildingDependOn(project, tasks.register(Constants.CHECK_GIT_TASK, CheckGitTask))

		// Register updateBuildNumber task
		tasks.register(Constants.UPDATE_BUILD_NUMBER_TASK, UpdateGitBuildNumberTask).configure { task ->
			DependencyUtil.findTaskByName(project, Constants.PUBLISH_EXTERNAL_TASK, task.&mustRunAfter)
			DependencyUtil.findTaskByName(project, Constants.CURSEFORGE_EXTERNAL_TASK, task.&mustRunAfter)
		}

		// Register createGitTag task
		tasks.register(Constants.CREATE_GIT_TAG_TASK, CreateGitTagTask).configure { task ->
			DependencyUtil.findTaskByName(project, Constants.PUBLISH_EXTERNAL_TASK, task.&mustRunAfter)
			DependencyUtil.findTaskByName(project, Constants.CURSEFORGE_EXTERNAL_TASK, task.&mustRunAfter)
			task.dependsOn(tasks.named(Constants.CHECK_GIT_TASK))
		}

		// Register releaseMod task
		tasks.register(Constants.RELEASE_MOD_TASK, ReleaseModTask).configure { task ->
			task.group = "release"

			DependencyUtil.findTaskByName(project, Constants.PUBLISH_EXTERNAL_TASK, task.&dependsOn)
			DependencyUtil.findTaskByName(project, Constants.CURSEFORGE_EXTERNAL_TASK, task.&dependsOn)
			task.dependsOn(tasks.named(Constants.CREATE_GIT_TAG_TASK))
			task.dependsOn(tasks.named(Constants.UPDATE_BUILD_NUMBER_TASK))
		}
	}

	private static void handleMultiLoaderMainProject(final Project project) {
		final def tasks = project.tasks

		// Register checkGit task
		tasks.register(Constants.CHECK_GIT_TASK, CheckGitTask)

		// Register updateBuildNumber task
		tasks.register(Constants.UPDATE_BUILD_NUMBER_TASK, UpdateGitBuildNumberTask)

		// Register createGitTag task
		tasks.register(Constants.CREATE_GIT_TAG_TASK, CreateGitTagTask).configure { task ->
			task.dependsOn(tasks.named(Constants.CHECK_GIT_TASK))
		}

		// Register releaseMod task
		tasks.register(Constants.RELEASE_MOD_TASK, ReleaseModTask).configure { task ->
			task.group = "release"

			task.dependsOn(tasks.named(Constants.CREATE_GIT_TAG_TASK))
			task.dependsOn(tasks.named(Constants.UPDATE_BUILD_NUMBER_TASK))
		}
	}

	private static void handleMultiLoaderLoaderProject(final Project project) {
		final def mainProject = GradleFilesUtil.getMainProject(project)
		final def mainTasks = mainProject.tasks

		// Register dependencies for checkGitTask in main project
		DependencyUtil.allBuildingDependOn(project, mainTasks.named(Constants.CHECK_GIT_TASK))

		// Register dependencies for updateBuildNumber in main project
		mainTasks.named(Constants.UPDATE_BUILD_NUMBER_TASK).configure { task ->
			DependencyUtil.findTaskByName(project, Constants.PUBLISH_EXTERNAL_TASK, task.&mustRunAfter)
			DependencyUtil.findTaskByName(project, Constants.CURSEFORGE_EXTERNAL_TASK, task.&mustRunAfter)
		}

		// Register dependencies for createGitTag in main project
		mainTasks.named(Constants.CREATE_GIT_TAG_TASK).configure { task ->
			DependencyUtil.findTaskByName(project, Constants.PUBLISH_EXTERNAL_TASK, task.&mustRunAfter)
			DependencyUtil.findTaskByName(project, Constants.CURSEFORGE_EXTERNAL_TASK, task.&mustRunAfter)
		}

		// Register dependencies for releaseMod in main project
		mainTasks.named(Constants.RELEASE_MOD_TASK).configure { task ->
			DependencyUtil.findTaskByName(project, Constants.PUBLISH_EXTERNAL_TASK, task.&dependsOn)
			DependencyUtil.findTaskByName(project, Constants.CURSEFORGE_EXTERNAL_TASK, task.&dependsOn)
		}
	}
}
