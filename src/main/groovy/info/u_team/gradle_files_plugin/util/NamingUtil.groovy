package info.u_team.gradle_files_plugin.util

import org.apache.commons.lang3.StringUtils

import groovy.transform.CompileStatic

@CompileStatic
class NamingUtil {
	
	private static final String INVALID_CHARACTERS = "[\\\\\\/\\:\\<\\>\\\"\\?\\*\\|]"
	
	static String subProjectName(final String rootProjectName, final String projectName) {
		final def sanitizedString = projectName.split(INVALID_CHARACTERS).collect { namePart ->
			StringUtils.capitalize(namePart)
		}.join("_")
		return "${rootProjectName}_${sanitizedString}"
	}
}
