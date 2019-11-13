# Jenkins Build Watcher

**Note:** This project is still in its infancy, and not currently an operational plugin. When it is ready, it will be published to the plugin marketplace.

This project is a plugin for IntelliJ based IDEs to add a panel for the purpose of tracking build jobs on a Jenkins build server. 

It can be configured to watch specific jobs, or it can be configured to watch jobs which match a pattern. The pattern may be associated with the current git branch (and other local branches which have remotes), to allow jobs which are triggered by continuous integration to be tracked automatically.

## Testing

Integration testing is currently done using [docker](https://www.docker.com/)
as per the instructions [here](https://github.com/jenkinsci/docker/blob/master/README.md).

```
docker pull jenkins/jenkins:lts
docker run -p 8080:8080 -p 50000:50000 -v jenkins_home:/var/jenkins_home jenkins/jenkins:lts
```

## Features

- [x] store server address, and authentication in per-project settings
- [ ] browse and select jobs to watch
- [ ] notifications upon job status changes
- [ ] stop/start/restart jobs
- [ ] select jobs using a pattern
- [ ] select jobs associated with git branches
- [ ] highlight important jobs (e.g. current git branch)

## Resources

Resources I used to help create this plugin:

- [Application Configurable](http://corochann.com/intellij-plugin-development-introduction-applicationconfigurable-projectconfigurable-873.html)
- [Configurable IDEA](https://sites.google.com/site/malenkov/java/150403)
