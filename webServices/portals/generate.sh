#!/bin/bash

PORTALDIR=../../portal

echo "Generating portals"

for portal in `ls -d */`; do \
	i=`basename ${portal}`; \
	cd $i; \
	cp *.properties ${PORTALDIR}/src/main/resources/; \
	cd $PORTALDIR/../; \
	mvn -pl portal package; \
	cd -; \
	cp ${PORTALDIR}/target/portal.war ./`sed '/^\#/d' portal.properties | grep '^name'  | tail -n 1 | cut -d "=" -f2-`.war; \
	cd ..; \
done;

