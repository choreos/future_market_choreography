customer: FORCE
	mvn clean install

deploy:
	cp -f target/customer.war $(CATALINA_HOME)/webapps/

undeploy:
	rm -rf $(CATALINA_HOME)/webapps/customer*

FORCE:
