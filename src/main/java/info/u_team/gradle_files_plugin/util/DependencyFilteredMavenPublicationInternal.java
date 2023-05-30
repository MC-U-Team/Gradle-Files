package info.u_team.gradle_files_plugin.util;

import java.util.Set;
import java.util.function.Supplier;

import org.gradle.api.Action;
import org.gradle.api.Task;
import org.gradle.api.artifacts.ModuleVersionIdentifier;
import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.component.SoftwareComponent;
import org.gradle.api.internal.attributes.ImmutableAttributes;
import org.gradle.api.internal.component.SoftwareComponentInternal;
import org.gradle.api.publish.VersionMappingStrategy;
import org.gradle.api.publish.internal.PublicationArtifactSet;
import org.gradle.api.publish.internal.versionmapping.VersionMappingStrategyInternal;
import org.gradle.api.publish.maven.MavenArtifact;
import org.gradle.api.publish.maven.MavenArtifactSet;
import org.gradle.api.publish.maven.MavenDependency;
import org.gradle.api.publish.maven.MavenPom;
import org.gradle.api.publish.maven.internal.dependencies.MavenDependencyInternal;
import org.gradle.api.publish.maven.internal.publication.MavenPomInternal;
import org.gradle.api.publish.maven.internal.publication.MavenPublicationInternal;
import org.gradle.api.publish.maven.internal.publisher.MavenNormalizedPublication;
import org.gradle.api.publish.maven.internal.publisher.MutableMavenProjectIdentity;
import org.gradle.api.tasks.TaskProvider;
import org.gradle.internal.DisplayName;

import com.google.common.base.Suppliers;

public class DependencyFilteredMavenPublicationInternal implements MavenPublicationInternal {
	
	private final MavenPublicationInternal publication;
	private final Supplier<SoftwareComponentInternal> component;
	
	public DependencyFilteredMavenPublicationInternal(MavenPublicationInternal publication) {
		this.publication = publication;
		component = Suppliers.memoize(() -> new DependencyFilteredSoftwareComponentInternal(publication::getComponent));
	}
	
	@Override
	public String getName() {
		return publication.getName();
	}
	
	@Override
	public DisplayName getDisplayName() {
		return publication.getDisplayName();
	}
	
	@Override
	public <T> T getCoordinates(Class<T> type) {
		return publication.getCoordinates(type);
	}
	
	@Override
	public void withoutBuildIdentifier() {
		publication.withoutBuildIdentifier();
	}
	
	@Override
	public SoftwareComponentInternal getComponent() {
		return component.get();
	}
	
	@Override
	public void withBuildIdentifier() {
		publication.withBuildIdentifier();
	}
	
	@Override
	public boolean isAlias() {
		return publication.isAlias();
	}
	
	@Override
	public ModuleVersionIdentifier getCoordinates() {
		return publication.getCoordinates();
	}
	
	@Override
	public boolean isLegacy() {
		return publication.isLegacy();
	}
	
	@Override
	public ImmutableAttributes getAttributes() {
		return publication.getAttributes();
	}
	
	@Override
	public MavenPomInternal getPom() {
		return publication.getPom();
	}
	
	@Override
	public void setAlias(boolean alias) {
		publication.setAlias(alias);
	}
	
	@Override
	public PublicationArtifactSet<MavenArtifact> getPublishableArtifacts() {
		return publication.getPublishableArtifacts();
	}
	
	@Override
	public void setPomGenerator(TaskProvider<? extends Task> pomGenerator) {
		publication.setPomGenerator(pomGenerator);
	}
	
	@Override
	public void setModuleDescriptorGenerator(TaskProvider<? extends Task> moduleMetadataGenerator) {
		publication.setModuleDescriptorGenerator(moduleMetadataGenerator);
	}
	
	@Override
	public void allPublishableArtifacts(Action<? super MavenArtifact> action) {
		publication.allPublishableArtifacts(action);
	}
	
	@Override
	public MutableMavenProjectIdentity getMavenProjectIdentity() {
		return publication.getMavenProjectIdentity();
	}
	
	@Override
	public void whenPublishableArtifactRemoved(Action<? super MavenArtifact> action) {
		publication.whenPublishableArtifactRemoved(action);
	}
	
	@Override
	public Set<MavenDependency> getApiDependencyConstraints() {
		return publication.getApiDependencyConstraints();
	}
	
	@Override
	public MavenArtifact addDerivedArtifact(MavenArtifact originalArtifact, DerivedArtifact file) {
		return publication.addDerivedArtifact(originalArtifact, file);
	}
	
	@Override
	public Set<MavenDependency> getRuntimeDependencyConstraints() {
		return publication.getRuntimeDependencyConstraints();
	}
	
	@Override
	public Set<MavenDependency> getImportDependencyConstraints() {
		return publication.getImportDependencyConstraints();
	}
	
	@Override
	public Set<MavenDependencyInternal> getApiDependencies() {
		return publication.getApiDependencies();
	}
	
	@Override
	public Set<MavenDependencyInternal> getRuntimeDependencies() {
		return publication.getRuntimeDependencies();
	}
	
	@Override
	public Set<MavenDependencyInternal> getOptionalApiDependencies() {
		return publication.getOptionalApiDependencies();
	}
	
	@Override
	public Set<MavenDependencyInternal> getOptionalRuntimeDependencies() {
		return publication.getOptionalRuntimeDependencies();
	}
	
	@Override
	public MavenNormalizedPublication asNormalisedPublication() {
		return publication.asNormalisedPublication();
	}
	
	@Override
	public String determinePackagingFromArtifacts() {
		return publication.determinePackagingFromArtifacts();
	}
	
	@Override
	public void publishWithOriginalFileName() {
		publication.publishWithOriginalFileName();
	}
	
	@Override
	public void removeDerivedArtifact(MavenArtifact artifact) {
		publication.removeDerivedArtifact(artifact);
	}
	
	@Override
	public PublishedFile getPublishedFile(PublishArtifact source) {
		return publication.getPublishedFile(source);
	}
	
	@Override
	public VersionMappingStrategyInternal getVersionMappingStrategy() {
		return publication.getVersionMappingStrategy();
	}
	
	@Override
	public boolean writeGradleMetadataMarker() {
		return publication.writeGradleMetadataMarker();
	}
	
	@Override
	public boolean isPublishBuildId() {
		return publication.isPublishBuildId();
	}
	
	@Override
	public void pom(Action<? super MavenPom> configure) {
		publication.pom(configure);
	}
	
	@Override
	public void from(SoftwareComponent component) {
		publication.from(component);
	}
	
	@Override
	public MavenArtifact artifact(Object source) {
		return publication.artifact(source);
	}
	
	@Override
	public MavenArtifact artifact(Object source, Action<? super MavenArtifact> config) {
		return publication.artifact(source, config);
	}
	
	@Override
	public void setArtifacts(Iterable<?> sources) {
		publication.setArtifacts(sources);
	}
	
	@Override
	public MavenArtifactSet getArtifacts() {
		return publication.getArtifacts();
	}
	
	@Override
	public String getGroupId() {
		return publication.getGroupId();
	}
	
	@Override
	public void setGroupId(String groupId) {
		publication.setGroupId(groupId);
	}
	
	@Override
	public String getArtifactId() {
		return publication.getArtifactId();
	}
	
	@Override
	public void setArtifactId(String artifactId) {
		publication.setArtifactId(artifactId);
	}
	
	@Override
	public String getVersion() {
		return publication.getVersion();
	}
	
	@Override
	public void setVersion(String version) {
		publication.setVersion(version);
	}
	
	@Override
	public void versionMapping(Action<? super VersionMappingStrategy> configureAction) {
		publication.versionMapping(configureAction);
	}
	
	@Override
	public void suppressPomMetadataWarningsFor(String variantName) {
		publication.suppressPomMetadataWarningsFor(variantName);
	}
	
	@Override
	public void suppressAllPomMetadataWarnings() {
		publication.suppressAllPomMetadataWarnings();
	}
	
}
