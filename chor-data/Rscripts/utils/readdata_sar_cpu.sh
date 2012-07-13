#!/bin/bash

echo -n "$2_data <- list("
for f in `ls data/$1/sar/$2/*/cpu_sar.out`; do echo -n "read.table(\""$f"\",head=FALSE,sep=\" \"), ";  done
echo -e "\x8\x8)"
echo 
t="Threads=c("
m="Mean_of_cpu_usage=c("
s="sigma=c("
i=1
for d in `ls data/$1/sar/$2`; do t="$t$d,"; m="${m}mean(100-data[[$i]][3]),"; s="${s}sd(100-data[[$i]][3]),"; i=`expr $i + 1`; done
echo -e "$t\x8)"
echo
echo -e "$m\x8)"
echo
echo -e "$s\x8)"

