require 'fileutils'
include FileUtils

require '../lib/wsdl'
require '../lib/adaptor'

number_of_ws = ARGV[0].to_i

(1..number_of_ws).each do |id|
	#Generating web services
	WSDL.generate_wsdl id
	
	#Creating web services implementation using wsdl2java
	WSDL.implement_ws id		
end

Adaptor.adapt_ws "../workspace/eu/choreos/services/.", number_of_ws

#Start ws server
cd "../workspace"
`ant SM#{number_of_ws}PortTypeServer`
