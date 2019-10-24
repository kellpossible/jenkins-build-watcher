package org.jenkinsbuildwatcher.panel;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import org.jenkinsbuildwatcher.jenkins.JenkinsStatus;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;

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
                rootPanel.repaint(); //todo: broken
            }
            if (message == JenkinsStatus.StatusMessage.ERROR) {
                rootPanel.removeAll();
                rootPanel.add(new JLabel("Error during status update"));
                JButton button = new JButton("Retry");
                button.addActionListener((actionEvent) -> {
                    //todo need to find a different way to process events
                    SwingUtilities.invokeLater(() -> jenkinsStatus.query());
                });
                rootPanel.add(button);
                rootPanel.repaint(); //todo: broken
            }
        });

        jenkinsStatus.query();
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
