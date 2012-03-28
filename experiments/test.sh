#!/bin/bash

matrix=""
index=0
for threads in 050 100 150 200 250 300 350 400 450 500 550 600 650 700 750 800; do
	echo -n "t$threads=( START_TSTAMP=$(date +%s)"
	java -jar target/load-generator-1.0-jar-with-dependencies.jar $threads 2500 > test.log 2>&1
	echo " END_TSTAMP=$(date +%s) THREADS=$threads )"
	mv shipment.log lowest_price.log purchase.log data/$1/time/$threads/
	matrix="$matrix\t[$index]=\"t$threads[*]\"\n"
	index=`expr $index + 1`
	sleep 3
done
echo
echo -e "matrix=( \n$matrix)"

