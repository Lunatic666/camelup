package org.m18.camelup.processor

import org.slf4j.*
import groovy.json.*
import org.apache.camel.*
import org.apache.log4j.*
import groovy.util.logging.*
import org.apache.camel.impl.*
import groovy.util.ConfigObject

@Log4j
/**
 * @author Wolfram Huesken <woh@wolframite.net>
 */
class PickupProcessor implements Processor {

	def context
	def config

	def jsonSlurper
	def csTpl
	def prodTpl

	public PickupProcessor(context, config) {
		this.context = context
		this.config = config

		jsonSlurper = new JsonSlurper()
		csTpl = new DefaultConsumerTemplate(context)
		prodTpl = new DefaultProducerTemplate(context)
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		def pickupMap = jsonSlurper.parse(exchange.getIn().getBody())
		def consumerExchange

		def endPointURI = String.format(config.route.Pickup.sftp,
			pickupMap['server'], pickupMap['file']
		)

		def producerURI = String.format(config.route.Pickup.to,
			pickupMap['server'], pickupMap['type']
		)

		csTpl.start()
		prodTpl.start()

		consumerExchange = csTpl.receive(endPointURI)
		prodTpl.send(producerURI, consumerExchange)

		prodTpl.stop()
		csTpl.doneUoW(consumerExchange)
		csTpl.stop()
	}

}
