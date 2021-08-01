buildscript {
	repositories {
		maven { url = "https://files.minecraftforge.net/maven" }
		mavenCentral()
	}
	dependencies {
		classpath group: "net.minecraftforge.gradle", name: "ForgeGradle", version: "5.1.+", changing: true
	}
}

import net.minecraftforge.gradle.common.tasks.SignJar
import org.gradle.api.internal.artifacts.dsl.LazyPublishArtifact

ext.createSignJarTask = { taskName, depends, file ->
	tasks.create(name: taskName, type: SignJar) {
		onlyIf {
			project.hasProperty("keystore")
		}
		afterEvaluate {
			dependsOn depends
		}
		if (project.hasProperty("keystore")) {
			doLast {
				println "Signing jar " + file.getName()
			}
			keyStore = project.findProperty("keystore")
			alias = project.findProperty("keystore.alias")
			storePass = project.findProperty("keystore.password")
			keyPass = project.findProperty("keystore.password")
			inputFile = file
			outputFile = file
		} else {
			println "Could not sign " + file.getName() + ". No keystore property could be found"
		}
	}
}

ext.signJar = { task ->
	def taskName = "sign${task.name}"
	createSignJarTask(taskName, task, task.archivePath)
	build.dependsOn taskName
}

ext.signAllJars = {
	->
	configurations.archives.allArtifacts.each { artifact ->
		if(!(artifact instanceof LazyPublishArtifact)) {
			def taskName = "signJar${artifact.getClassifier()}"
			createSignJarTask(taskName, artifact, artifact.getFile())
			build.dependsOn taskName
		}
	}
}
