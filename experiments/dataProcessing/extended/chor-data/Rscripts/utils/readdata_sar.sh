#!/bin/bash

for i in $(seq 1 9); do
	echo -n "$1_node${i}_data <- list("
	for f in `ls data/$1/sar/node$i/*/$2_sar.out`; do 
		echo -n "read.table(\""$f"\",head=FALSE,sep=\" \"), ";
	done
	echo -e "\x8\x8)"
	echo;
done
echo
echo -n "name <- c("
for i in $(seq 1 9); do
	echo -n "\"png/xtended_mean_$1_sar_$2_node${i}.png\", ";
done
echo -e "\x8\x8)"
