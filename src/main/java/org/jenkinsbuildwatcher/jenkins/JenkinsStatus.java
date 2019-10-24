package org.jenkinsbuildwatcher.jenkins;

import com.intellij.openapi.project.Project;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.BasicConfigurator;
import org.jenkinsbuildwatcher.settings.JBWSettings;
import org.jenkinsbuildwatcher.settings.PasswordManager;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

public class JenkinsStatus {
    private List<String> jobs = Collections.emptyList();
    private JBWSettings settings;
    private PasswordManager passwordManager;
    private QueryThread thread;
    private ArrayList<JenkinsStatusListener> listeners = new ArrayList<>();

    public interface JenkinsStatusListener {
        void listen(JenkinsStatus status, StatusMessage message);
    }

    public enum StatusMessage {
        UPDATED,
        DISCONNECTED,
        ERROR
    }

    public JenkinsStatus(Project project) {
        this.settings = JBWSettings.getInstance(project);
        this.passwordManager = new PasswordManager(project);
    }

    public void addListener(JenkinsStatusListener statusListener) {
        listeners.add(statusListener);
    }

    private void processEvent(StatusMessage message) {
        listeners.forEach(listener -> listener.listen(this, message));
    }

    public void query() {
        thread = new QueryThread();
        thread.start();
    }

    public List<String> getJobs() {
        return jobs;
    }

    private void setJobs(List<String> jobs) {
        this.jobs = jobs;
        processEvent(StatusMessage.UPDATED);
    }

    private class QueryThread extends Thread {
        @Override
        public void run() {
            BasicConfigurator.configure();
            Log4JLogger logger = new Log4JLogger();

            try {
                if (!settings.hasPasswordCredentials()) {
                    processEvent(StatusMessage.ERROR);
                    return;
                }
                System.out.println("address " + settings.getServerAddress());
                System.out.println("username " + settings.getUsername());
                System.out.println("password " + passwordManager.getPassword());

                URI uri = new URI(settings.getServerAddress());
                JenkinsServer jenkins = new JenkinsServer(
                        uri,
                        settings.getUsername(),
                        passwordManager.getPassword().orElseGet(String::new));
//			System.out.println(jenkins.getViews());

			    Map<String, Job> jobs = jenkins.getJobs();
			    List<String> jobNames = jobs.values().stream().map(Job::getFullName).collect(Collectors.toList());
			    setJobs(jobNames);

//                Map<String, Job> jobs = jenkins.getJobs(new FolderJob(FOLDER_JOB_NAME, SERVER_URL + "/" + FOLDER_JOB_URL));
//
//                System.out.println(jobs);
//                System.out.println(jobs.get(JOB_NAME.replace("/", "%2F")));

//			Project project = ProjectManager.getInstance().getOpenProjects()[0];
//			GitRepositoryManager.getInstance(project).getRepositories();
            }
            catch (Exception  e) {
                e.printStackTrace();
                processEvent(StatusMessage.ERROR);
            }
        }
    }
}
