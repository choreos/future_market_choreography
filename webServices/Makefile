all: registry bank portals supermarkets shippers
	@echo "==============================================="
	@echo "=== If you want to deploy, run make deploy. ==="
	@echo "==============================================="

futuremarket: registry
	mvn -pl futureMarket install

registry: FORCE
	mvn -pl registry install

shippers: futuremarket
	cd shippers && \
	./generate.sh

bank: futuremarket
	mvn -pl bank package

supermarkets: futuremarket
	cd supermarkets && \
	./generate.sh

portals: futuremarket
	cd portals && \
	./generate.sh

undeploy:
	make -C registry undeploy
	cd $(CATALINA_HOME)/webapps && \
	rm -rf shipper* bank* supermarket* manufacturer* supplier* portal*

deploy-registry:
	make -C registry deploy

deploy-others:
	cp -f shippers/*/*.war $(CATALINA_HOME)/webapps/
	cp -f bank/target/bank.war $(CATALINA_HOME)/webapps/
	cp -f supermarkets/*/*.war $(CATALINA_HOME)/webapps/
	cp -f portals/*/*.war $(CATALINA_HOME)/webapps/

clean-all: clean-mvn clean-testCase clean-repo

clean-mvn:
	mvn -pl futureMarket,registry,bank,shipper,supermarket,portal clean

clean-testCase:
	cd shippers && ./clean.sh
	cd supermarkets && ./clean.sh
	cd portals && ./clean.sh

clean-repo:
	rm -rf ~/.m2/repository/br/usp/ime/futuremarket/

FORCE:
