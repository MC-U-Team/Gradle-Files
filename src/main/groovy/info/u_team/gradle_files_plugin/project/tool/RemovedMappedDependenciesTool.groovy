package info.u_team.gradle_files_plugin.project.tool

import java.util.function.Predicate

import org.gradle.api.Project

import com.google.common.base.Predicates

import groovy.transform.CompileStatic

@CompileStatic
class RemovedMappedDependenciesTool {
	
	private static Predicate<Dependency> filterPublishingDependencies = Predicates.alwaysFalse()
	
	static void remove(final Project project) {
		filterPublishingDependency { Dependency dependency ->
			dependency.version.contains("_mapped_")
		}
		
		project.afterEvaluate {
			//			println project.components
			//			println project.components.findByName("java")
		}
		
		//		project.tasks.withType(GenerateMavenPom).configureEach { GenerateMavenPom task ->
		//			final def pom = task.pom
		//			if(pom instanceof MavenPomInternal) {
		//				final def internalPom = pom as MavenPomInternal
		//				final def filteredPom = new DependencyFilteredMavenPomInternal(internalPom)
		//
		//				task.pom = filteredPom
		//			}
		//		}
		//
		//		project.tasks.withType(GenerateModuleMetadata).configureEach { GenerateModuleMetadata task ->
		//			final def publication = task.publication.get()
		//			if(publication instanceof MavenPublicationInternal) {
		//				final def internalPublication = publication as MavenPublicationInternal
		//				final def filteredPublication = new DependencyFilteredMavenPublicationInternal(internalPublication)
		//
		//				task.publication.set(filteredPublication)
		//				task.publications.set([filteredPublication])
		//			}
		//		}
	}
	
	static void filterPublishingDependency(@DelegatesTo(Predicate.class) Closure predicate) {
		filterPublishingDependencies = filterPublishingDependencies.or(predicate)
	}
	
	
	public static Predicate<Dependency> getFilterPublishingDependencies() {
		return filterPublishingDependencies;
	}
	
	static class Dependency {
		String group
		String name
		String version
		
		Dependency(String group, String name, String version) {
			this.group = group;
			this.name = name;
			this.version = version;
		}
	}
}
