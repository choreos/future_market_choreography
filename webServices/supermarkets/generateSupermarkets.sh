#!/bin/bash

SMDIR=../../supermarket

for sm in `ls --color=never -d */`; do \
	i=`basename ${sm}`; \
	cd $i; \
	cp *.properties ${SMDIR}/src/main/resources/; \
	cd $SMDIR; \
	mvn package; \
	cd -; \
	cp ${SMDIR}/target/supermarket.war ./`sed '/^\#/d' supermarket.properties | grep 'this.name'  | tail -n 1 | cut -d "=" -f2-`.war; \
	cd ..; \
done;

