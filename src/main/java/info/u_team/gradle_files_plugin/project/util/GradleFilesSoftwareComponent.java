package info.u_team.gradle_files_plugin.project.util;

import java.util.Set;

import org.gradle.api.internal.component.SoftwareComponentInternal;
import org.gradle.api.internal.component.UsageContext;

public class GradleFilesSoftwareComponent implements SoftwareComponentInternal {
	
	private final SoftwareComponentInternal javaComponent;
	
	public GradleFilesSoftwareComponent(SoftwareComponentInternal javaComponent) {
		this.javaComponent = javaComponent;
	}
	
	@Override
	public String getName() {
		return javaComponent.getName();
	}
	
	@Override
	public Set<? extends UsageContext> getUsages() {
		return javaComponent.getUsages();
	}
}
