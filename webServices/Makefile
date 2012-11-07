all: registry bank portals supermarkets shippers loadGenerator
	@echo "==============================================="
	@echo "=== If you want to deploy, run make deploy. ==="
	@echo "==============================================="

registry: FORCE
	mvn -pl registry install

parent:
	mvn -N install

futuremarket: registry parent
	mvn -pl futureMarket install

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

loadGenerator:
	mvn -pl helloWorld install
	mvn -pl ../experiments/loadGenerator package

undeploy:
	make -C registry undeploy
	cd $(CATALINA_HOME)/webapps && \
		rm -rf shipper* bank* supermarket* manufacturer* supplier* portal*

deploy:
	make -C registry deploy
	cp -f shippers/*/*.war $(CATALINA_HOME)/webapps/
	cp -f bank/target/bank.war $(CATALINA_HOME)/webapps/
	cp -f supermarkets/*/*.war $(CATALINA_HOME)/webapps/
	cp -f portals/*/*.war $(CATALINA_HOME)/webapps/

sendContext: contextSender/target/contextSender-1.0-jar-with-dependencies.jar
	@serviceNames="bank $$(grep ^name {portals,shippers,supermarkets}/*/*.properties | cut -f2 -d= | sort | xargs)"; \
	for serviceName in $$serviceNames; do \
		for arch in orchestration choreography; do \
			echo "$$serviceName - $$arch"; \
			java -jar contextSender/target/contextSender-1.0-jar-with-dependencies.jar http://127.0.0.1:8080/$$serviceName/$$arch registry http://127.0.0.1:8080/registry/endpoint; \
		done; \
	done

contextSender/target/contextSender-1.0-jar-with-dependencies.jar: contextSender
	mvn -pl contextSender package

clean-all: clean-mvn clean-testCase clean-m2

clean-mvn:
	mvn -pl futureMarket,registry,bank,shipper,supermarket,portal clean

clean-testCase:
	cd shippers && ./clean.sh
	cd supermarkets && ./clean.sh
	cd portals && ./clean.sh

clean-m2:
	rm -rf ~/.m2/repository/br/usp/ime/futuremarket/

FORCE:
