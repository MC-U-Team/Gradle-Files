package info.u_team.gradle_files_plugin.tool

import org.gradle.api.publish.maven.internal.publication.MavenPublicationInternal
import org.gradle.api.publish.tasks.GenerateModuleMetadata

import info.u_team.gradle_files_plugin.GradleFilesPlugin
import info.u_team.gradle_files_plugin.util.DependencyFilteredMavenPublicationInternal

class RemovedMappedDependenciesTool {

	static void remove(final GradleFilesPlugin plugin) {
		plugin.project.tasks.withType(GenerateModuleMetadata) { task ->
			final def publication = task.getPublication().get()
			if(publication instanceof MavenPublicationInternal) {
				final def internalPublication = publication as MavenPublicationInternal
				final def filteredPublication = new DependencyFilteredMavenPublicationInternal(internalPublication)

				task.getPublication().set(filteredPublication)
				task.getPublications().set([filteredPublication])
			}
		}
	}
}
