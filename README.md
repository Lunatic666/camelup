# Camel Backup

The purpose of this project is to backup files from remote servers without putting private keys on them

## Workflow Step-By-Step

- Remote server creates a backup with whatever solution you prefer
- Remote server sends an HTTP request to the backup server
- Backup server validates the request and puts it in a [beanstalkd](http://kr.github.io/beanstalkd) queue
- Backup server listens on the queue
- Backup server logs in to the remote server
- Backup server copies over the file and deletes it on success

## Prerequisites

- Java 1.7+

## Setup

The bootstrap code is taken from the [CamelSkeleton](https://github.com/Lunatic666/camelskeleton). I made an interesting modification: In the skeleton the package of the routes which are loaded is hardwired, that's configurable now. I'll change that in the skeleton, too. Tomorrow. Soon. When I have time...

- `git clone git@github.com:Lunatic666/camelup.git`
- Edit the configuration file (src/main/resources/conf/camelskeleton.groovy)
- `./gradlew run`
