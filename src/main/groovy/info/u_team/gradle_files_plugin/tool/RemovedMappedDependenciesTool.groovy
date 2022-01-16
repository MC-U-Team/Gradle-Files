package info.u_team.gradle_files_plugin.tool

import info.u_team.gradle_files_plugin.GradleFilesPlugin

import org.gradle.api.artifacts.Configuration
import org.gradle.api.component.AdhocComponentWithVariants
import org.gradle.api.component.SoftwareComponentVariant
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin

class RemovedMappedDependenciesTool {
	
	static void remove(final GradleFilesPlugin plugin) {
		
		final def minecraft = plugin.project.configurations.getByName("minecraft")
		
		println "____________________________________"
		
		println minecraft
		minecraft.withDependencies { dependencies ->
				println dependencies
				dependencies.removeIf { dependency ->
					dependency.name == "forge"
				}
				println "X_"
				println dependencies
			}
		
		
		
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
