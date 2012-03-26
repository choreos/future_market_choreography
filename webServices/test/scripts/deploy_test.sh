#!/bin/bash

BASEDIR=`pwd`
SMSDIR=${BASEDIR}/../../supermarkets
BACKUPDIR=${BASEDIR}/backup
FUTUREDIR=${BASEDIR}/../../futureMarket

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
    #supermarkets dir
    echo "backup supermarkets"
    if [ -d "${SMSDIR}" ]
	then
	    find $SMSDIR -depth 1 -not -name \*.sh -exec cp -r {} $BACKUPDIR/supermarkets/ \; 
    fi

    echo "backup futureMarket config"
    #futuremarket config backup
    if [ -f "${FUTUREDIR}/src/main/resources/config.properties" ]
    then
	cp ${FUTUREDIR}/src/main/resources/config.properties ${BACKUPDIR}/config.properties.backup
    fi
fi

echo "applying test configurations"
case $TESTCASE in
[1-3])
    #copy supermarkets dir
    find $SMSDIR -depth 1 -not -name \*.sh | xargs rm -rf 
    cp -r ${BASEDIR}/$TESTCASE/supermarkets/* ${SMSDIR}

    #copy futuremarket config
    cp ${BASEDIR}/$TESTCASE/config.properties ${FUTUREDIR}/src/main/resources/
;;
*)
    echo "Please insert a valid test case."
    print_help
;;
esac

cd ../../
make
