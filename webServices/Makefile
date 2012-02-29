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
	rm -rf $(CATALINA_HOME)/webapps/shipper* \
	rm -rf $(CATALINA_HOME)/webapps/bank* \
	rm -rf $(CATALINA_HOME)/webapps/supermarket* \
	rm -rf $(CATALINA_HOME)/webapps/customer*

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
