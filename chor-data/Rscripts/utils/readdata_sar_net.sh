#!/bin/bash

echo -n "$2_data <- list("
for f in `ls data/$1/sar/$2/*/net_sar.out`; do echo -n "read.table(\""$f"\",head=FALSE,sep=\" \"), ";  done
echo -e "\x8\x8)"
echo 
t="Threads=c("
for d in `ls data/$1/`; do t="$t$d,"; done
echo -e "$t\x8)"

