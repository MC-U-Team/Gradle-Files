package info.u_team.gradle_files_plugin.project.extension

import org.gradle.api.Project
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.component.SoftwareComponent
import org.gradle.api.component.SoftwareComponentVariant
import org.gradle.api.internal.component.SoftwareComponentInternal

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.project.util.GradleFilesSoftwareComponent

@CompileStatic
class FilteredComponentExtensionImpl {
	
	static GradleFilesSoftwareComponent filteredJavaComponent(final Project project, final @DelegatesTo(GradleFilesSoftwareComponent.class) Closure configureClosure) {
		filteredComponent(project.components.getByName("java"), configureClosure)
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
