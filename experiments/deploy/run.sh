#!/bin/bash

MAX_THREADS=750

echo 'Arch,Portals,Clients,Timestamp' >>graphs.log

while [ true ]; do
  for arch in orch chor; do
    for portals in {1..4}; do
      make -f Makefile.opencirrus18.tomcat restart PORTALS=$portals
      java -jar lg.jar $arch $MAX_THREADS
    done
  done
done
