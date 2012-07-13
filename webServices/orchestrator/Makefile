orchestrator: FORCE
	mvn clean install

deploy:
	cp -f target/orchestrator.war $(CATALINA_HOME)/webapps/

undeploy:
	rm -rf $(CATALINA_HOME)/webapps/orchestrator*

FORCE:
