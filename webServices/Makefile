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

FORCE:
