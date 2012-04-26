#!/bin/bash

t1=( START_TSTAMP=1234567890 END_TSTAMP=1234567890 THREADS=1 )
t2=( START_TSTAMP=1234567890 END_TSTAMP=1234567890 THREADS=2 )
t3=( START_TSTAMP=1234567890 END_TSTAMP=1234567890 THREADS=3 )
t5=( START_TSTAMP=1234567890 END_TSTAMP=1234567890 THREADS=5 )
t8=( START_TSTAMP=1234567890 END_TSTAMP=1234567890 THREADS=8 )

matrix=( 
	[0]="t1[*]"
	[1]="t2[*]"
	[2]="t3[*]"
	[3]="t5[*]"
	[4]="t8[*]"
)

ROOT_DIR=$1

function createFiles() {
	
	local ${!1}
	DAY=$(date -d @$START_TSTAMP +%d)
	START=$(date -d @$START_TSTAMP +%H:%M:%S)
	END=$(date -d @$END_TSTAMP +%H:%M:%S)	
	DIR=$ROOT_DIR/$THREADS	
	if [ ! -d $DIR ] ; then mkdir $DIR; fi
	
	# Received and Transmitted kB/sec
	sar -n DEV -f /var/log/sysstat/sa$DAY -s $START -e $END | grep eth0 | grep -v ^Average | tr -s ' ' | cut -f 1,6,7 -d ' '> $DIR/net_sar.out

	# Idle CPU (from 0 to 100)
	sar -P ALL -f /var/log/sysstat/sa$DAY -s $START -e $END | grep ^[0-9] | grep -v all | grep -v idle | tr -s ' ' | cut -f 1,3,9 -d ' ' > $DIR/cpu_sar.out

	# Memory usage (used, buffer, cache)
	sar -r -f /var/log/sysstat/sa$DAY -s $START -e $END | grep ^[0-9] | grep -v  kbm | tr -s ' ' | cut -f 1,4,6,7 -d ' ' > $DIR/mem_sar.out
}

for i in ${matrix[*]}; do
	createFiles $i 
done
