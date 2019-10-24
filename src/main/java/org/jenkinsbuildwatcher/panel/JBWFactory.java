package org.jenkinsbuildwatcher.panel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jenkinsbuildwatcher.jenkins.JenkinsStatus;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class JBWFactory implements ToolWindowFactory {
    private JenkinsStatus jenkinsStatus;

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        jenkinsStatus = new JenkinsStatus(project);
        JPanel rootPanel = new JPanel();
        rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
        toolWindow.getComponent().add(rootPanel);

        jenkinsStatus.addListener((status, message) -> {
            if (message == JenkinsStatus.StatusMessage.UPDATED) {
                rootPanel.removeAll();
                status.getJobs().forEach(jobName -> {
                    rootPanel.add(new JLabel(jobName));
                });
            }
            if (message == JenkinsStatus.StatusMessage.ERROR) {
                rootPanel.removeAll();
                rootPanel.add(new JLabel("Error during status update"));
            }
        });
    }

    @Override
    public void init(ToolWindow window) {

    }

    @Override
    public boolean shouldBeAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public boolean isDoNotActivateOnStart() {
        return false;
    }
}
