#!/bin/bash

for thread in ../data/*/time/*; do
  if test -e $thread/lowest_price.log; then
    cat $thread/lowest_price.log $thread/purchase.log $thread/shipment.log | \
    cut -f-2 -d' ' | sort -n >$thread/all_operations.log
    echo $thread/all_operations.log
  fi
done
