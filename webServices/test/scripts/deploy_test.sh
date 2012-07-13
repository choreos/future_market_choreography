#!/bin/bash

BASEDIR=`pwd`
ORCHSDIR=${BASEDIR}/../../orchestrators
SMSDIR=${BASEDIR}/../../supermarkets
SHIPPERSDIR=${BASEDIR}/../../shippers
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
    echo "backup orchestrators"
    if [ -d "${ORCHSDIR}" ]
	then
	    find $ORCHSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh -exec cp -r {} $BACKUPDIR/orchestrations/ \;
    fi
    #supermarkets dir
    echo "backup supermarkets"
    if [ -d "${SMSDIR}" ]
	then
	    find $SMSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh -exec cp -r {} $BACKUPDIR/supermarkets/ \;
    fi
    echo "backup shippers"
    if [ -d "${SHIPPERSDIR}" ]
	then
	    find $SHIPPERSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh -exec cp -r {} $BACKUPDIR/shippers/ \;
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
[1-4])
    #copy orchetsrations dir
    find $ORCHSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh | xargs rm -rf
    cp -r ${BASEDIR}/$TESTCASE/orchestrators/* ${ORCHSDIR}

    #copy supermarkets dir
    find $SMSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh | xargs rm -rf
    cp -r ${BASEDIR}/$TESTCASE/supermarkets/* ${SMSDIR}

    #copy shippers dir
        find $SHIPPERSDIR -maxdepth 1 -mindepth 1 -not -name \*.sh | xargs rm -rf
    cp -r ${BASEDIR}/$TESTCASE/shippers/* ${SHIPPERSDIR}

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
