package org.jenkinsbuildwatcher.settings;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class JBWSettingsForm {
    private JPanel rootPanel;
    private JTextField serverAddress;
    private JLabel serverAddressLabel;
    private JTextField username;
    private JLabel usernameLabel;
    private JPasswordField password;
    private JLabel passwordLabel;
    private JLabel authenticationVerificationLabel;
    private JBWSettings settings;
    private PasswordManager passwordManager;
    private List previousSettings = null;

    void setup(JBWSettings settings, PasswordManager passwordManager) {
        System.out.println("setting up");
        this.settings = settings;
        this.passwordManager = passwordManager;
        reset();
    }

    JPanel getRootPanel() {
        return rootPanel;
    }

    void reset() {
        System.out.println("resetting");
        serverAddress.setText(settings.getServerAddress());
        username.setText(settings.getUsername());

        String passwordString = passwordManager.getPassword().orElseGet(String::new);
        password.setText(passwordString);

        previousSettings = getSettings();
    }

    private List<Object> getSettings() {
        return Arrays.asList(
                serverAddress.getText(),
                username.getText(),
                new String(password.getPassword()));
    }

    boolean isModified() {
        return !previousSettings.equals(getSettings());
    }

    void apply() {
        System.out.println("applying");
        settings.setServerAddress(serverAddress.getText());
        settings.setUsername(username.getText());

        String passwordString = new String(password.getPassword());

        if (passwordString.length() > 0) {
            passwordManager.setPassword(passwordString);
        }

        previousSettings = getSettings();
    }
}
