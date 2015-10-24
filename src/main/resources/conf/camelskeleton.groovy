route.routePackage = "org.m18.camelup.route"
route.name = [ 'WebEndpoint', 'Pickup' ]

route.WebEndpoint.validServers = ['midgard.m18.org', 'mail.m18.org']
route.WebEndpoint.validTypes = ['web', 'mail', 'config', 'db']

route.WebEndpoint.from = "jetty:http://0.0.0.0:8080/glong"
route.WebEndpoint.wireTap = "log:org.m18.camelup.WireTap?level=DEBUG"
route.WebEndpoint.to = "rabbitmq://localhost/backup?username=guest&password=guest&" +
	"autoAck=false&" +
	"autoDelete=true&" +
	"durable=false&" +
	""

route.WebEndpoint.names.to = "Logger"
route.WebEndpoint.names.WebEndpointProcessor = "WebEndpointProcessor"

route.Pickup.from = "rabbitmq://localhost/backup?username=guest&password=guest&" +
	"autoAck=false&" +
	"autoDelete=true&" +
	"durable=false"

route.Pickup.sftp = "sftp://camelup@%s/backup?fileName=%s&" +
	"knownHostsFile=/Users/whuesken/.ssh/known_hosts&" +
	"delete=true&" +
	"streamDownload=true&" +
	"preferredAuthentications=publickey&" +
	"privateKeyFile=/Users/whuesken/Copy/weg/BackupKeys/backup"

route.Pickup.to = "file:" + System.getProperty("user.home") + '/Downloads/%s/%s'
