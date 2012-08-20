#!/bin/bash

BASEDIR=`pwd`
PORTALDIR=${BASEDIR}/../portals
SMSDIR=${BASEDIR}/../supermarkets
SHIPPERSDIR=${BASEDIR}/../shippers
BACKUPDIR=${BASEDIR}/backup
FUTUREDIR=${BASEDIR}/../futureMarket

BACKUP=$1

TESTCASE=$2

print_help () 
{
    echo "USAGE:"
    echo "    $0 <BACKUP: y/n> <TEST CASE>"
    exit 1
}

# check variable
[ -z $BACKUP ]   && print_help
[ -z $TESTCASE ] && print_help

# make backup
if [ $BACKUP == "y" ]
then  
    echo "backup portals"
    if [ -d "${PORTALDIR}" ]; then
      find $PORTALDIR -maxdepth 1 -mindepth 1 -not -name \*.sh -exec cp -r {} $BACKUPDIR/portals/ \;
    fi

    echo "backup supermarkets"
    if [ -d "${SMSDIR}" ]; then
      find $SMSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh -exec cp -r {} $BACKUPDIR/supermarkets/ \;
    fi

    echo "backup shippers"
    if [ -d "${SHIPPERSDIR}" ]; then
      find $SHIPPERSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh -exec cp -r {} $BACKUPDIR/shippers/ \;
    fi

    echo "backup futureMarket config"
    if [ -f "${FUTUREDIR}/src/main/resources/config.properties" ]; then
      cp ${FUTUREDIR}/src/main/resources/config.properties ${BACKUPDIR}/config.properties.backup
    fi
fi

echo "applying test configurations"
case $TESTCASE in
[1-4])
    # Remove old configs
    find $PORTALDIR -maxdepth 1 -mindepth 1 -not -name \*.sh | xargs rm -rf
    # Create configs
    cp -r ${BASEDIR}/$TESTCASE/portals/* ${PORTALDIR}

    find $SMSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh | xargs rm -rf
    cp -r ${BASEDIR}/$TESTCASE/supermarkets/* ${SMSDIR}

    find $SHIPPERSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh | xargs rm -rf
    cp -r ${BASEDIR}/$TESTCASE/shippers/* ${SHIPPERSDIR}

    cp ${BASEDIR}/$TESTCASE/config.properties ${FUTUREDIR}/src/main/resources/
;;
*)
    echo "Please insert a valid test case."
    print_help
;;
esac
