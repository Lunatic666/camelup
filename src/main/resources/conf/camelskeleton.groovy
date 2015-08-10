route.routePackage = "org.m18.camelup.route"
route.name = [ 'WebEndpoint' ]

route.WebEndpoint.validServers = ['midgard.m18.org', 'mail.m18.org']
route.WebEndpoint.validTypes = ['web', 'mail', 'config']

route.WebEndpoint.from = "jetty:http://0.0.0.0:8080/glong"
route.WebEndpoint.to = "log:org.m18.camelup.WebEndpoint?level=INFO"
route.WebEndpoint.wireTap = "log:org.m18.camelup.WireTap?level=DEBUG"

route.WebEndpoint.names.to = "Logger"
route.WebEndpoint.names.WebEndpointProcessor = "WebEndpointProcessor"
