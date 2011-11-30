require 'fileutils'
include FileUtils

require "./config"
require 'lib/wsdl'
require 'lib/adaptor'
require 'lib/soap_provide'
require 'lib/soap_consume'
require 'lib/bpel'

def generate_supermarket_services(number_of_ws)
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
end

def install_service_assemblies_into_petals
  copy_entry("#{ROOT_DIR}/petals/soap-provide/", "#{PETALS_PLATAFORM_DIR}/install/")
  copy_entry("#{ROOT_DIR}/petals/bpel/", "#{PETALS_PLATAFORM_DIR}/install/")
  copy_entry("#{ROOT_DIR}/petals/soap-consume/", "#{PETALS_PLATAFORM_DIR}/install/")
end

def start_ws_server(number_of_ws)
  puts "starting ws server..."
  cd "#{ROOT_DIR}/workspace"
  `ant SM#{number_of_ws}PortTypeServer`  
end

def run_main
  number_of_ws = ARGV[0].to_i
  generate_supermarket_services(number_of_ws)
  Adaptor.adapt_ws "#{ROOT_DIR}/workspace/eu/choreos/services/.", number_of_ws
  install_service_assemblies_into_petals
  start_ws_server(number_of_ws)
end

begin 
  run_main
rescue SystemExit, Interrupt
  cd ROOT_DIR
  `ruby scripts/clean.rb`
end



