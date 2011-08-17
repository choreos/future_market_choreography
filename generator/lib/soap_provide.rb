require 'lib/generator_utils'
include GeneratorUtils


module SOAP_Provide
	module_function
	def create_soap_provider id
		cd "../petals/soap-provide"		
		create_su_package id
		create_sa_package id
		cd "../../scripts"
	end

	def create_su_package id
		cp "../../workspace/SM#{id}.wsdl", "SM#{id}.wsdl"

    mkdir_p "META-INF"
		
		su_jbi = File.open("../../resources/soap/provide/su-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", su_jbi	
				
		compact_petals_files "su-SOAP-SM#{id}-provide.zip", "SM#{id}.wsdl"	
	end

	def create_sa_package id
		mkdir_p "META-INF"
		
		sa_jbi = File.open("../../resources/soap/provide/sa-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", sa_jbi	
				
		compact_petals_files "sa-SOAP-SM#{id}-provide.zip", "su-SOAP-SM#{id}-provide.zip"
			
	end

end

		
