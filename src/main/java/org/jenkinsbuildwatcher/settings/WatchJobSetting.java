package org.jenkinsbuildwatcher.settings;

/**
 * A setting to be stored in {@link JBWSettings}, for watching
 * a particular job on the configured jenkins server.
 */
public class WatchJobSetting {
    private String jobName;
    private String folderName;

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
