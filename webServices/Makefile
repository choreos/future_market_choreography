all: sm1 sm2 sm3 registry shipper customer

customer: pojo
	cd CustomerWS && \
	mvn clean && \
	mvn package && \
	cp -f target/smcustomer.war $(CATALINA_HOME)/webapps/

shipper: registry pojo
	cd Shipper && \
	mvn clean && \
	mvn package && \
	cp -f target/smshipper.war $(CATALINA_HOME)/webapps/

registry:
	cd SMRegistry && \
	mvn clean && \
	mvn package && \
	cp -f target/smregistry.war $(CATALINA_HOME)/webapps/

sm1: sm FORCE
	cd SM1 && \
	mvn clean && \
	mvn package && \
	cp -f target/smsupermarket1.war $(CATALINA_HOME)/webapps/

sm2: sm FORCE
	cd SM2 && \
	mvn clean && \
	mvn package && \
	cp -f target/smsupermarket2.war $(CATALINA_HOME)/webapps/

sm3: sm FORCE
	cd SM3 && \
	mvn clean && \
	mvn package && \
	cp -f target/smsupermarket3.war $(CATALINA_HOME)/webapps/

sm: registry pojo
	cd SM && \
	mvn install

pojo:
	cd Pojos && \
	mvn clean && \
	mvn install

clean:
	rm -rf $(CATALINA_HOME)/webapps/smregistry* \
	rm -rf $(CATALINA_HOME)/webapps/smshipper* \
	rm -rf $(CATALINA_HOME)/webapps/smsupermarket* \
	rm -rf $(CATALINA_HOME)/webapps/smcustomer*

FORCE:

deploy:
	scp SMRegistry/target/smregistry.war choreos@198.55.33.0:apache-tomcat-7.0.25/webapps/
	@echo "Sleeping while registry is deployed"
	sleep 10
	scp Shipper/target/smshipper.war choreos@198.55.33.3:apache-tomcat-7.0.25/webapps/
	scp SM1/target/smsupermarket1.war choreos@198.55.33.4:apache-tomcat-7.0.25/webapps/
	scp SM2/target/smsupermarket2.war choreos@198.55.33.5:apache-tomcat-7.0.25/webapps/
	scp SM3/target/smsupermarket3.war choreos@198.55.33.6:apache-tomcat-7.0.25/webapps/
	scp CustomerWS/target/smcustomer.war choreos@198.55.33.7:apache-tomcat-7.0.25/webapps/
