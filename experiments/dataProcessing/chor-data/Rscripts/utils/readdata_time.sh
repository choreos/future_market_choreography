#!/bin/bash

echo -n "data <- list("
for f in `ls data/$1/time/*/$2`; do echo -n "read.table(\""$f"\",head=FALSE,sep=\" \"), ";  done
echo -e "\x8\x8)"
echo 
t="c("
m="c("
s="c("
i=1
for d in `ls data/$1/time`; do t="$t$d,"; m="${m}mean(data[[$i]][2]),"; s="${s}sd(data[[$i]][2]),"; i=`expr $i + 1`; done
echo -e "$t\x8)"
echo
echo -e "$m\x8)"
echo
echo -e "$s\x8)"

