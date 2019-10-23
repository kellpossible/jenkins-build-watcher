# Jenkins Build Watcher

This project is a plugin for IntelliJ based IDEs to add a panel for the purpose of tracking build jobs on a Jenkins build server. 

It can be configured to watch specific jobs, or it can be configured to watch jobs which match a pattern. The pattern may be associated with the current git branch (and other local branches which have remotes), to allow jobs which are triggered by continuous integration to be tracked automatically.

## Features

- [ ] browse and select jobs to watch
- [ ] notifications upon job status changes
- [ ] stop/start/restart jobs
- [ ] select jobs using a pattern
- [ ] select jobs associated with git branches
- [ ] highlight important jobs (e.g. current git branch)