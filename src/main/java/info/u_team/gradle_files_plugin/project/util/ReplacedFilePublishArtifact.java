package info.u_team.gradle_files_plugin.project.util;

import java.io.File;
import java.util.Date;

import org.gradle.api.artifacts.PublishArtifact;
import org.gradle.api.tasks.TaskDependency;

/**
 * Publish artifact that keeps the coordinates of the original artifact, but publishes the file of the replacement
 */
public class ReplacedFilePublishArtifact implements PublishArtifact {
	
	private final PublishArtifact original;
	private final PublishArtifact replacement;
	
	public ReplacedFilePublishArtifact(PublishArtifact original, PublishArtifact replacement) {
		this.original = original;
		this.replacement = replacement;
	}
	
	@Override
	public String getName() {
		return original.getName();
	}
	
	@Override
	public String getExtension() {
		return original.getExtension();
	}
	
	@Override
	public String getType() {
		return original.getType();
	}
	
	@Override
	public String getClassifier() {
		return original.getClassifier();
	}
	
	@Override
	public File getFile() {
		return replacement.getFile();
	}
	
	@Override
	public Date getDate() {
		return replacement.getDate();
	}
	
	@Override
	public TaskDependency getBuildDependencies() {
		return replacement.getBuildDependencies();
	}
}
