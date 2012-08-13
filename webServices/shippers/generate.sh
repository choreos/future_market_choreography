#!/bin/bash

SHIPPERDIR=../../shipper

for shipper in `ls -d */`; do \
	i=`basename ${shipper}`; \
	cd $i; \
	cp *.properties ${SHIPPERDIR}/src/main/resources/; \
	cd $SHIPPERDIR/../; \
	mvn -pl shipper package; \
	cd -; \
	cp ${SHIPPERDIR}/target/shipper.war ./`sed '/^\#/d' shipper.properties | grep '^name'  | tail -n 1 | cut -d "=" -f2-`.war; \
	cd ..; \
done;

