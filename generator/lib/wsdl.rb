require 'lib/generator_utils'
include GeneratorUtils

module WSDL
	module_function
	def generate_wsdl id
		wsdl = File.open("#{ROOT_DIR}/resources/wsdl/smx.wsdl", "r").readlines.join.gsub('#{id}', id.to_s)
		open_file_and_write "#{ROOT_DIR}/workspace/SM#{id}.wsdl", wsdl
	end

	module_function
	def implement_ws id
		cd "#{ROOT_DIR}/workspace"
		`#{CXF_DIR_BIN}/wsdl2java -ant -impl -server -d . SM#{id}.wsdl`
		cd "#{ROOT_DIR}/scripts"
	end
end
