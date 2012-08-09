all: portal orchestrators
	@echo "==============================================="
	@echo "=== If you want to deploy, run make deploy. ==="
	@echo "==============================================="

futuremarket: registry
	cd futureMarket && \
	mvn install

shipper: futuremarket
	cd shipper && \
	mvn install && \
	cd ../shippers && \
	./generateShippers.sh

bank: futuremarket
	cd bank && \
	mvn install

registry: FORCE
	make -C registry

supermarket: shipper bank
	cd supermarket && \
	mvn install && \
	cd ../supermarkets && \
	./generateSupermarkets.sh

portal:
	cd portal && \
	mvn install

orchestrators: portal
	cd ../orchestrators && \
	./generateOrchestrators.sh

undeploy:
	make -C registry undeploy
	rm -rf $(CATALINA_HOME)/webapps/{s,S}hipper* \
	rm -rf $(CATALINA_HOME)/webapps/{b,B}ank* \
	rm -rf $(CATALINA_HOME)/webapps/{s,S}upermarket* \
	rm -rf $(CATALINA_HOME)/webapps/{m,M}anufacturer* \
	rm -rf $(CATALINA_HOME)/webapps/{s,S}upplier* \

FORCE:

deploy:
	make -C registry deploy
	cp -f shippers/*/*.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while shippers are deployed"
	sleep 7
	cp -f bank/target/bank.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while bank is deployed"
	sleep 7
	cp -f orchestrators/*/*.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 7 sec while orchestrators are deployed"
	sleep 7
	cp -f supermarkets/*/*.war $(CATALINA_HOME)/webapps/
