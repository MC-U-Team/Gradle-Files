package info.u_team.sign_jar_plugin.task

import java.nio.file.Files
import java.nio.file.StandardCopyOption

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import org.gradle.api.tasks.TaskAction

@CacheableTask
abstract class SignJarTask extends DefaultTask {

	@TaskAction
	void sign() {
		final def inputFile = getInputFile().get().asFile
		final def outputFile = getOutputFile().get().asFile
		final def signing = getSigning().get()
		final def keyStore = getKeyStore().getOrNull()
		final def alias = getAlias().getOrNull()
		final def storePass = getStorePass().getOrNull()
		final def keyPass = getKeyPass().getOrNull()

		if(!signing) {
			Files.copy(inputFile.toPath(), outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING)
			return
		}

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
	abstract Property<Boolean> getSigning()

	@Input
	@Optional
	abstract Property<String> getKeyStore()

	@Input
	@Optional
	abstract Property<String> getAlias()

	@Input
	@Optional
	abstract Property<String> getStorePass()

	@Input
	@Optional
	abstract Property<String> getKeyPass()
}
