class ProductLoader

  def initialize 
    @products = {
    'product1' => 'milk',
    'product2' => 'bread',
    'product3' => 'butter', 
    'product4' => 'juice',
    'product5' => 'wine',
    'product6' => 'beer', 
    'product7' => 'jam', 
    'product8' => 'ham',
    'product9' => 'sugar',
    'product10' => 'salt'}
  end

    def update_supermarket_stocks
      Dir.glob("3/supermarkets/*") do |project|
        update_config_properties project
      end
    end

    def update_config_properties(project)

      content = ""
      File.open("#{project}/supermarket.properties", "r").each_line { |line_content|
        content << line_content
      }

      new_file = File.open("#{project}/supermarket.properties", "w")

      new_content = replace_old_product_names(content)
 
      new_file.write new_content
      new_file.close
    end

    def replace_old_product_names old_content
        @products.keys.each do |key|
          old_content.gsub!(/#{Regexp.escape(key)}\D/, "#{@products[key]}.")
        end
        old_content
    end
end

loader = ProductLoader.new
loader.update_supermarket_stocks
