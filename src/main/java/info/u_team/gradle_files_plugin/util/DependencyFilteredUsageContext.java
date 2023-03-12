package info.u_team.gradle_files_plugin.util;

import java.util.Set;

import org.gradle.api.DomainObjectSet;
import org.gradle.api.artifacts.DependencyConstraint;
import org.gradle.api.artifacts.ExcludeRule;
import org.gradle.api.artifacts.ModuleDependency;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.attributes.Usage;
import org.gradle.api.capabilities.Capability;
import org.gradle.api.internal.component.UsageContext;

public class DependencyFilteredUsageContext implements UsageContext {
	
	private final UsageContext context;
	
	public DependencyFilteredUsageContext(UsageContext context) {
		this.context = context;
	}
	
	@Override
	public String getName() {
		return context.getName();
	}
	
	@Override
	public AttributeContainer getAttributes() {
		return context.getAttributes();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Usage getUsage() {
		return context.getUsage();
	}
	
	@Override
	public Set<? extends PublishArtifact> getArtifacts() {
		return context.getArtifacts();
	}
	
	@Override
	public Set<? extends ModuleDependency> getDependencies() {
		final Set<? extends ModuleDependency> dependencies = context.getDependencies();
		
		if (dependencies instanceof DomainObjectSet) {
			final DomainObjectSet<? extends ModuleDependency> domainDependencies = (DomainObjectSet<? extends ModuleDependency>) dependencies;
			return domainDependencies.matching(dependency -> {
				return !dependency.getVersion().contains("_mapped_");
			});
		}
		return dependencies;
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
	public Set<ExcludeRule> getGlobalExcludes() {
		return context.getGlobalExcludes();
	}
	
}
