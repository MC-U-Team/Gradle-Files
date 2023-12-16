package info.u_team.gradle_files_plugin.util

import org.apache.commons.lang3.StringUtils

class NamingUtil {

	static String subProjectName(final String rootProjectName, String projectName) {
		return "${rootProjectName}_${StringUtils.capitalize(projectName)}"
	}
}
