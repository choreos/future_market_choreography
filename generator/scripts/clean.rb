require "./config"

def remove_service_assemblies_of_petals
  `rm #{PETALS_PLATAFORM_DIR}/installed/sa-*`
  `rm #{PETALS_PLATAFORM_DIR}/installed/SA-*`
end

def clean_workspace_dir
  `rm #{ROOT_DIR}/workspace/SM*.wsdl`
  `rm #{ROOT_DIR}/workspace/build.xml`
  `rm -rf #{ROOT_DIR}/workspace/build`
  `rm -rf #{ROOT_DIR}/workspace/eu`
  `rm #{ROOT_DIR}/petals/bpel/*`
  `rm #{ROOT_DIR}/petals/soap-provide/*`
  `rm #{ROOT_DIR}/petals/soap-consume/*`
end

remove_service_assemblies_of_petals
clean_workspace_dir
