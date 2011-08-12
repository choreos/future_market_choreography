require 'fileutils'
include FileUtils

CXF_DIR_BIN="/home/besson/Downloads/apache-cxf-2.4.1/bin"

module WSDL
	module_function
	def generate_wsdl id
		wsdl = File.open("../resources/wsdl/smx.wsdl", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "../workspace/SM#{id}.wsdl", wsdl
	end

	module_function
	def implement_ws id
		cd "../workspace"
		`#{CXF_DIR_BIN}/wsdl2java -ant -impl -server -d . SM#{id}.wsdl`
		cd "../scripts"
	end

	def open_file_and_write file_name, content
	    file = File.new(file_name, "w")
	    file.puts content
	    file.close
	end
end
