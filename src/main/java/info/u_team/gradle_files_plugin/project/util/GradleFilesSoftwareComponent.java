package info.u_team.gradle_files_plugin.project.util;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.gradle.api.artifacts.DependencyConstraint;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.capabilities.Capability;
import org.gradle.api.internal.component.SoftwareComponentInternal;
import org.gradle.api.internal.component.UsageContext;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

import info.u_team.gradle_files_plugin.project.extension.FilteredComponentExtensionImpl.DependencyHolder;

public class GradleFilesSoftwareComponent implements SoftwareComponentInternal {
	
	private Predicate<DependencyHolder> filterPublishingDependencies = Predicates.alwaysFalse();
	
	private final SoftwareComponentInternal javaComponent;
	
	public GradleFilesSoftwareComponent(SoftwareComponentInternal javaComponent) {
		this.javaComponent = javaComponent;
	}
	
	/**
	 * Return true to remove the dependency, else false
	 * 
	 * @param predicate Predicate for dependencies
	 */
	public void filterDependency(Predicate<DependencyHolder> predicate) {
		filterPublishingDependencies = filterPublishingDependencies.or(predicate);
	}
	
	@Override
	public String getName() {
		return javaComponent.getName();
	}
	
	@Override
	public Set<? extends UsageContext> getUsages() {
		final ImmutableSet.Builder<UsageContext> builder = new ImmutableSet.Builder<>();
		for (final UsageContext context : javaComponent.getUsages()) {
			builder.add(new UsageContext() {
				
				@Override
				public Set<? extends ModuleDependency> getDependencies() {
					return context.getDependencies().stream().filter(dependency -> {
						return filterPublishingDependencies.negate().test(new DependencyHolder(dependency, context));
					}).collect(Collectors.toCollection(LinkedHashSet::new));
				}
				
				@Override
				public String getName() {
					return context.getName();
				}
				
				@Override
				public AttributeContainer getAttributes() {
					return context.getAttributes();
				}
				
				@Override
				public Set<ExcludeRule> getGlobalExcludes() {
					return context.getGlobalExcludes();
				}
				
				@Override
				public Set<? extends DependencyConstraint> getDependencyConstraints() {
					return context.getDependencyConstraints();
				}
				
				@Override
				public Set<? extends Capability> getCapabilities() {
					return context.getCapabilities();
				}
				
				@Override
				public Set<? extends PublishArtifact> getArtifacts() {
					return context.getArtifacts();
				}
			});
		}
		return builder.build();
	}
}
