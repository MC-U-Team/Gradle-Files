package info.u_team.gradle_files_plugin.tool

import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.type.ArtifactTypeDefinition
import org.gradle.api.attributes.Bundling
import org.gradle.api.attributes.Category
import org.gradle.api.attributes.DocsType
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
import org.gradle.api.plugins.internal.JvmPluginsHelper
import org.gradle.api.plugins.jvm.internal.JvmPluginExtension
import org.gradle.api.plugins.jvm.internal.JvmPluginServices
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.bundling.Jar
import org.gradle.language.base.plugins.LifecycleBasePlugin
import org.gradle.language.jvm.tasks.ProcessResources

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import javax.inject.Inject

class DeobfJarTaskTool {
	
	static void add(final GradleFilesPlugin plugin) {
		final def project = plugin.project
		final def javaPluginExtension = plugin.project.extensions.getByType(JavaPluginExtension)
		
		javaPluginExtension.metaClass.withDeobfJar {
			final def tasks = project.tasks
			
			final def mainSourceSet = javaPluginExtension.sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME)
			
			final def jarTask = tasks.register("deobfJar", Jar) { task ->
				task.description = "Assemble a jar archive containing the deobfed classes"
				task.group = BasePlugin.BUILD_GROUP
				task.archiveClassifier = "deobf"
				task.from(mainSourceSet.output)
			}
			
			if (tasks.names.contains(LifecycleBasePlugin.ASSEMBLE_TASK_NAME)) {
				tasks.named(LifecycleBasePlugin.ASSEMBLE_TASK_NAME).configure { task ->
					task.dependsOn(jarTask)
				}
			}
			
			final def jvmPluginServices = plugin.jvmPluginServices
			
			final def defaultConfiguration = project.configurations.getByName(Dependency.DEFAULT_CONFIGURATION);
			final def implementationConfiguration = project.configurations.getByName(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME)
			final def runtimeOnlyConfiguration = project.configurations.getByName(JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME)
			
			final def deobfRuntimeElementsConfiguration = jvmPluginServices.createOutgoingElements("deobfRuntimeElements") { builder ->
				builder .fromSourceSet(mainSourceSet)
						.providesRuntime()
						.withDescription("Deobf elements of runtime for main.")
						.extendsFrom(implementationConfiguration, runtimeOnlyConfiguration)
			}
			defaultConfiguration.extendsFrom(deobfRuntimeElementsConfiguration)
			
			deobfRuntimeElementsConfiguration.deprecateForDeclaration(JavaPlugin.IMPLEMENTATION_CONFIGURATION_NAME, JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME, JavaPlugin.RUNTIME_ONLY_CONFIGURATION_NAME);
			
			deobfRuntimeElementsConfiguration.attributes.attribute(Category.CATEGORY_ATTRIBUTE, project.objects.named(Category, Category.ENFORCED_PLATFORM))
			
			final def publications = deobfRuntimeElementsConfiguration.outgoing
			final def artifact = new LazyPublishArtifact(jarTask, (project as ProjectInternal).fileResolver)
			
			project.extensions.getByType(DefaultArtifactPublicationSet).addCandidate(artifact)
			
			publications.artifacts.add(artifact)
			publications.attributes.attribute(ArtifactTypeDefinition.ARTIFACT_TYPE_ATTRIBUTE, ArtifactTypeDefinition.JAR_TYPE)
			
			jvmPluginServices.configureClassesDirectoryVariant("deobfRuntimeElements", mainSourceSet)
			
			final def deobfRuntimeVariants = publications.variants
			final def resourcesVariant = deobfRuntimeVariants.create("resources")
			resourcesVariant.attributes.attribute(Usage.USAGE_ATTRIBUTE, project.objects.named(Usage, Usage.JAVA_RUNTIME))
			resourcesVariant.attributes.attribute(LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE, project.objects.named(LibraryElements, LibraryElements.RESOURCES))
			
			final def processResources = project.tasks.named(JavaPlugin.PROCESS_RESOURCES_TASK_NAME, ProcessResources)
			resourcesVariant.artifact(new JvmPluginsHelper.IntermediateJavaArtifact(ArtifactTypeDefinition.JVM_RESOURCES_DIRECTORY, processResources) {
						
						@Override
						public File getFile() {
							return processResources.get().destinationDir
						}
					})
			
			final def javaComponent = project.components.getByName("java") as AdhocComponentWithVariants
			javaComponent.addVariantsFromConfiguration(deobfRuntimeElementsConfiguration, new JavaConfigurationVariantMapping("runtime", true))
		}
	}
}
