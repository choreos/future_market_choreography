#!/bin/bash

DAY=01
START=00:00:00
END=23:59:59

# Received and Transmitted kB/sec
sar -n DEV -f /var/log/sysstat/sa$DAY -s $START -e $END | grep eth0 | grep -v ^Average | tr -s ' ' | cut -f 1,6,7 -d ' '


