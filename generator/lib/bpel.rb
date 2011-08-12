require 'fileutils'
include FileUtils

module BPEL
	module_function
	def create_bpel id
		cd "../petals/bpel"		
		create_su_package id
		create_sa_package id
		cd "../../scripts"
	end

	def create_su_package id
		mkdir_p "META-INF"
		cp "../../workspace/SM#{id}.wsdl", "SM#{id}.wsdl"
		cp "../../resources/wsdl/smregistry.wsdl", "smregistry.wsdl"
		cp "../../resources/wsdl/smregistry_xsd_1", "smregistry_xsd_1"

		su_jbi = File.open("../../resources/bpel/su-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", su_jbi	

		bpel = File.open("../../resources/bpel/supermarket.bpel", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "supermarket.bpel", bpel	

		artifacts = File.open("../../resources/bpel/supermarketArtifacts.wsdl", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "supermarketArtifacts.wsdl", artifacts

		definition = File.open("../../resources/bpel/supermarketDefinition.wsdl", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "supermarketDefinition.wsdl", definition	
				
		zip_j "su-BPEL-SM#{id}-provide.zip", "SM#{id}.wsdl"
		zip_j "su-BPEL-SM#{id}-provide.zip", "smregistry.wsdl"
		zip_j "su-BPEL-SM#{id}-provide.zip", "smregistry_xsd_1"
		zip_j "su-BPEL-SM#{id}-provide.zip", "supermarket.bpel"
		zip_j "su-BPEL-SM#{id}-provide.zip", "supermarketArtifacts.wsdl"
		zip_j "su-BPEL-SM#{id}-provide.zip", "supermarketDefinition.wsdl"

		zip "su-BPEL-SM#{id}-provide.zip", "META-INF"				
	end

	def create_sa_package id
		mkdir_p "META-INF"
		sa_jbi = File.open("../../resources/bpel/sa-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", sa_jbi	
				
		zip_j "sa-BPEL-SM#{id}-provide.zip", "su-BPEL-SM#{id}-provide.zip"
		zip "sa-BPEL-SM#{id}-provide.zip", "META-INF"				
	end
	
	def open_file_and_write file_name, content
	    file = File.new(file_name, "w")
	    file.puts content
	    file.close
	end

	def zip zip_file, *args
	  list = args.join " "
	  `zip -qr0m #{zip_file} #{list} 2>/dev/null`
	end

	def zip_j zip_file, *args
	  list = args.join " "
	  `zip -qr0mj #{zip_file} #{list} 2>/dev/null`
	end
end

		
