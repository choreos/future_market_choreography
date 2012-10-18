#!/bin/bash

while [ true ]; do
  for arch in orch chor; do
    make -f Makefile.opencirrus18.tomcat restart PORTALS=1
    java -jar lg.jar frequency $arch 500 8500 500 5000 0.95 2000
  done
done
