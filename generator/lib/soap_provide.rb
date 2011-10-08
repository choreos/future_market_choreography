require 'lib/generator_utils'
include GeneratorUtils


module SOAP_Provide
	module_function
	def create_soap_provider id
	  create_dir "../petals/soap-provide"
		cd "../petals/soap-provide"		
		create_su_package id
		create_sa_package id
		cd "../../scripts"
	end

	def create_su_package id
		cp "../../workspace/SM#{id}.wsdl", "SM#{id}.wsdl"

    mkdir_p "META-INF"
		@id = id
		substitute("../../resources/soap/provide/su-jbi.erb.xml", "META-INF/jbi.xml")
				
		compact_petals_files "su-SOAP-SM#{id}-provide.zip", "SM#{id}.wsdl"	
	end

	def create_sa_package id
		mkdir_p "META-INF"
		@id = id		
		substitute("../../resources/soap/provide/sa-jbi.erb.xml", "META-INF/jbi.xml")
				
		compact_petals_files "sa-SOAP-SM#{id}-provide.zip", "su-SOAP-SM#{id}-provide.zip"
			
	end

end

		
