package info.u_team.gradle_files_plugin.util;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.gradle.api.Action;
import org.gradle.api.XmlProvider;
import org.gradle.api.provider.MapProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.publish.internal.versionmapping.VersionMappingStrategyInternal;
import org.gradle.api.publish.maven.MavenDependency;
import org.gradle.api.publish.maven.MavenPomCiManagement;
import org.gradle.api.publish.maven.MavenPomContributor;
import org.gradle.api.publish.maven.MavenPomContributorSpec;
import org.gradle.api.publish.maven.MavenPomDeveloper;
import org.gradle.api.publish.maven.MavenPomDeveloperSpec;
import org.gradle.api.publish.maven.MavenPomDistributionManagement;
import org.gradle.api.publish.maven.MavenPomIssueManagement;
import org.gradle.api.publish.maven.MavenPomLicense;
import org.gradle.api.publish.maven.MavenPomLicenseSpec;
import org.gradle.api.publish.maven.MavenPomMailingList;
import org.gradle.api.publish.maven.MavenPomMailingListSpec;
import org.gradle.api.publish.maven.MavenPomOrganization;
import org.gradle.api.publish.maven.MavenPomScm;
import org.gradle.api.publish.maven.internal.dependencies.MavenDependencyInternal;
import org.gradle.api.publish.maven.internal.publication.MavenPomDistributionManagementInternal;
import org.gradle.api.publish.maven.internal.publication.MavenPomInternal;
import org.gradle.api.publish.maven.internal.publisher.MavenProjectIdentity;

import info.u_team.gradle_files_plugin.tool.RemovedMappedDependenciesTool;

public class DependencyFilteredMavenPomInternal implements MavenPomInternal {
	
	private final MavenPomInternal pom;
	
	public DependencyFilteredMavenPomInternal(MavenPomInternal pom) {
		this.pom = pom;
	}
	
	@SuppressWarnings("unchecked")
	private <E extends Set<? extends MavenDependency>, V> E filterDependencies(E set) {
		return (E) set.stream().filter(dependency -> {
			final RemovedMappedDependenciesTool.Dependency convertedDependency = new RemovedMappedDependenciesTool.Dependency(dependency.getGroupId(), dependency.getArtifactId(), dependency.getVersion());
			return RemovedMappedDependenciesTool.getFilterPublishingDependencies().negate().test(convertedDependency);
		}).collect(Collectors.toCollection(LinkedHashSet::new));
	}
	
	@Override
	public Set<MavenDependency> getApiDependencyManagement() {
		return filterDependencies(pom.getApiDependencyManagement());
	}
	
	@Override
	public Set<MavenDependency> getRuntimeDependencyManagement() {
		return filterDependencies(pom.getRuntimeDependencyManagement());
	}
	
	@Override
	public Set<MavenDependency> getImportDependencyManagement() {
		return filterDependencies(pom.getImportDependencyManagement());
	}
	
	@Override
	public Set<MavenDependencyInternal> getApiDependencies() {
		return filterDependencies(pom.getApiDependencies());
	}
	
	@Override
	public Set<MavenDependencyInternal> getRuntimeDependencies() {
		return filterDependencies(pom.getRuntimeDependencies());
	}
	
	@Override
	public Set<MavenDependencyInternal> getOptionalApiDependencies() {
		return filterDependencies(pom.getOptionalApiDependencies());
	}
	
	@Override
	public Set<MavenDependencyInternal> getOptionalRuntimeDependencies() {
		return filterDependencies(pom.getOptionalRuntimeDependencies());
	}
	
	@Override
	public String getPackaging() {
		return pom.getPackaging();
	}
	
	@Override
	public void setPackaging(String packaging) {
		pom.setPackaging(packaging);
	}
	
	@Override
	public List<MavenPomLicense> getLicenses() {
		return pom.getLicenses();
	}
	
	@Override
	public MavenPomOrganization getOrganization() {
		return pom.getOrganization();
	}
	
	@Override
	public Property<String> getName() {
		return pom.getName();
	}
	
	@Override
	public List<MavenPomDeveloper> getDevelopers() {
		return pom.getDevelopers();
	}
	
	@Override
	public List<MavenPomContributor> getContributors() {
		return pom.getContributors();
	}
	
	@Override
	public MavenPomScm getScm() {
		return pom.getScm();
	}
	
	@Override
	public Property<String> getDescription() {
		return pom.getDescription();
	}
	
	@Override
	public MavenPomIssueManagement getIssueManagement() {
		return pom.getIssueManagement();
	}
	
	@Override
	public MavenPomCiManagement getCiManagement() {
		return pom.getCiManagement();
	}
	
	@Override
	public MavenPomDistributionManagementInternal getDistributionManagement() {
		return pom.getDistributionManagement();
	}
	
	@Override
	public Property<String> getUrl() {
		return pom.getUrl();
	}
	
	@Override
	public List<MavenPomMailingList> getMailingLists() {
		return pom.getMailingLists();
	}
	
	@Override
	public MavenProjectIdentity getProjectIdentity() {
		return pom.getProjectIdentity();
	}
	
	@Override
	public Property<String> getInceptionYear() {
		return pom.getInceptionYear();
	}
	
	@Override
	public void licenses(Action<? super MavenPomLicenseSpec> action) {
		pom.licenses(action);
	}
	
	@Override
	public Action<XmlProvider> getXmlAction() {
		return pom.getXmlAction();
	}
	
	@Override
	public void organization(Action<? super MavenPomOrganization> action) {
		pom.organization(action);
	}
	
	@Override
	public VersionMappingStrategyInternal getVersionMappingStrategy() {
		return pom.getVersionMappingStrategy();
	}
	
	@Override
	public boolean writeGradleMetadataMarker() {
		return pom.writeGradleMetadataMarker();
	}
	
	@Override
	public void developers(Action<? super MavenPomDeveloperSpec> action) {
		pom.developers(action);
	}
	
	@Override
	public void contributors(Action<? super MavenPomContributorSpec> action) {
		pom.contributors(action);
	}
	
	@Override
	public void scm(Action<? super MavenPomScm> action) {
		pom.scm(action);
	}
	
	@Override
	public void issueManagement(Action<? super MavenPomIssueManagement> action) {
		pom.issueManagement(action);
	}
	
	@Override
	public void ciManagement(Action<? super MavenPomCiManagement> action) {
		pom.ciManagement(action);
	}
	
	@Override
	public void distributionManagement(Action<? super MavenPomDistributionManagement> action) {
		pom.distributionManagement(action);
	}
	
	@Override
	public void mailingLists(Action<? super MavenPomMailingListSpec> action) {
		pom.mailingLists(action);
	}
	
	@Override
	public MapProperty<String, String> getProperties() {
		return pom.getProperties();
	}
	
	@Override
	public void withXml(Action<? super XmlProvider> action) {
		pom.withXml(action);
	}
	
}
