package info.u_team.gradle_files_plugin.util;

import java.util.Set;
import java.util.function.Supplier;

import org.gradle.api.internal.component.SoftwareComponentInternal;
import org.gradle.api.internal.component.UsageContext;

import com.google.common.collect.ImmutableSet;

public class DependencyFilteredSoftwareComponentInternal implements SoftwareComponentInternal {
	
	private final Supplier<SoftwareComponentInternal> component;
	
	public DependencyFilteredSoftwareComponentInternal(Supplier<SoftwareComponentInternal> component) {
		this.component = component;
	}
	
	@Override
	public String getName() {
		return component.get().getName();
	}
	
	@Override
	public Set<? extends UsageContext> getUsages() {
		final ImmutableSet.Builder<UsageContext> builder = ImmutableSet.builder();
		
		component.get().getUsages().forEach(usage -> {
			builder.add(new DependencyFilteredUsageContext(usage));
		});
		
		return builder.build();
	}
	
}
