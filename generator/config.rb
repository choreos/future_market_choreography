# Manual set up of environment variables
PETALS_PLATAFORM_DIR="/Users/pedroleal/Library/petals-platform-3.1.1"
NUMBER_PRODUCTS=1000

# Auto set up of environment variables
CXF_DIR_BIN=ENV['CXF_HOME']+"/bin"
raise "Please set CXF_HOME environment variable." if CXF_DIR_BIN.nil?
ROOT_DIR=(`pwd`).chomp

