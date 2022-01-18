package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import org.gradle.api.DomainObjectSet
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ConfigurationPublications
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.DependencySet
import org.gradle.api.artifacts.ExcludeRule
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.component.AdhocComponentWithVariants
import org.gradle.api.component.SoftwareComponentVariant
import org.gradle.api.internal.CompositeDomainObjectSet
import org.gradle.api.internal.DefaultDomainObjectCollection
import org.gradle.api.internal.DelegatingDomainObjectSet
import org.gradle.api.internal.artifacts.DefaultDependencySet
import org.gradle.api.internal.artifacts.DefaultExcludeRule
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.internal.DefaultAdhocSoftwareComponent
import org.gradle.api.plugins.internal.JavaConfigurationVariantMapping
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin
import org.gradle.api.specs.Spec

class RemovedMappedDependenciesTool {

	static void remove(final GradleFilesPlugin plugin) {

		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).dependencies
		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).dependencies.getClass()
		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies
		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies.getClass()

		println "RRRR"

		DefaultDependencySet set = plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies

		println set.getClass()
		println DelegatingDomainObjectSet.class.declaredFields
		println set.getClass().declaredFields

		def field = DelegatingDomainObjectSet.class.declaredFields.find { field ->
			field.name == "backingSet"
		}
		field.accessible = true

		println field
		println "________________________"
		final CompositeDomainObjectSet<Dependency> backingSet = field.get(plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies)


		println backingSet

		backingSet.removeCollection(plugin.project.configurations.getByName("minecraft").dependencies)


		println ":::::::::::::::::::::::"
		println plugin.project.configurations.getByName(JavaPlugin.RUNTIME_ELEMENTS_CONFIGURATION_NAME).allDependencies
		

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
