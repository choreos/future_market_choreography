require 'lib/generator_utils'
include GeneratorUtils

module SOAP_Consume
	module_function
	def create_soap_consume id
		cd "../petals/soap-consume"		
		create_su_package id
		create_sa_package id
		cd "../../scripts"
	end

	def create_su_package id
		mkdir_p "META-INF"

    @id = id
		substitute("../../resources/soap/consume/su-jbi.erb.xml", "META-INF/jbi.xml")
		compact_petals_files "su-SOAP-SM#{id}-consume.zip"				
	end

	def create_sa_package id
		mkdir_p "META-INF"
		
		@id = id
		substitute("../../resources/soap/consume/sa-jbi.erb.xml", "META-INF/jbi.xml")

		compact_petals_files "sa-SOAP-SM#{id}-consume.zip", "su-SOAP-SM#{id}-consume.zip"							
	end
end

		
