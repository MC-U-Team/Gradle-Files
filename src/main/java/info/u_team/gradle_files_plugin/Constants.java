package info.u_team.gradle_files_plugin;

public class Constants {
	
	public static final String EXTENSION_NAME = "gradlefiles";
	
	public static final String CONFIG_FILE = "build.properties";
	
	public static final String U_TEAM_MAVEN_NAME = "U-Team-Maven";
	public static final String U_TEAM_MAVEN_URL = "https://repo.u-team.info";
	
	public static final String KEYSTORE = "keystore";
	public static final String KEYSTORE_ALIAS = KEYSTORE + ".alias";
	public static final String KEYSTORE_PASSWORD = KEYSTORE + ".password";
	public static final String KEYSTORE_KEY_PASSWORD = KEYSTORE + ".password_key";
	public static final String KEYSTORE_FINGERPRINT = KEYSTORE + ".fingerprint";
	
	public static final String MULTILOADER_MAIN_PROJECT_SETTING = "multiLoaderMainProject";
	
	public static final String BUILD_PROPERTY = "createBuild";
	public static final String HEADLESS_BUILD_PROPERTY = "headlessBuild";
	public static final String GIT_REPOSITORY_NAME = "patchVersion/gitRepository";
	public static final String VERSIONING_BRANCH = "versioning";
	public static final String PATCH_FILE = "patch";
	public static final String COMMIT_FORCE_MESSAGE = "Commit forced, because of new build";
	public static final String UPDATE_BUILD_NUMBER_TASK = "updateBuildNumber";
	public static final String CHECK_GIT_TASK = "checkGit";
	public static final String CREATE_GIT_TAG_TASK = "createGitTag";
	public static final String RELEASE_MOD_TASK = "releaseMod";
}
