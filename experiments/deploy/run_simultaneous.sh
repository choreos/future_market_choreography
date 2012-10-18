#!/bin/bash

while [ true ]; do
  for arch in orch chor; do
    for portals in {1..4}; do
      make -f Makefile.opencirrus18.tomcat restart PORTALS=$portals
      sleep 10
      java -jar lg.jar simultaneous $arch 50 1000 50 900000
    done
  done
done
