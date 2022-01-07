package info.u_team.gradle_files_plugin.tool

import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.type.ArtifactTypeDefinition
import org.gradle.api.attributes.Bundling
import org.gradle.api.attributes.Category
import org.gradle.api.attributes.LibraryElements
import org.gradle.api.attributes.Usage
import org.gradle.api.attributes.java.TargetJvmEnvironment
import org.gradle.api.attributes.java.TargetJvmVersion
import org.gradle.api.component.AdhocComponentWithVariants
import org.gradle.api.internal.artifacts.dsl.LazyPublishArtifact
import org.gradle.api.internal.plugins.DefaultArtifactPublicationSet
import org.gradle.api.internal.project.ProjectInternal
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.plugins.internal.JavaConfigurationVariantMapping
import org.gradle.api.plugins.jvm.internal.JvmPluginServices
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.bundling.Jar
import org.gradle.language.base.plugins.LifecycleBasePlugin

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import javax.inject.Inject

class DeobfJarTaskTool {
	
	static void add(final GradleFilesPlugin plugin) {
		final def javaPluginExtension = plugin.project.extensions.getByType(JavaPluginExtension)
		javaPluginExtension.metaClass.withDeobfJar {
			final def project = plugin.project
			final def tasks = project.tasks
			
			final def jarTask = tasks.register("deobfJar", Jar) { task ->
				task.description = "Assemble a jar archive containing the deobfed classes"
				task.group = BasePlugin.BUILD_GROUP
				task.archiveClassifier = "deobf"
				task.from(javaPluginExtension.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).output)
			}
			
			if (tasks.names.contains(LifecycleBasePlugin.ASSEMBLE_TASK_NAME)) {
				tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure { task ->
					task.dependsOn(jarTask)
				}
			}
			
			final def configuration = project.configurations.maybeCreate("deobfRuntimeElements")
			configuration.description = "Deobf jar elements for main sourceset"
			configuration.visible = false
			configuration.canBeResolved = false
			configuration.canBeConsumed = true
			
			def attributes = configuration.attributes
			
			// Copy attributes from runtime elements
			def runtimeElementsAttributes = project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).attributes
			project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).attributes.keySet().each { attribute ->
				attributes.attribute(attribute, runtimeElementsAttributes.getAttribute(attribute))
			}
			
			println javaPluginExtension.autoTargetJvmDisabled
			println getJvmPluginServices()
			//javaPluginExtension.toolchain.languageVersion.
			
			// Add java version attribute
			attributes.attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, Integer.parseInt(JavaVersion.current().getMajorVersion()))
			
			configuration.outgoing.artifact(new LazyPublishArtifact(jarTask, (project as ProjectInternal).fileResolver))
			
			final def javaComponent = project.components.getByName("java") as AdhocComponentWithVariants
			javaComponent.addVariantsFromConfiguration(configuration, new JavaConfigurationVariantMapping("runtime", true))
		}
	}
	
	@Inject
	private static JvmPluginServices getJvmPluginServices() {
		throw new UnsupportedOperationException();
	}
}
