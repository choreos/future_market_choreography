all: registry

registry:
	mvn clean && \
	mvn package && \
	cp -f target/registry.war $(CATALINA_HOME)/webapps/

clean:
	rm -rf $(CATALINA_HOME)/webapps/registry* \

FORCE:

