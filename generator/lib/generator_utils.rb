require 'fileutils'
include FileUtils

module GeneratorUtils
  def open_file_and_write file_name, content
	    file = File.new(file_name, "w")
	    file.puts content
	    file.close
	end
	
	def compact_petals_files zip_file, *args
	  zip_j zip_file, args unless args.empty?
	  zip zip_file, "META-INF"
	end
	
	def zip zip_file, *args
	  list = args.join " "
	  `zip -qr0m #{zip_file} #{list} 2>/dev/null`
	end

	def zip_j zip_file, *args
	  list = args.join " "
	  `zip -qr0mj #{zip_file} #{list} 2>/dev/null`
	end  
	
	def substitute(template, dest)
    erb = ERB.new(File.read(template))
    File.open(dest, "w+") do |f|
      f.write(erb.result(binding))
    end
  end
  
  def create_dir(dir)
    mkdir_p(dir)
  end
  
end