require 'fileutils'
include FileUtils

require '../lib/wsdl'
require '../lib/adaptor'
require '../lib/soap_provide'
require '../lib/soap_consume'
require '../lib/bpel'

number_of_ws = ARGV[0].to_i

(1..number_of_ws).each do |id|
	#Generating web services
	WSDL.generate_wsdl id
	
	#Creating web services implementation using wsdl2java
	WSDL.implement_ws id

	#Creating petals provide assemblies
	SOAP_Provide.create_soap_provider id

	#Creating petals bpel assemblies
	BPEL.create_bpel id

	#Creating petals bpel assemblies
	SOAP_Consume.create_soap_consume id
end

Adaptor.adapt_ws "../workspace/eu/choreos/services/.", number_of_ws

#Start ws server
cd "../workspace"
`ant SM#{number_of_ws}PortTypeServer`


