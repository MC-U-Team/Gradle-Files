package info.u_team.sign_jar_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.artifacts.PublishArtifact
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskDependency

@CacheableTask
abstract class SignJarTask extends DefaultTask implements PublishArtifact {

	@TaskAction
	void sign() {
		final def inputFile = getInputFile().get().asFile
		final def outputFile = getOutputFile().get().asFile
		final def keyStore = getKeyStore().getOrNull()
		final def alias = getAlias().get()
		final def storePass = getStorePass().get()
		final def keyPass = getKeyPass().getOrNull()

		final def properties = [
			keyStore: keyStore,
			alias: alias,
			storepass: storePass,
			keyPass: keyPass,
			jar: inputFile,
			signedJar: outputFile,
			force: true
		]
		ant.invokeMethod("signjar", properties)
	}

	@InputFile
	@PathSensitive(PathSensitivity.NONE)
	abstract RegularFileProperty getInputFile();

	@OutputFile
	abstract RegularFileProperty getOutputFile();

	@Input
	@Optional
	abstract Property<String> getKeyStore()

	@Input
	abstract Property<String> getAlias()

	@Internal
	abstract Property<String> getStorePass()

	@Internal
	abstract Property<String> getKeyPass()

	@Internal
	abstract Property<String> getArchiveClassifier()

	@Internal
	abstract Property<String> getArchiveExtension()

	@Internal
	@Override
	String getClassifier() {
		return archiveClassifier.getOrNull()
	}

	@Internal
	@Override
	String getExtension() {
		return archiveExtension.get()
	}

	@Internal
	@Override
	String getType() {
		return archiveExtension.get()
	}

	@Internal
	@Override
	File getFile() {
		return outputFile.get().asFile
	}

	@Internal
	@Override
	Date getDate() {
		return null
	}

	@Internal
	@Override
	TaskDependency getBuildDependencies() {
		return { Task task -> [this] as Set<Task> } as TaskDependency
	}
}
