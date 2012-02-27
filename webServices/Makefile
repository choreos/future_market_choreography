all: supermarket1 supermarket2 supermarket3 shipper customer
	@echo "==============================================="
	@echo "=== If you want to deploy, run make deploy. ==="
	@echo "==============================================="

futuremarket: registry
	cd futureMarket && \
	mvn clean install

customer: pojo registry supermarket shipper futuremarket
	cd customer && \
	mvn clean install

shipper: registry pojo futuremarket
	cd shipper && \
	mvn clean install

registry: FORCE
	make -C registry

supermarket1: supermarket FORCE
	cd supermarket1 && \
	mvn clean package

supermarket2: supermarket FORCE
	cd supermarket2 && \
	mvn clean package

supermarket3: supermarket FORCE
	cd supermarket3 && \
	mvn clean package

supermarket: pojo registry futuremarket shipper
	cd supermarket && \
	mvn clean install

pojo:
	cd pojos && \
	mvn clean install

undeploy:
	make -C registry undeploy
	rm -rf $(CATALINA_HOME)/webapps/shipper* \
	rm -rf $(CATALINA_HOME)/webapps/supermarket* \
	rm -rf $(CATALINA_HOME)/webapps/customer*

FORCE:

deploy:
	make -C registry deploy
	cp -f shipper/target/shipper.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 10 sec while shipper is deployed"
	sleep 10
	cp -f supermarket1/target/supermarket1.war $(CATALINA_HOME)/webapps/
	cp -f supermarket2/target/supermarket2.war $(CATALINA_HOME)/webapps/
	cp -f supermarket3/target/supermarket3.war $(CATALINA_HOME)/webapps/
	cp -f customer/target/customer.war $(CATALINA_HOME)/webapps/
