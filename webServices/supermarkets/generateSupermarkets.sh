#!/bin/bash

SMDIR=../../supermarket

for sm in `ls --color=never -d */`; do \
	i=`basename ${sm}`; \
	cd $i; \
	cp *.xml ${SMDIR}/src/main/webapp/WEB-INF/; \
	cp *.properties ${SMDIR}/src/main/resources/; \
	cd $SMDIR; \
	mvn package; \
	cd -; \
	cp ${SMDIR}/target/supermarket.war ./supermarket${i}.war; \
	cp *.war ${CATALINA_HOME}/webapps/; \
	cd ..; \
done;

