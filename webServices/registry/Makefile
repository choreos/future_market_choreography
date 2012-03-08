registry: FORCE
	mvn clean install

deploy:
	cp -f target/registry.war $(CATALINA_HOME)/webapps/
	@echo "Sleeping for 10 sec while register is deployed"
	sleep 10

undeploy:
	rm -rf $(CATALINA_HOME)/webapps/registry*

FORCE:
