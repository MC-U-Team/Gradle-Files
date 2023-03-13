package info.u_team.gradle_files_plugin.util

import org.gradle.api.Project
import org.gradle.api.plugins.ExtraPropertiesExtension

import groovy.transform.CompileStatic
import groovy.transform.Memoized

@CompileStatic
class GradleFilesUtil {
	
	static def getProjectConfig(final Project project) {
		return getExtraProperties(project).get("config")
	}
	
	static boolean isMainProject(final Project project) {
		return project.is(getMainProject(project))
	}
	
	@Memoized
	static Project getMainProject(final Project project) {
		final def extraProperties = getExtraProperties(project)
		
		if(extraProperties.has("mainProject")) {
			final String mainProject = extraProperties.get("mainProject")
			return project.project(mainProject)
		}
		return project
	}
	
	
	
	/*@Memoized
	 static boolean isLoaderProject(final Project project) {
	 final def extraProperties = getExtraProperties(project)
	 if(extraProperties.has("loaderProjects")) {
	 final def loaderProjects = extraProperties.get("loaderProjects") as List<String>
	 return loaderProjects.any { loaderProject ->
	 project.is(project.project(loaderProject))
	 }
	 }
	 return false
	 }*/
	
	@Memoized
	static boolean isRootProject(final Project project) {
		return project.is(getRootProject(project))
	}
	
	@Memoized
	static Project getRootProject(final Project project) {
		def rootProject = project.rootProject
		while(!rootProject.is(rootProject.rootProject)) {
			rootProject = rootProject.rootProject
		}
		return rootProject
	}
	
	static ExtraPropertiesExtension getExtraProperties(final Project project) {
		return project.extensions.extraProperties
	}
}
