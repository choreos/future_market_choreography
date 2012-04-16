all: orchestrator
	@echo "==============================================="
	@echo "=== If you want to deploy, run make deploy. ==="
	@echo "==============================================="

futuremarket: registry pojo
	cd futureMarket && \
	mvn clean install

orchestrator: supermarket 
	cd orchestrator && \
	mvn clean install

shipper: futuremarket
	cd shipper && \
	mvn clean install && \
	cd ../shippers && \
	./generateShippers.sh

bank: futuremarket
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
	rm -rf $(CATALINA_HOME)/webapps/{O,o}rchestrator*

FORCE:

deploy:
	make -C registry deploy
	cp -f shippers/*/*.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while shipper is deployed"
	sleep 7
	cp -f bank/target/bank.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while bank is deployed"
	sleep 7
	cp -f orchestrator/target/orchestrator.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while orchestrator is deployed"
	sleep 7
	cp -f supermarkets/*/*.war $(CATALINA_HOME)/webapps/
