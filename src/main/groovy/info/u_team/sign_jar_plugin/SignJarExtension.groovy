package info.u_team.sign_jar_plugin

import java.util.function.Function

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.RegularFile
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.TaskProvider

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.Constants
import info.u_team.sign_jar_plugin.extension.CreateSignJarTaskExtensionImpl
import info.u_team.sign_jar_plugin.task.SignJarTask

@CompileStatic
abstract class SignJarExtension {

	private final Project project

	SignJarExtension(final Project project) {
		this.project = project
		keyStore.convention(project.findProperty(Constants.KEYSTORE) as String);
		alias.convention(project.findProperty(Constants.KEYSTORE_ALIAS) as String);
		storePass.convention(project.findProperty(Constants.KEYSTORE_PASSWORD) as String);
		keyPass.convention(project.findProperty(Constants.KEYSTORE_KEY_PASSWORD) as String);
	}

	abstract Property<String> getKeyStore()

	abstract Property<String> getAlias()

	abstract Property<String> getStorePass()

	abstract Property<String> getKeyPass()

	TaskProvider<SignJarTask> sign(final TaskProvider<?> taskProvider) {
		CreateSignJarTaskExtensionImpl.sign(project, this, taskProvider)
	}

	TaskProvider<SignJarTask> sign(final TaskProvider<?> taskProvider, @DelegatesTo(Task.class) final Closure<Provider<RegularFile>> fileFunction) {
		CreateSignJarTaskExtensionImpl.sign(project, this, taskProvider, fileFunction)
	}
}
