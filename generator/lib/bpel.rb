require 'lib/generator_utils'
include GeneratorUtils

module BPEL
	module_function
	def create_bpel id
		cd "../petals/bpel"		
		create_su_package id
		create_sa_package id
		cd "../../scripts"
	end

	def create_su_package id
		cp "../../workspace/SM#{id}.wsdl", "SM#{id}.wsdl"
		cp "../../resources/wsdl/smregistry.wsdl", "smregistry.wsdl"
		cp "../../resources/wsdl/smregistry_xsd_1", "smregistry_xsd_1"

    mkdir_p "META-INF"
    
		su_jbi = File.open("../../resources/bpel/su-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", su_jbi	

		bpel = File.open("../../resources/bpel/supermarket.bpel", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "supermarket.bpel", bpel	

		artifacts = File.open("../../resources/bpel/supermarketArtifacts.wsdl", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "supermarketArtifacts.wsdl", artifacts

		definition = File.open("../../resources/bpel/supermarketDefinition.wsdl", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "supermarketDefinition.wsdl", definition	
				
		compact_petals_files "su-BPEL-SM#{id}-provide.zip", "SM#{id}.wsdl", "smregistry.wsdl", 
		                                                    "smregistry_xsd_1", "supermarket.bpel",
		                                                    "supermarketArtifacts.wsdl", "supermarketDefinition.wsdl"
	end

	def create_sa_package id
		mkdir_p "META-INF"
		sa_jbi = File.open("../../resources/bpel/sa-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", sa_jbi	
				
		compact_petals_files "sa-BPEL-SM#{id}-provide.zip", "su-BPEL-SM#{id}-provide.zip"
	end
end

		
