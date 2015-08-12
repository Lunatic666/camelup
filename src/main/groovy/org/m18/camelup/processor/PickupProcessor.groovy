package org.m18.camelup.processor

import org.slf4j.*
import groovy.json.*
import org.apache.camel.*
import org.apache.log4j.*
import groovy.util.logging.*
import groovy.util.ConfigObject

@Log4j
/**
 * @author Wolfram Huesken <woh@wolframite.net>
 */
class PickupProcessor implements Processor {

	def context
	def config
	def jsonSlurper

	public PickupProcessor(context, config) {
		jsonSlurper = new JsonSlurper()
		this.context = context
		this.config = config
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		def pickupMap = jsonSlurper.parse(exchange.getIn().getBody())
		def endPointURI = String.format(config.route.Pickup.sftp,
			pickupMap['server'], pickupMap['file']
		)

		PollingConsumer consumer = context.getEndpoint(endPointURI).createPollingConsumer()
		consumer.start()
		consumer.receive()
	}

}
