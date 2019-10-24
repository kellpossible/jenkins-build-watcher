package org.jenkinsbuildwatcher.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name="JenkinsBuildWatcherSettings",
        storages={@Storage("JenkinsBuildWatcherSettings.xml")}
)
public class JBWSettings implements PersistentStateComponent<JBWSettings> {
    private String serverAddress;
    private String username;

    public String getServerAddress() {
        return serverAddress;
    }

    void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getUsername() {
        return username;
    }

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

    public static JBWSettings getInstance(Project project) {
        return ServiceManager.getService(project, JBWSettings.class);
    }
}
