package info.u_team.gradle_files_plugin.util

import org.gradle.api.Project

class ClassUtil {

	static Class<?> findClass(final Project project, final String name) {
		try {
			return Class.forName(name, false, project.buildscript.classLoader)
		} catch(Exception ex) {
			return null
		}
	}
}
