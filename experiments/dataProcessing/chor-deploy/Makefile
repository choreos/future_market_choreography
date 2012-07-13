NODE1=opencirrus-07904.hpl.hp.com
NODE2=opencirrus-07905.hpl.hp.com
NODE3=opencirrus-07906.hpl.hp.com
NODE4=opencirrus-07907.hpl.hp.com
NODE5=opencirrus-07908.hpl.hp.com
NODE6=opencirrus-07912.hpl.hp.com
NODE7=opencirrus-07913.hpl.hp.com
NODE8=opencirrus-07914.hpl.hp.com
NODE9=opencirrus-07915.hpl.hp.com
ALL_HOSTS=07904 07905 07906 07908 07912 07913 07914 07915

BANK_HOST=$(NODE1)
REGISTRY_HOST=$(NODE1)

SM1_HOST=$(NODE2)
SM2_HOST=$(NODE2)

BROKER_HOST=$(NODE3)

SM3_HOST=$(NODE5)
SM4_HOST=$(NODE5)

SM5_HOST=$(NODE6)
SUPPLIER1_HOST=$(NODE6)

SUPPLIER2_HOST=$(NODE7)
SUPPLIER3_HOST=$(NODE7)

SHIPPER1_HOST=$(NODE8)
MANUFACT_HOST=$(NODE8)

SHIPPER2_HOST=$(NODE9)

CATALINA_HOME=/home/choreos/apache-tomcat-7.0.25
WEBAPPS_DIR=$(CATALINA_HOME)/webapps

TOMCAT_FILE=/tmp/apache-tomcat-7.0.25.baile.tar.bz2

services: bank broker manufact registry shipper1 shipper2 sm1 sm2 sm3 sm4 sm5 supplier1 supplier2 supplier3

checkWsDir:
	@if ! test -d "$(WS_DIR)"; then \
		echo 'You must define where are the WSs. E.g.:'; \
		echo 'WS_DIR=/repos/futureMarket/webServices make'; \
    echo; \
	fi

shippers: shipper1 shipper2
	@echo "Waiting for shippers deployment..."
	sleep 20

bank: registry
	scp $(WS_DIR)/bank/target/bank.war choreos@$(REGISTRY_HOST):/tmp/
	ssh $(REGISTRY_HOST) "mv /tmp/bank.war $(WEBAPPS_DIR)/"

broker: registry
	scp $(WS_DIR)/customer/target/customer.war choreos@$(BROKER_HOST):/tmp/
	ssh $(BROKER_HOST) "mv /tmp/customer.war $(WEBAPPS_DIR)/"

manufact: registry shippers
	scp $(WS_DIR)/supermarkets/manufacturer/Manufacturer.war choreos@$(MANUFACT_HOST):/tmp/
	ssh $(MANUFACT_HOST) "mv /tmp/Manufacturer.war $(WEBAPPS_DIR)/"

registry: checkWsDir
	scp $(WS_DIR)/registry/target/registry.war choreos@$(REGISTRY_HOST):/tmp/
	ssh $(REGISTRY_HOST) "mv /tmp/registry.war $(WEBAPPS_DIR)/"
	@echo "Waiting for registry deployment..."
	sleep 20

shipper1: registry
	scp $(WS_DIR)/shippers/shipper1/Shipper.war choreos@$(SHIPPER1_HOST):/tmp/
	ssh $(SHIPPER1_HOST) "mv /tmp/Shipper.war $(WEBAPPS_DIR)/"

shipper2: registry
	scp $(WS_DIR)/shippers/shipper2/Shipper2.war choreos@$(SHIPPER2_HOST):/tmp/
	ssh $(SHIPPER2_HOST) "mv /tmp/Shipper2.war $(WEBAPPS_DIR)/"

sm1: registry shippers
	scp $(WS_DIR)/supermarkets/sm1/Supermarket1.war choreos@$(SM1_HOST):/tmp/
	ssh $(SM1_HOST) "mv /tmp/Supermarket1.war $(WEBAPPS_DIR)/"

sm2: registry shippers
	scp $(WS_DIR)/supermarkets/sm2/Supermarket2.war choreos@$(SM2_HOST):/tmp/
	ssh $(SM2_HOST) "mv /tmp/Supermarket2.war $(WEBAPPS_DIR)/"

sm3: registry shippers
	scp $(WS_DIR)/supermarkets/sm3/Supermarket3.war choreos@$(SM3_HOST):/tmp/
	ssh $(SM3_HOST) "mv /tmp/Supermarket3.war $(WEBAPPS_DIR)/"

sm4: registry shippers
	scp $(WS_DIR)/supermarkets/sm4/Supermarket4.war choreos@$(SM4_HOST):/tmp/
	ssh $(SM4_HOST) "mv /tmp/Supermarket4.war $(WEBAPPS_DIR)/"

sm5: registry shippers
	scp $(WS_DIR)/supermarkets/sm5/Supermarket5.war choreos@$(SM5_HOST):/tmp/
	ssh $(SM5_HOST) "mv /tmp/Supermarket5.war $(WEBAPPS_DIR)/"

supplier1: registry shippers
	scp $(WS_DIR)/supermarkets/supplier1/Supplier1.war choreos@$(SUPPLIER1_HOST):/tmp/
	ssh $(SUPPLIER1_HOST) "mv /tmp/Supplier1.war $(WEBAPPS_DIR)/"

supplier2: registry shippers
	scp $(WS_DIR)/supermarkets/supplier2/Supplier2.war choreos@$(SUPPLIER2_HOST):/tmp/
	ssh $(SUPPLIER2_HOST) "mv /tmp/Supplier2.war $(WEBAPPS_DIR)/"

supplier3: registry shippers
	scp $(WS_DIR)/supermarkets/supplier3/Supplier3.war choreos@$(SUPPLIER3_HOST):/tmp/
	ssh $(SUPPLIER3_HOST) "mv /tmp/Supplier3.war $(WEBAPPS_DIR)/"

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
