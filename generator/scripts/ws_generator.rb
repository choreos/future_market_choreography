require 'fileutils'
include FileUtils

require "./config"
require './lib/wsdl'
require './lib/adaptor'
require 'open3.rb'

puts `pwd`

number_of_ws = ARGV[0].to_i

(1..number_of_ws).each do |id|
	#Generating web services
	WSDL.generate_wsdl id
	
	#Creating web services implementation using wsdl2java
	WSDL.implement_ws id		
end

Adaptor.adapt_ws "#{ROOT_DIR}/workspace/eu/choreos/services/.", number_of_ws

#Start ws server
cd "#{ROOT_DIR}/workspace"
`ant SM#{number_of_ws}PortTypeServer`
puts "#{$?}"


