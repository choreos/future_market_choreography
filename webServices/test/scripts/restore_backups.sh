#!/bin/bash

BASEDIR=`pwd`
SMSDIR=${BASEDIR}/../../supermarkets
BACKUPDIR=${BASEDIR}/backup
FUTUREDIR=${BASEDIR}/../../futureMarket

#make backup
#supermarkets dir
echo "restore supermarkets"
if [ -d "${SMSDIR}" ]
then
    rm -rf ${SMSDIR} \
    cp -r ${BACKUPDIR}/supermarkets ${SMSDIR}
fi

echo "restore futureMarket config"
#futuremarket config backup
if [ -f "${FUTUREDIR}/src/main/resources/config.properties" ]
then
    cp ${BACKUPDIR}/config.properties.backup ${FUTUREDIR}/src/main/resources/config.properties 
fi


