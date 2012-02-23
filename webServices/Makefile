all: sm1 sm2 sm3 shipper customer

futuremarket: registry
	cd futureMarket && \
	mvn clean && \
	mvn install

customer: pojo registry sm shipper futuremarket
	cd customer && \
	mvn clean && \
	mvn package && \
	cp -f target/customer.war $(CATALINA_HOME)/webapps/

shipper: registry pojo futuremarket
	cd shipper && \
	mvn clean && \
	mvn install && \
	cp -f target/shipper.war $(CATALINA_HOME)/webapps/

registry:
	make -C registry

sm1: sm FORCE
	cd supermarket1 && \
	mvn clean && \
	mvn package && \
	cp -f target/supermarket1.war $(CATALINA_HOME)/webapps/

sm2: sm FORCE
	cd supermarket2 && \
	mvn clean && \
	mvn package && \
	cp -f target/supermarket2.war $(CATALINA_HOME)/webapps/

sm3: sm FORCE
	cd supermarket3 && \
	mvn clean && \
	mvn package && \
	cp -f target/supermarket3.war $(CATALINA_HOME)/webapps/

sm: pojo registry futuremarket shipper
	cd supermarket && \
	mvn clean && \
	mvn install

pojo:
	cd pojos && \
	mvn clean && \
	mvn install

undeploy:
	rm -rf $(CATALINA_HOME)/webapps/registry.war \
	rm -rf $(CATALINA_HOME)/webapps/shipper.war \
	rm -rf $(CATALINA_HOME)/webapps/supermarket*.war \
	rm -rf $(CATALINA_HOME)/webapps/customer.war

FORCE:

deploy-opencirrus:
	scp registry/target/registry.war choreos@198.55.33.0:apache-tomcat-7.0.25/webapps/
	@echo "Sleeping while registry is deployed"
	sleep 10
	scp shipper/target/shipper.war choreos@198.55.33.3:apache-tomcat-7.0.25/webapps/
	scp supermarket1/target/supermarket1.war choreos@198.55.33.4:apache-tomcat-7.0.25/webapps/
	scp supermarket2/target/supermarket2.war choreos@198.55.33.5:apache-tomcat-7.0.25/webapps/
	scp supermarket3/target/supermarket3.war choreos@198.55.33.6:apache-tomcat-7.0.25/webapps/
	scp customer/target/customer.war choreos@198.55.33.7:apache-tomcat-7.0.25/webapps/

clean:
	rm -rf $(CATALINA_HOME)/webapps/registry.war* \
	rm -rf $(CATALINA_HOME)/webapps/shipper.war* \
	rm -rf $(CATALINA_HOME)/webapps/supermarket?.war* \
	rm -rf $(CATALINA_HOME)/webapps/customer.war*
