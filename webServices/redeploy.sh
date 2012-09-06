#!/bin/bash
$CATALINA_HOME/bin/shutdown.sh
sleep 3
rm -f $CATALINA_HOME/logs/*
make undeploy
$CATALINA_HOME/bin/startup.sh
sleep 10
make deploy-registry
sleep 10
make deploy-others 
