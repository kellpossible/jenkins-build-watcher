package org.jenkinsbuildwatcher.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 *
 */
public class JBWConfigurable implements Configurable {
    private JBWSettingsForm settingsForm;
    private JBWSettings settings;
    private PasswordManager passwordManager;

    public JBWConfigurable(Project project) {
        this.settings = JBWSettings.getInstance(project);
        this.passwordManager = new PasswordManager(project);
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return "Jenkins Build Watcher"; //keep in sync with plugin.xml
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        settingsForm = new JBWSettingsForm();
        settingsForm.setup(settings, passwordManager);
        return settingsForm.getRootPanel();
    }

    @Override
    public boolean isModified() {
        return settingsForm.isModified();
    }

    @Override
    public void reset() {
        settingsForm.reset();
    }

    @Override
    public void apply() {
        settingsForm.apply();
    }

    @Override
    public void disposeUIResources() {
        settingsForm = null;
    }
}
