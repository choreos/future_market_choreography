#!/bin/bash

>load-generator.log
>matrix.log

matrix=""
index=0
for threads in 001 002 003 004 005 006 007 008 009 010 011 012 013 014 015 016 017 018 019 020 050 100 150 200 250 300 350 400 450 500; do
	echo -n "t$threads=( START_TSTAMP=$(date +%s)" >>matrix.log
	make -j -s stats_begin
	java -jar load-generator-1.0-jar-with-dependencies.jar $threads 30 >> load-generator.log 2>&1
	make -j -s stats_end
	echo " END_TSTAMP=$(date +%s) THREADS=$threads )" >>matrix.log

	mv shipment.log lowest_price.log purchase.log *_stat_*.out data/$1/time/$threads/
	
	matrix="$matrix\t[$index]=\"t$threads[*]\"\n"
	index=`expr $index + 1`
	sleep 3
done

echo -e "matrix=( \n$matrix)" >> matrix.log
