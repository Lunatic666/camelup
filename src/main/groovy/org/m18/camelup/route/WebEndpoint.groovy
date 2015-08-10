package org.m18.camelup.route

import net.wolframite.camel.ConfigRouteBuilder
import org.m18.camelup.processor.WebEndpointProcessor

/**
 * @author Wolfram Huesken <woh@wolframite.net>
 */
class WebEndpoint extends ConfigRouteBuilder {

	def webEndpointProcessor

	public WebEndpoint(ConfigObject config) {
		super(config)
		webEndpointProcessor = new WebEndpointProcessor(config)
	}

	@Override
	public void configure() {
		from(config.route.WebEndpoint.from).routeId(getClass().getSimpleName())
			.convertBodyTo(String.class)
			.wireTap(config.route.WebEndpoint.wireTap)
			.process(webEndpointProcessor).id(config.route.WebEndpoint.names.WebEndpointProcessor)
			.validate(header(WebEndpointProcessor.HEADER_VALID_REQUEST).isEqualTo(true))
			.to(config.route.WebEndpoint.to).id(config.route.WebEndpoint.names.to)
	}
}
