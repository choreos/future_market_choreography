#!/bin/bash

START_TSTAMP=0123456789
END_TSTAMP=0123456789

DAY=$(date -d @$START_TSTAMP +%d)
START=$(date -d @$START_TSTAMP +%H:%M:%S)
END=$(date -d @$END_TSTAMP +%H:%M:%S)

# Received and Transmitted kB/sec
sar -n DEV -f /var/log/sysstat/sa$DAY -s $START -e $END | grep eth0 | grep -v ^Average | tr -s ' ' | cut -f 1,6,7 -d ' '> net_sar.out

# Idle CPU (from 0 to 100)
sar -f /var/log/sysstat/sa$DAY -s $START -e $END | grep ^[0-9] | grep -v idle | tr -s ' ' | cut -f 1,9 -d ' ' > cpu_sar.out

# Memory usage (used, buffer, cache)
sar -r -f /var/log/sysstat/sa$DAY -s $START -e $END | grep ^[0-9] | grep -v  kbm | tr -s ' ' | cut -f 1,3,5,6 -d ' ' > mem_sar.out
