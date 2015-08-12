route.routePackage = "org.m18.camelup.route"
route.name = [ 'WebEndpoint', 'Pickup' ]

route.WebEndpoint.validServers = ['midgard.m18.org', 'mail.m18.org']
route.WebEndpoint.validTypes = ['web', 'mail', 'config']

route.WebEndpoint.from = "jetty:http://0.0.0.0:8080/glong"
route.WebEndpoint.to = "beanstalk://localhost:11300/backup?jobTimeToRun=3600"
route.WebEndpoint.wireTap = "log:org.m18.camelup.WireTap?level=DEBUG"

route.WebEndpoint.names.to = "Logger"
route.WebEndpoint.names.WebEndpointProcessor = "WebEndpointProcessor"

route.Pickup.from = "beanstalk://localhost:11300/backup"
route.Pickup.sftp = "sftp://camelup@%s/backup?fileName=%s&" +
	"knownHostsFile=/Users/whuesken/.ssh/known_hosts&" +
	"delete=true&" +
	"streamDownload=true&" +
	"preferredAuthentications=publickey&" +
	"privateKeyFile=/Users/whuesken/Copy/weg/BackupKeys/backup"

route.Pickup.to = "file:" + System.getProperty("user.home") + '/Downloads'
