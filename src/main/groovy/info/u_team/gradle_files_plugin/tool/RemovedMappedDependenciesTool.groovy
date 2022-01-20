package info.u_team.gradle_files_plugin.tool

import java.lang.reflect.Field

import org.gradle.api.publish.maven.internal.publication.DefaultMavenPom
import org.gradle.api.publish.maven.internal.publication.MavenPomInternal
import org.gradle.api.publish.maven.internal.publication.MavenPublicationInternal
import org.gradle.api.publish.maven.tasks.GenerateMavenPom
import org.gradle.api.publish.tasks.GenerateModuleMetadata

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.DependencyFilteredMavenPomInternal
import info.u_team.gradle_files_plugin.util.DependencyFilteredMavenPublicationInternal

class RemovedMappedDependenciesTool {
	
	static void remove(final GradleFilesPlugin plugin) {
		plugin.project.tasks.withType(GenerateMavenPom) { task ->
			final def pom = task.pom
			if(pom instanceof MavenPomInternal) {
				final def internalPom = pom as MavenPomInternal
				final def filteredPom = new DependencyFilteredMavenPomInternal(internalPom)
				
				task.pom = filteredPom
			}
		}
		
		plugin.project.tasks.withType(GenerateModuleMetadata) { task ->
			final def publication = task.publication.get()
			if(publication instanceof MavenPublicationInternal) {
				final def internalPublication = publication as MavenPublicationInternal
				final def filteredPublication = new DependencyFilteredMavenPublicationInternal(internalPublication)
				
				task.publication.set(filteredPublication)
				task.publications.set([filteredPublication])
			}
		}
	}
}
