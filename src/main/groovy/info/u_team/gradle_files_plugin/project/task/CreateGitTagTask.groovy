package info.u_team.gradle_files_plugin.project.task

import javax.inject.Inject

import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.TaskAction
import org.gradle.process.ExecOperations
import org.gradle.work.DisableCachingByDefault

import info.u_team.gradle_files_plugin.Constants
import info.u_team.gradle_files_plugin.project.util.ExecuteUtil
import info.u_team.gradle_files_plugin.project.util.GitUtil
import info.u_team.gradle_files_plugin.project.util.GradleFilesUtil

@DisableCachingByDefault(because = "Interacts with git")
abstract class CreateGitTagTask extends DefaultTask {

	CreateGitTagTask() {
		final def createBuild = project.hasProperty(Constants.BUILD_PROPERTY)
		onlyIf {
			createBuild
		}

		final def config = GradleFilesUtil.getProjectConfig(project)
		tagName.convention(project.provider { "${config.minecraft.version}-${project.version}" as String })
		final def rootDir = project.rootDir
		rootProjectDirectory.convention(project.layout.dir(project.provider { rootDir }))
	}

	@Input
	abstract Property<String> getTagName()

	@Internal
	abstract DirectoryProperty getRootProjectDirectory()

	@Inject
	abstract ExecOperations getExecOperations()

	@TaskAction
	void create() {
		final def executor = ExecuteUtil.fromExecOperations(logger, execOperations)
		final def rootDir = rootProjectDirectory.get().asFile
		final def tagName = getTagName().get()

		GitUtil.executeGitCommandException(executor, rootDir, "tag", "-a", tagName, "-m", tagName)
		GitUtil.executeGitCommandException(executor, rootDir, "push", "origin", tagName)

		logger.quiet("Created release tag ${tagName}")
	}
}
