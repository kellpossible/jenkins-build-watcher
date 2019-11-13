package org.jenkinsbuildwatcher.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Store per-project settings for this plugin in <b>JenkinsBuildWatcherSettings.xml</b>
 */
@State(
        name="JenkinsBuildWatcherSettings",
        storages={@Storage("JenkinsBuildWatcherSettings.xml")}
)
public class JBWSettings implements PersistentStateComponent<JBWSettings> {
    private String serverAddress;
    private String username;
    private ArrayList<WatchJobSetting> watchJobSettings;

    /**
     * @return the address of the jenkins server to query
     */
    public String getServerAddress() {
        return serverAddress;
    }

    /**
     * @param serverAddress set the address of the jenkins server to query
     */
    void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    /**
     * @return the username to authenticate with the jenkins server
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to authenticate with the jenkins server
     */
    void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    @Override
    public JBWSettings getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull JBWSettings state) {
        XmlSerializerUtil.copyBean(state, this);
    }

    /**
     * @param project the project these settings are associated with
     * @return the instance of settings associated with the specified project
     */
    public static JBWSettings getInstance(Project project) {
        return ServiceManager.getService(project, JBWSettings.class);
    }

    /**
     * @return whether or not the settings have all the credentials required for retrieving/storing the password
     *         in {@link PasswordManager}.
     */
    public boolean hasPasswordCredentials() {
        return serverAddress != null && username != null;
    }
}
