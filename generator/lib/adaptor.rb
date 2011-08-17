require 'erb'
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
      new_supermarket_data_class(dir_name, id, number_of_ws)
      change_return_value_of_getPrice_to_method_call(dir_name, id)
		end
	end

  # substitute the ws_server of the original to host |number_of_ws| web services inside it
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
	
	def new_supermarket_data_class(dir_name, id, number_of_ws)
	  @id = id
	  @number_of_ws = number_of_ws
	  @number_of_products = NUMBER_PRODUCTS
	  @price_generator = Proc.new {|i| ((rand(number_of_ws)/number_of_ws.to_f) + i)}
		substitute("#{ROOT_DIR}/resources/java/SMData.erb.java", "#{dir_name}/SM#{id}Data.java")
	end
	
  def change_return_value_of_getPrice_to_method_call(dir_name, id)
    file_name = "#{dir_name}/SM#{id}PortTypeImpl.java"
		content = File.open(file_name).readlines.join
		content = content.sub("0.0", "SM#{id}Data.getPrice(arg0)")
		open_file_and_write file_name, content
	end
	
	def open_file_and_write file_name, content
	    file = File.new(file_name, "w")
	    file.puts content
	    file.close
	end
	
	def substitute(template, dest)
    erb = ERB.new(File.read(template))
    File.open(dest, "w+") do |f|
      f.write(erb.result(binding))
    end
  end
  
end
