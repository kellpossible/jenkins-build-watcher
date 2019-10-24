package org.jenkinsbuildwatcher.settings;

import com.intellij.credentialStore.CredentialAttributes;
import com.intellij.ide.passwordSafe.PasswordSafe;
import com.intellij.openapi.project.Project;

import java.util.Optional;

public class PasswordManager {
    private final JBWSettings settings;

    public PasswordManager(Project project)
    {
        this.settings = JBWSettings.getInstance(project);
    }

    /**
     * Set the password for accessing the Jenkins build server API.
     *
     * @param password the password to store
     */
    void setPassword(String password)
    {
        PasswordSafe.getInstance().setPassword(createCredentialAttributes(), password);
    }

    public Optional<String> getPassword()
    {
        CredentialAttributes credentialAttributes = createCredentialAttributes();
        return Optional.ofNullable(PasswordSafe.getInstance().getPassword(credentialAttributes));
    }

    private CredentialAttributes createCredentialAttributes() {
        String username = settings.getUsername();
        String serverAddress = settings.getServerAddress();

        if (username == null || serverAddress == null)
        {
            throw new RuntimeException("username and server address have not yet been set, unable to create credentials");
        }

        String key = username + "," + serverAddress;
        return new CredentialAttributes("JenkinsBuildWatcherSettings", key);
    }
}
