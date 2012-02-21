all: sm1 sm2 sm3 registry shipper customer

customer: pojo wsclient
	cd CustomerWS && \
	mvn clean && \
	mvn package && \
	cp -f target/customer.war $(CATALINA_HOME)/webapps/

shipper: registry pojo wsclient
	cd Shipper && \
	mvn clean && \
	mvn package && \
	cp -f target/shipper.war $(CATALINA_HOME)/webapps/

registry:
	cd Registry && \
	make

sm1: sm wsclient FORCE
	cd SM1 && \
	mvn clean && \
	mvn package && \
	cp -f target/supermarket1.war $(CATALINA_HOME)/webapps/

sm2: sm wsclient FORCE
	cd SM2 && \
	mvn clean && \
	mvn package && \
	cp -f target/supermarket2.war $(CATALINA_HOME)/webapps/

sm3: sm wsclient FORCE
	cd SM3 && \
	mvn clean && \
	mvn package && \
	cp -f target/supermarket3.war $(CATALINA_HOME)/webapps/

sm: registry pojo wsclient
	cd SM && \
	mvn install

pojo:
	cd Pojos && \
	mvn clean && \
	mvn install

wsclient:
	cd WSClientWR && \
	mvn clean && \
	mvn install

clean:
	rm -rf $(CATALINA_HOME)/webapps/smregistry* \
	rm -rf $(CATALINA_HOME)/webapps/smshipper* \
	rm -rf $(CATALINA_HOME)/webapps/smsupermarket* \
	rm -rf $(CATALINA_HOME)/webapps/smcustomer*

FORCE:

deploy:
	scp Registry/target/registry.war choreos@198.55.33.0:apache-tomcat-7.0.25/webapps/
	@echo "Sleeping while registry is deployed"
	sleep 10
	scp Shipper/target/shipper.war choreos@198.55.33.3:apache-tomcat-7.0.25/webapps/
	scp SM1/target/supermarket1.war choreos@198.55.33.4:apache-tomcat-7.0.25/webapps/
	scp SM2/target/supermarket2.war choreos@198.55.33.5:apache-tomcat-7.0.25/webapps/
	scp SM3/target/supermarket3.war choreos@198.55.33.6:apache-tomcat-7.0.25/webapps/
	scp CustomerWS/target/customer.war choreos@198.55.33.7:apache-tomcat-7.0.25/webapps/
