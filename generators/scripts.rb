require 'fileutils'
include FileUtils

def zip zip_file, *args
  list = args.join " "
  `zip -qr0m #{zip_file} #{list} 2>/dev/null`
end

def zip_j zip_file, *args
  list = args.join " "
  `zip -qr0mj #{zip_file} #{list} 2>/dev/null`
end

# path to final files
mkdir_p "final_files"

# zipping SOAP-SupermarketWS-provide
mkdir_p "META-INF/"
cp "files/SOAP-SupermarketWS-provide/SM2.wsdl",  "."
cp "files/SOAP-SupermarketWS-provide/SM2_xsd_1",  "."
cp "files/SOAP-SupermarketWS-provide/jbi.xml",  "META-INF/"

zip_j "su-SOAP-SupermarketWS-provide.zip", "SM2.wsdl"
zip_j "su-SOAP-SupermarketWS-provide.zip", "SM2_xsd_1"
zip "su-SOAP-SupermarketWS-provide.zip", "META-INF/"

mkdir_p "META-INF"
cp "files/SOAP-SupermarketWS-provide/jbi-sa.xml", "META-INF/jbi.xml"
zip_j "sa-SOAP-SupermarketWS-provide.zip", "su-SOAP-SupermarketWS-provide.zip"
zip "sa-SOAP-SupermarketWS-provide.zip", "META-INF/"

mv "sa-SOAP-SupermarketWS-provide.zip", "final_files/"


# zipping BPEL-supermarket-provide
mkdir_p "META-INF"
files = ["SM2.wsdl", "SM2_xsd_1", "smregistry.wsdl", "smregistry_xsd_1", "supermarket.bpel", 
         "supermarketArtifacts.wsdl", "supermarketDefinition.wsdl"]

files.each do |file|
  cp "files/BPEL-supermarket-provide/" + file,  "."
end
cp "files/BPEL-supermarket-provide/jbi.xml",  "META-INF/"

files.each do |file|
  zip_j "su-BPEL-supermarket-provide.zip", file
end
zip "su-BPEL-supermarket-provide.zip", "META-INF/"

mkdir_p "META-INF"
cp "files/BPEL-supermarket-provide/jbi-sa.xml", "META-INF/jbi.xml"
zip_j "sa-BPEL-supermarket-provide.zip", "su-BPEL-supermarket-provide.zip"
zip "sa-BPEL-supermarket-provide.zip", "META-INF/"

mv "sa-BPEL-supermarket-provide.zip", "final_files/"

#zipping SOAP-supermarket-consume
mkdir_p "META-INF"
cp "files/SOAP-supermarket-consume/jbi.xml",  "META-INF/"

zip "su-SOAP-supermarket-consume.zip", "META-INF/"

mkdir_p "META-INF"
cp "files/SOAP-supermarket-consume/jbi-sa.xml", "META-INF/jbi.xml"
zip_j "sa-SOAP-supermarket-consume.zip", "su-SOAP-supermarket-consume.zip"
zip "sa-SOAP-supermarket-consume.zip", "META-INF/"

mv "sa-SOAP-supermarket-consume.zip", "final_files/"



