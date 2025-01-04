package info.u_team.gradle_files_plugin.util

import org.apache.commons.lang3.RegExUtils
import org.apache.commons.lang3.StringUtils

import groovy.transform.CompileStatic

@CompileStatic
class NamingUtil {

	static String subProjectName(final String rootProjectName, final String projectName) {
		final def sanitizedString = RegExUtils.replaceAll(projectName, "[\\\\\\/\\:\\<\\>\\\"\\?\\*\\|]", "_")
		return "${rootProjectName}_${StringUtils.capitalize(projectName)}"
	}
}
