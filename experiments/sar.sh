#!/bin/bash

DAY=01
START=00:00:00
END=23:59:59

# Received and Transmitted kB/sec
sar -n DEV -f /var/log/sysstat/sa$DAY -s $START -e $END | grep eth0 | grep -v ^Average | tr -s ' ' | cut -f 1,6,7 -d ' '

# Idle CPU (from 0 to 100)
sar -n DEV -f /var/log/sysstat/sa$DAY -s $START -e $END | grep ^[0-9] | grep -v idle | tr -s ' ' | cut -f 1,9 -d ' '
