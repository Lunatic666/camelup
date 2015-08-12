package org.m18.camelup.route

import net.wolframite.camel.ConfigRouteBuilder
import org.m18.camelup.processor.PickupProcessor

/**
 * @author Wolfram Huesken <woh@wolframite.net>
 */
class Pickup extends ConfigRouteBuilder {

	def pickupProcessor

	public Pickup(ConfigObject config) {
		super(config)

		pickupProcessor = new PickupProcessor(getContext(), config)
	}

	@Override
	public void configure() {
		from("beanstalk:backup").process(pickupProcessor)
	}
}
