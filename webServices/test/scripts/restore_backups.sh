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
    find $SMSDIR -depth 1 -not -name \*.sh -exec rm -rf {} \;
    find $BACKUPDIR/supermarkets -depth 1 -not -name \*.sh -exec cp -r {} $SMSDIR \;
fi

echo "restore futureMarket config"
#futuremarket config backup
if [ -f "${FUTUREDIR}/src/main/resources/config.properties" ]
then
    cp ${BACKUPDIR}/config.properties.backup ${FUTUREDIR}/src/main/resources/config.properties 
fi


