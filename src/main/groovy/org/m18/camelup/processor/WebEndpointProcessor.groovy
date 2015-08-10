package org.m18.camelup.processor

import org.slf4j.*
import groovy.json.*
import java.util.regex.Pattern as RPattern
import org.apache.camel.*
import org.apache.log4j.*
import groovy.util.logging.*
import groovy.util.ConfigObject

@Log4j
/**
 * @author Wolfram Huesken <woh@wolframite.net>
 */
class WebEndpointProcessor implements Processor {

	def config
	def jsonSlurper
	public static final String HEADER_VALID_REQUEST = 'HEADER_VALID_REQUEST'
	
	public WebEndpointProcessor(ConfigObject config) {
		this.config = config
		jsonSlurper = new JsonSlurper()
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		exchange.getIn().setHeader(HEADER_VALID_REQUEST, false)
		def valid = true		
		def payload = getBackupMap(exchange.getIn().getBody())
		
		valid &= isValidType(payload)
		valid &= isValidServer(payload)
		valid &= isValidFilename(payload)

		exchange.getIn().setHeader(HEADER_VALID_REQUEST, valid)
	}

	def isValidType(payload) {
		if (payload?.type in config.route.WebEndpoint.validTypes) {
			return true
		}
		
		log.warn(String.format("'%s' is not a valid type", payload?.type))
		return false
	}
	
	def isValidServer(payload) {
		if (payload?.server in config.route.WebEndpoint.validServers) {
			return true
		}
		
		log.warn(String.format("'%s' is not a valid server", payload?.server))
		return false
	}

	def isValidFilename(payload) {
		def filename = payload?.file
		
		if (!filename) { 
			log.warn("Filename is empty")	
			return false
		}

		if (new File(filename).getName() != filename) {
			log.warn("Only bare filenames allowed, no relative or absolute paths")
			return false
		}
		
		if (filename.replaceAll(/[^a-zA-Z0-9\-_\.]/, "") != filename) {
			log.warn("Found illegal characters!")
			return false
		}
		
		return true
	}

	private synchronized Map getBackupMap(String jsonString) {
		jsonSlurper.parseText(jsonString)
	}
}
