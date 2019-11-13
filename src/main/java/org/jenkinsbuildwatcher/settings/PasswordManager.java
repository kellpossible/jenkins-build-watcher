package org.jenkinsbuildwatcher.settings;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.project.Project;

import java.util.Optional;

/**
 * A secure way to store/retrieve a password to use for authenticating with a jenkins server.
 */
public class PasswordManager {
    private final JBWSettings settings;

    public PasswordManager(Project project) {
        this.settings = JBWSettings.getInstance(project);
    }

    /**
     * Set the password for accessing the Jenkins build server API.
     *
     * @param password the password to store
     */
    void setPassword(String password) {
        PasswordSafe.getInstance().setPassword(createCredentialAttributes(), password);
    }

    /**
     * @return the password (if it exists)
     */
    public Optional<String> getPassword() {
        CredentialAttributes credentialAttributes = createCredentialAttributes();
        return Optional.ofNullable(PasswordSafe.getInstance().getPassword(credentialAttributes));
    }

    /**
     * Create the credentials needed to retrieve the password using username and server address
     * from {@link JBWSettings}.
     *
     * @return the credentials
     */
    private CredentialAttributes createCredentialAttributes() {
        if (!settings.hasPasswordCredentials()) {
            throw new RuntimeException("username and server address have not yet been set, unable to create credentials");
        }

        String username = settings.getUsername();
        String serverAddress = settings.getServerAddress();

        String key = username + "," + serverAddress;
        return new CredentialAttributes("JenkinsBuildWatcherSettings", key);
    }
}
