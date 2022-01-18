package info.u_team.gradle_files_plugin.tool

import org.apache.tools.ant.taskdefs.optional.depend.Depend
import org.gradle.api.publish.maven.internal.publication.MavenPublicationInternal
import org.gradle.api.publish.tasks.GenerateModuleMetadata

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.DependencyFilteredMavenPublicationInternal

class RemovedMappedDependenciesTool {

	static void remove(final GradleFilesPlugin plugin) {

		/*PublicationContainer container = plugin.project.extensions.getByType(PublishingExtension).publications;
		 container.withType(MavenPublication) { publication ->
		 MavenPublicationInternal mavenPublication = publication
		 println mavenPublication.runtimeDependencies.clear()
		 println "________________________________________________________________________________________________"
		 println mavenPublication
		 println ""
		 println mavenPublication.apiDependencies
		 println mavenPublication.runtimeDependencies
		 println mavenPublication.optionalDependencies
		 }*/

		plugin.project.tasks.withType(GenerateModuleMetadata) { task ->
			final def publication = task.getPublication().get()
			if(publication instanceof MavenPublicationInternal) {
				final def internalPublication = publication as MavenPublicationInternal
				final def filteredPublication = new DependencyFilteredMavenPublicationInternal(internalPublication)

				task.getPublication().set(filteredPublication)
				task.getPublications().set([filteredPublication])


				task.getPublication().get().component.usages.each {
					println it.dependencies
				}

				//				internalPublication.component.usages.each {
				//					it.dependencies.clear()
				//					println it.dependencies
				//				}
			}
		}


		//		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).dependencies
		//		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).dependencies.getClass()
		//		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies
		//		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies.getClass()
		//
		//		println "RRRR"
		//
		//		DefaultDependencySet set = plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies
		//
		//		println set.getClass()
		//		println DelegatingDomainObjectSet.class.declaredFields
		//		println set.getClass().declaredFields
		//
		//		def field = DelegatingDomainObjectSet.class.declaredFields.find { field ->
		//			field.name == "backingSet"
		//		}
		//		field.accessible = true
		//
		//		println field
		//		println "________________________"
		//		final CompositeDomainObjectSet<Dependency> backingSet = field.get(plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies)
		//
		//
		//		println backingSet
		//
		//		backingSet.removeCollection(plugin.project.configurations.getByName("minecraft").dependencies)
		//
		//
		//		println ":::::::::::::::::::::::"
		//		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies


		//println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies.add(null)

		//		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).incoming.dependencies
		//		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).incoming.dependencies.getClass()
		//
		//		plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).incoming.dependencies.withType(ModuleDependency).each { dependency ->
		//			println "___________"
		//			println dependency
		//			println dependency.attributes
		//		}



		//plugin.project.configurations.getByName(DeobfJarTaskTool.CONFIGURATION_NAME).exclude([group: "net.minecraftforge", module: "forge"])


		//		final def config = plugin.project.configurations.getByName(DeobfJarTaskTool.CONFIGURATION_NAME).copyRecursive { spec ->
		//			println "Spec ->>>>>>>>>>>>>" + spec.getClass()
		//			false
		//		}
		//
		//		println config
		//		println config.allDependencies
		//
		//
		//		final def javaComponent = plugin.project.components.getByName("java") as AdhocComponentWithVariants
		//		javaComponent.addVariantsFromConfiguration(config, new JavaConfigurationVariantMapping("runtime", true))





		//		[
		//			JavaPlugin.API_ELEMENTS_CONFIGURATION_NAME,
		//			JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME,
		//			DeobfJarTaskTool.CONFIGURATION_NAME
		//		].collect { name ->
		//			plugin.project.configurations.getByName(name)
		//		}.each { configuration ->
		//			println "__S__AS_DAS_D_AS_D_ASD_AS_DA_SD_AS_DAS_D_AS_D_ASD_AS_DA_SD_AS_D_A_D_ASD_AS_D_ASD"
		//			println configuration
		//
		//			final def x = ((Configuration)configuration);
		//
		//			println x.dependencies
		//			println x.hierarchy
		//			println x.outgoing
		//
		//
		//			x.withDependencies { dependencies ->
		//				println dependencies
		//				dependencies.removeIf { dependency ->
		//					dependency.name == "forge"
		//				}
		//				println "X_"
		//				println dependencies
		//			}
		//
		//			/*x.allDependencies.removeIf { dependency ->
		//			 dependency.name == "forge"
		//			 }*/
		//		}

		//		(plugin.project.components.getByName("java") as AdhocComponentWithVariants).withVariantsFromConfiguration(plugin.project.configurations.getByName("minecraft")) { configurationVariantDetails ->
		//			configurationVariantDetails.skip()
		//		}

		//		final def component = plugin.project.components.getByName("java") as SoftwareComponentVariant
		//		component.dependencies.each { dependency ->
		//			println "ROFL ORFLRO OOO"
		//			println dependency.group
		//			println dependency.name
		//			println dependency.version
		//		}
		//
		//		(plugin.project.components.getByName("java") as AdhocComponentWithVariants).withVariantsFromConfiguration(plugin.project.configurations.getByName("runtimeElements")) { configurationVariantDetails ->
		//			configurationVariantDetails.configurationVariant.
		//		}


		//		plugin.project.tasks.withType(MavenPublishPlugin) { task ->
		//			task.options.encoding = "UTF-8"
		//		}
	}
}
