require "./config"

def remove_service_assemblies_of_petals
  `rm #{PETALS_PLATAFORM_DIR}/installed/sa-SOAP-SM*.zip`
  `rm #{PETALS_PLATAFORM_DIR}/installed/sa-BPEL-SM*.zip`
end

def clean_workspace_dir
  `rm #{ROOT_DIR}/workspace/SM*.wsdl`
  `rm #{ROOT_DIR}/workspace/build.xml`
  `rm -rf #{ROOT_DIR}/workspace/build`
  `rm -rf #{ROOT_DIR}/workspace/eu`
end

remove_service_assemblies_of_petals
clean_workspace_dir
