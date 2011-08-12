require 'fileutils'
include FileUtils

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

		su_jbi = File.open("../../resources/soap/consume/su-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", su_jbi	
				
		zip "su-SOAP-SM#{id}-consume.zip", "META-INF"				
	end

	def create_sa_package id
		mkdir_p "META-INF"
		sa_jbi = File.open("../../resources/soap/consume/sa-jbi.xml", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "META-INF/jbi.xml", sa_jbi	

		zip_j "sa-SOAP-SM#{id}-consume.zip", "su-SOAP-SM#{id}-consume.zip"				
		zip "sa-SOAP-SM#{id}-consume.zip", "META-INF"				
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

		
