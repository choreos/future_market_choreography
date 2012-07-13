#!/bin/bash

ORCHDIR=../../orchestrator

echo "Generating orchestrators"

for orch in `ls -d */`; do \
	i=`basename ${orch}`; \
	cd $i; \
	cp *.properties ${ORCHDIR}/src/main/resources/; \
	cd $ORCHDIR; \
	mvn package; \
	cd -; \
	cp ${ORCHDIR}/target/orchestrator.war ./`sed '/^\#/d' orchestrator.properties | grep 'this.name'  | tail -n 1 | cut -d "=" -f2-`.war; \
	cd ..; \
done;

