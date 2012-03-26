all: customer
	@echo "==============================================="
	@echo "=== If you want to deploy, run make deploy. ==="
	@echo "==============================================="

futuremarket: registry
	cd futureMarket && \
	mvn clean install

customer: supermarket 
	cd customer && \
	mvn clean install

shipper: pojo futuremarket
	cd shipper && \
	mvn clean install

bank: pojo futuremarket
	cd bank && \
	mvn clean install

registry: FORCE
	make -C registry

supermarket: shipper bank
	cd supermarket && \
	mvn clean install && \
	cd ../supermarkets && \
	./generateSupermarkets.sh

pojo:
	cd pojos && \
	mvn clean install

undeploy:
	make -C registry undeploy
	rm -rf $(CATALINA_HOME)/webapps/{s,S}hipper* \
	rm -rf $(CATALINA_HOME)/webapps/{b,B}ank* \
	rm -rf $(CATALINA_HOME)/webapps/{s,S}upermarket* \
	rm -rf $(CATALINA_HOME)/webapps/{m,M}anufacturer* \
	rm -rf $(CATALINA_HOME)/webapps/{s,S}upplier* \
	rm -rf $(CATALINA_HOME)/webapps/{c,C}ustomer*

FORCE:

deploy:
	make -C registry deploy
	cp -f shipper/target/shipper.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while shipper is deployed"
	sleep 7
	cp -f bank/target/bank.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while bank is deployed"
	sleep 7
	cp -f supermarkets/*/*.war $(CATALINA_HOME)/webapps/
	cp -f customer/target/customer.war $(CATALINA_HOME)/webapps/
