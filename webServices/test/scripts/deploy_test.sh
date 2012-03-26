#!/bin/bash

BASEDIR=`pwd`
SMSDIR=${BASEDIR}/../../supermarkets
BACKUPDIR=${BASEDIR}/backup
FUTUREDIR=${BASEDIR}/../../futureMarket

#make backup
#supermarkets dir
echo "backup supermarkets"
if [ -d "${SMSDIR}" ]
then
    cp -r ${SMSDIR} ${BACKUPDIR}
fi

echo "backup futureMarket config"
#futuremarket config backup
if [ -f "${FUTUREDIR}/src/main/resources/config.properties" ]
then
    cp ${FUTUREDIR}/src/main/resources/config.properties ${BACKUPDIR}/config.properties.backup
fi

echo "copy supermarkets"
#copy supermarkets dir
if [ -d "${SMSDIR}" ]
then
    rm -rf ${SMSDIR}
fi
cp -r ${BASEDIR}/supermarkets ${SMSDIR}

echo "copy futureMarket config"
#copy futuremarket config
cp ${BASEDIR}/config.properties ${FUTUREDIR}/src/main/resources/

