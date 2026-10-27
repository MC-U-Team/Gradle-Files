package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.artifacts.PublishArtifact
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.component.SoftwareComponentVariant
import org.gradle.api.internal.component.SoftwareComponentInternal

import groovy.transform.CompileDynamic
import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.tool.SetupSpecialModLoaderSettingsTool
import info.u_team.gradle_files_plugin.project.util.GradleFilesSoftwareComponent
import info.u_team.gradle_files_plugin.project.util.ReplacedFilePublishArtifact
import info.u_team.gradle_files_plugin.util.ClassUtil

@CompileStatic
class FilteredComponentExtensionImpl {
	
	static GradleFilesSoftwareComponent filteredJavaComponent(final Project project, final @DelegatesTo(GradleFilesSoftwareComponent.class) Closure configureClosure) {
		final def component = filteredComponent(project.components.getByName("java"), configureClosure)
		replaceRenamedJars(project, component)
		return component
	}
	
	static GradleFilesSoftwareComponent filteredComponent(final SoftwareComponent component, final @DelegatesTo(GradleFilesSoftwareComponent.class) Closure configureClosure) {
		if(component instanceof SoftwareComponentInternal) {
			final def filteredComponent = new GradleFilesSoftwareComponent(component)
			configureClosure.delegate = filteredComponent
			configureClosure.call(filteredComponent)
			return filteredComponent
		} else {
			throw new IllegalArgumentException("Software component cannot be filtered")
		}
	}
	
	@CompileDynamic
	private static void replaceRenamedJars(final Project project, final GradleFilesSoftwareComponent component) {
		// Publish the jar renamed by the renamer plugin instead of the original jar
		component.replaceArtifact { PublishArtifact artifact ->
			final def renameJarClass = ClassUtil.findClass(project, SetupSpecialModLoaderSettingsTool.RENAME_JAR_CLASS)
			if(renameJarClass) {
				// Iterate over a copy of the names, as realizing a task can register new tasks
				final def renameJar = project.tasks.withType(renameJarClass).names.toList().collect { name ->
					project.tasks.named(name).get()
				}.find { task ->
					task.input.getOrNull()?.asFile == artifact.file
				}
				if(renameJar) {
					return new ReplacedFilePublishArtifact(artifact, renameJar)
				}
			}
			return artifact
		}
	}
	
	static class DependencyHolder {
		
		final ModuleDependency dependency
		final SoftwareComponentVariant variant
		
		DependencyHolder(final ModuleDependency dependency, final SoftwareComponentVariant variant) {
			this.dependency = dependency
			this.variant = variant
		}
		
		@Override
		String toString() {
			return "${variant.name} -> ${dependency}"
		}
	}
}
