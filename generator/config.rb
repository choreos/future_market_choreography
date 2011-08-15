
CXF_DIR_BIN=ENV['CXF_HOME']+"/bin"
raise "Please set CXF_HOME environment variable." if CXF_DIR_BIN.nil?


ROOT_DIR=(`pwd`).chomp