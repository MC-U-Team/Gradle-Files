package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.task.CheckGitTask
import info.u_team.gradle_files_plugin.task.CreateGitTagTask
import info.u_team.gradle_files_plugin.task.ReleaseModTask
import info.u_team.gradle_files_plugin.task.UpdateGitBuildNumberTask
import info.u_team.gradle_files_plugin.util.DependencyUtil
import info.u_team.gradle_files_plugin.util.GradleFilesUtil

class RegisterReleaseTasks {
	
	static void register(final GradleFilesPlugin plugin) {
		if(GradleFilesUtil.isMultiLoaderProject(plugin.project)) {
			if(GradleFilesUtil.isMainProject(plugin.project)) {
				handleMultiLoaderMainProject(plugin)
			} else {
				handleMultiLoaderLoaderProject(plugin)
			}
		} else {
			handleNormalProject(plugin)
		}
	}
	
	private static void handleNormalProject(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def tasks = project.tasks
		
		// Register checkGit task
		tasks.register(Constants.CHECK_GIT_TASK, CheckGitTask).configure { task ->
			DependencyUtil.allPublishingDependOn(project, task)
			DependencyUtil.allUploadDependOn(project, task)
		}
		
		// Register updateBuildNumber task
		tasks.register(Constants.UPDATE_BUILD_NUMBER_TASK, UpdateGitBuildNumberTask).configure { task ->
			task.mustRunAfter(tasks.named(Constants.PUBLISH_EXTERNAL_TASK))
			task.mustRunAfter(tasks.named(Constants.CURSEFORGE_EXTERNAL_TASK))
		}
		
		// Register createGitTag task
		tasks.register(Constants.CREATE_GIT_TAG_TASK, CreateGitTagTask).configure { task ->
			task.mustRunAfter(tasks.named(Constants.PUBLISH_EXTERNAL_TASK))
			task.mustRunAfter(tasks.named(Constants.CURSEFORGE_EXTERNAL_TASK))
			task.dependsOn(tasks.named(Constants.CHECK_GIT_TASK))
		}
		
		// Register releaseMod task
		tasks.register(Constants.RELEASE_MOD_TASK, ReleaseModTask).configure { task ->
			task.group = "release"
			
			task.dependsOn(tasks.named(Constants.PUBLISH_EXTERNAL_TASK))
			task.dependsOn(tasks.named(Constants.CURSEFORGE_EXTERNAL_TASK))
			task.dependsOn(tasks.named(Constants.CREATE_GIT_TAG_TASK))
			task.dependsOn(tasks.named(Constants.UPDATE_BUILD_NUMBER_TASK))
		}
	}
	
	private static void handleMultiLoaderMainProject(final GradleFilesPlugin plugin) {
		final def project = plugin.project
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
	
	private static void handleMultiLoaderLoaderProject(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def tasks = project.tasks
		
		final def mainProject = GradleFilesUtil.getMainProject(plugin.project)
		final def mainTasks = mainProject.tasks
		
		// Register dependencies for checkGitTask in main project
		mainTasks.named(Constants.CHECK_GIT_TASK).configure { task ->
			DependencyUtil.allPublishingDependOn(project, task)
			DependencyUtil.allUploadDependOn(project, task)
		}
		
		// Register dependencies for updateBuildNumber in main project
		mainTasks.named(Constants.UPDATE_BUILD_NUMBER_TASK).configure { task ->
			task.mustRunAfter(tasks.named(Constants.PUBLISH_EXTERNAL_TASK))
			task.mustRunAfter(tasks.named(Constants.CURSEFORGE_EXTERNAL_TASK))
		}
		
		// Register dependencies for createGitTag in main project
		mainTasks.named(Constants.CREATE_GIT_TAG_TASK).configure { task ->
			task.mustRunAfter(tasks.named(Constants.PUBLISH_EXTERNAL_TASK))
			task.mustRunAfter(tasks.named(Constants.CURSEFORGE_EXTERNAL_TASK))
		}
		
		// Register dependencies for releaseMod in main project
		mainTasks.named(Constants.RELEASE_MOD_TASK).configure { task ->
			task.dependsOn(tasks.named(Constants.PUBLISH_EXTERNAL_TASK))
			task.dependsOn(tasks.named(Constants.CURSEFORGE_EXTERNAL_TASK))
		}
	}
}
