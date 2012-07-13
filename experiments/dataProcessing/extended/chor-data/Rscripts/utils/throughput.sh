#!/bin/bash

cat $1 | grep "POST /$2/$2 HTTP/1.1" |tr -d '['| cut -f 4 -d ' '|cut -f 2,3,4 -d ':'>throughput.log 

