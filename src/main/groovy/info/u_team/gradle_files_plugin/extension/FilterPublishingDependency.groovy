package info.u_team.gradle_files_plugin.extension

import java.util.function.Predicate

import groovy.transform.CompileStatic
import info.u_team.gradle_files_plugin.tool.RemovedMappedDependenciesTool
import info.u_team.gradle_files_plugin.tool.RemovedMappedDependenciesTool.Dependency

@CompileStatic
class FilterPublishingDependency {
	
	static void removePublishingDependency(Predicate<Dependency> predicate) {
		RemovedMappedDependenciesTool.filterPublishingDependency(predicate)
	}
}

