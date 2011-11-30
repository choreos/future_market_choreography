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
    
    @id = id
    substitute("../../resources/bpel/su-jbi.erb.xml", "META-INF/jbi.xml")
    substitute("../../resources/bpel/supermarket.erb.bpel", "supermarket.bpel")
		substitute("../../resources/bpel/supermarketArtifacts.erb.wsdl", "supermarketArtifacts.wsdl")
		substitute("../../resources/bpel/supermarketDefinition.erb.wsdl", "supermarketDefinition.wsdl")
				
		compact_petals_files "su-BPEL-SM#{id}-provide.zip", "SM#{id}.wsdl", "smregistry.wsdl", 
		                                                    "smregistry_xsd_1", "supermarket.bpel",
		                                                    "supermarketArtifacts.wsdl", "supermarketDefinition.wsdl"
	end

	def create_sa_package id
		mkdir_p "META-INF"
		@id = id
		substitute("../../resources/bpel/sa-jbi.erb.xml", "META-INF/jbi.xml")
				
		compact_petals_files "sa-BPEL-SM#{id}-provide.zip", "su-BPEL-SM#{id}-provide.zip"
	end
	
end

		
