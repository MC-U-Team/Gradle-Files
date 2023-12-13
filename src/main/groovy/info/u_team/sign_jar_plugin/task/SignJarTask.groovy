package info.u_team.sign_jar_plugin.task

import org.gradle.api.DefaultTask
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Internal
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.TaskAction
import org.gradle.work.DisableCachingByDefault

@DisableCachingByDefault
abstract class SignJarTask extends DefaultTask {

	@TaskAction
	void sign() {
		final def properties = [
			keyStore: keyStore.getOrNull(),
			alias: alias.get(),
			storepass: storePass.get(),
			keyPass: keyPass.getOrNull(),
			jar: inputFile.get().asFile,
			signedJar: outputFile.get().asFile
		]
		ant.invokeMethod("signjar", properties)
	}

	@InputFile
	abstract RegularFileProperty getInputFile();

	@Internal
	abstract RegularFileProperty getOutputFile();

	@Input
	@Optional
	abstract Property<String> getKeyStore()

	@Input
	abstract Property<String> getAlias()

	@Input
	abstract Property<String> getStorePass()

	@Input
	@Optional
	abstract Property<String> getKeyPass()
}
