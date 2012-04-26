REGISTRY_HOST=opencirrus-07904.hpl.hp.com
SHIPPER_HOST=opencirrus-07912.hpl.hp.com
SM1_HOST=opencirrus-07905.hpl.hp.com
SM2_HOST=opencirrus-07906.hpl.hp.com
SM3_HOST=opencirrus-07907.hpl.hp.com
CUSTOMER_HOST=opencirrus-07908.hpl.hp.com
ALL_HOSTS=07912 07904 07905 07906 07907 07908

CATALINA_HOME=/home/choreos/apache-tomcat-7.0.25
WEBAPPS_DIR=$(CATALINA_HOME)/webapps

TOMCAT_FILE=/tmp/apache-tomcat-7.0.25.baile.tar.bz2

services: registry shipper supermarket1 supermarket2 supermarket3 customer

registry:
	scp ../../webServices/registry/target/registry.war choreos@$(REGISTRY_HOST):/tmp/
	ssh $(REGISTRY_HOST) "mv /tmp/registry.war" $(WEBAPPS_DIR)/
	@echo "Waiting Registry deployment (10 sec)"
	sleep 10
shipper: registry
	scp ../../webServices/shipper/target/shipper.war choreos@$(SHIPPER_HOST):/tmp/
	ssh $(SHIPPER_HOST) "mv /tmp/shipper.war" $(WEBAPPS_DIR)/
	@echo "Waiting Shipper deployment (10 sec)"
	sleep 10
supermarket1: shipper registry
	scp ../../webServices/supermarket1/target/supermarket1.war choreos@$(SM1_HOST):/tmp/
	ssh $(SM1_HOST) "mv /tmp/supermarket1.war" $(WEBAPPS_DIR)/
supermarket2: shipper registry
	scp ../../webServices/supermarket2/target/supermarket2.war choreos@$(SM2_HOST):/tmp/
	ssh $(SM2_HOST) "mv /tmp/supermarket2.war" $(WEBAPPS_DIR)/
supermarket3: shipper registry
	scp ../../webServices/supermarket3/target/supermarket3.war choreos@$(SM3_HOST):/tmp/
	ssh $(SM3_HOST) "mv /tmp/supermarket3.war" $(WEBAPPS_DIR)/
customer: shipper registry
	scp ../../webServices/customer/target/customer.war choreos@$(CUSTOMER_HOST):/tmp/
	ssh $(CUSTOMER_HOST) "mv /tmp/customer.war" $(WEBAPPS_DIR)/

tomcat-start:
	for host in $(ALL_HOSTS); do \
    echo $$host; \
		ssh opencirrus-$$host.hpl.hp.com "$(CATALINA_HOME)/bin/startup.sh"; \
	done

tomcat-shutdown:
	-for host in $(ALL_HOSTS); do \
    echo $$host; \
		ssh opencirrus-$$host.hpl.hp.com "$(CATALINA_HOME)/bin/shutdown.sh && sleep 2 && killall java"; \
	done

tomcat-remove:
	for host in $(ALL_HOSTS); do \
    echo $$host; \
		ssh opencirrus-$$host.hpl.hp.com "rm -rf $(CATALINA_HOME)"; \
	done

tomcat-copy:
	scp $(TOMCAT_FILE) $(REGISTRY_HOST):/tmp/
	ssh $(REGISTRY_HOST) "cd && ./scp_to_all.sh $(TOMCAT_FILE)"; \

tomcat-install:
	for host in $(ALL_HOSTS); do \
    echo $$host; \
		ssh opencirrus-$$host.hpl.hp.com "cd && tar xjf $(TOMCAT_FILE)"; \
	done

tomcat-reinstall: tomcat-shutdown tomcat-remove tomcat-install tomcat-start
