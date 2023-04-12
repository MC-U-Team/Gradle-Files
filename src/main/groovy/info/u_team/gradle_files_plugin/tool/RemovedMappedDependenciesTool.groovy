package info.u_team.gradle_files_plugin.tool

import java.util.function.Predicate

import org.gradle.api.publish.maven.internal.publication.MavenPomInternal
import org.gradle.api.publish.maven.internal.publication.MavenPublicationInternal
import org.gradle.api.publish.maven.tasks.GenerateMavenPom
import org.gradle.api.publish.tasks.GenerateModuleMetadata

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.tool.RemovedMappedDependenciesTool.Dependency
import info.u_team.gradle_files_plugin.util.DependencyFilteredMavenPomInternal
import info.u_team.gradle_files_plugin.util.DependencyFilteredMavenPublicationInternal

@CompileStatic
class RemovedMappedDependenciesTool {
	
	private static Predicate<Dependency> filterPublishingDependencies = {false}
	
	static void remove(final GradleFilesPlugin plugin) {
		if(plugin.extension.stripMappedDependencies) {
			filterPublishingDependency { dependency ->
				dependency.version.contains("_mapped_")
			}
		}
		
		plugin.project.tasks.withType(GenerateMavenPom) { GenerateMavenPom task ->
			final def pom = task.pom
			if(pom instanceof MavenPomInternal) {
				final def internalPom = pom as MavenPomInternal
				final def filteredPom = new DependencyFilteredMavenPomInternal(internalPom)
				
				task.pom = filteredPom
			}
		}
		
		plugin.project.tasks.withType(GenerateModuleMetadata) { GenerateModuleMetadata task ->
			final def publication = task.publication.get()
			if(publication instanceof MavenPublicationInternal) {
				final def internalPublication = publication as MavenPublicationInternal
				final def filteredPublication = new DependencyFilteredMavenPublicationInternal(internalPublication)
				
				task.publication.set(filteredPublication)
				task.publications.set([filteredPublication])
			}
		}
	}
	
	static void filterPublishingDependency(Predicate<Dependency> predicate) {
		filterPublishingDependencies = filterPublishingDependencies.or(predicate)
	}
	
	
	public static Predicate<Dependency> getFilterPublishingDependencies() {
		return filterPublishingDependencies;
	}
	
	static class Dependency {
		String group
		String artifact
		String version
		
		Dependency(String group, String artifact, String version) {
			this.group = group;
			this.artifact = artifact;
			this.version = version;
		}
	}
}
