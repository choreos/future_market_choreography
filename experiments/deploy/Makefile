REGISTRY_HOST=opencirrus-07901.hpl.hp.com
SHIPPER_HOST=opencirrus-07904.hpl.hp.com
SM1_HOST=opencirrus-07905.hpl.hp.com
SM2_HOST=opencirrus-07906.hpl.hp.com
SM3_HOST=opencirrus-07907.hpl.hp.com
CUSTOMER_HOST=opencirrus-07908.hpl.hp.com
WEBAPPS_DIR=/home/choreos/apache-tomcat-7.0.25/webapps

registry:
	scp ../../webServices/registry/target/registry.war choreos@$(REGISTRY_HOST):/tmp/
	ssh $(REGISTRY_HOST) "mv /tmp/registry.war" $(WEBAPPS_DIR)/
shipper:
	scp ../../webServices/shipper/target/shipper.war choreos@$(SHIPPER_HOST):/tmp/
	ssh $(SHIPPER_HOST) "mv /tmp/shipper.war" $(WEBAPPS_DIR)/
supermarket1:
	scp ../../webServices/supermarket1/target/supermarket1.war choreos@$(SM1_HOST):/tmp/
	ssh $(SM1_HOST) "mv /tmp/supermarket1.war" $(WEBAPPS_DIR)/
supermarket2:
	scp ../../webServices/supermarket2/target/supermarket2.war choreos@$(SM2_HOST):/tmp/
	ssh $(SM2_HOST) "mv /tmp/supermarket2.war" $(WEBAPPS_DIR)/
supermarket3:
	scp ../../webServices/supermarket3/target/supermarket3.war choreos@$(SM3_HOST):/tmp/
	ssh $(SM3_HOST) "mv /tmp/supermarket3.war" $(WEBAPPS_DIR)/
customer:
	scp ../../webServices/customer/target/customer.war choreos@$(CUSTOMER_HOST):/tmp/
	ssh $(CUSTOMER_HOST) "mv /tmp/customer.war" $(WEBAPPS_DIR)/

all-but-registry: shipper supermarket1 supermarket2 supermarket3 customer
