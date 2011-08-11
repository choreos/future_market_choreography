require 'fileutils'
include FileUtils

module Adaptor
	module_function
	def adapt_ws dir_name, number_of_ws
		populate_ws_impl dir_name, number_of_ws
		create_ws_server dir_name, number_of_ws	
	end
	
	def populate_ws_impl dir_name, number_of_ws
		(1..number_of_ws).each do |id|			
				data = File.open("../resources/java/SMData.java", "r").readlines.join.gsub('#{id}', id.to_s)		
		
				file_name = "#{dir_name}/SM#{id}PortTypeImpl.java"
				content = File.open(file_name).readlines.to_s
				content = content.sub("0.0", "SM#{id}Data.getPrice(arg0)")
				
				open_file_and_write file_name, content
				open_file_and_write "#{dir_name}/SM#{id}Data.java", data
		end
	end

	def create_ws_server dir_name, number_of_ws
		server_class = "package eu.choreos.services;\n\n"
		server_class << "import javax.xml.ws.Endpoint;\n\n"
		server_class << "public class SM#{number_of_ws}PortType_SM#{number_of_ws}Port_Server{\n\n"
		server_class << "\tpublic static void main(String args[]) throws java.lang.Exception {\n\n"

		(1..number_of_ws).each do |i| 
			server_class << "\t\tObject implementor#{i} = new SM#{i}PortTypeImpl();\n"
			server_class << "\t\tString address#{i} = \"http://localhost:4321/WS/SM#{i}\";\n"
			server_class << "\t\tEndpoint.publish(address#{i}, implementor#{i});\n\n"
		end
	
		server_class << "\t}\n"
		server_class << "}"

		open_file_and_write "#{dir_name}/SM#{number_of_ws}PortType_SM#{number_of_ws}Port_Server.java", server_class
	end

	def open_file_and_write file_name, content
	    file = File.new(file_name, "w")
	    file.puts content
	    file.close
	end
end
