Requirements
============

A deployed choreography or orchestration.
Files in Makefile's LOAD_NODE (directly in folder EXPERIMENT_DIR):
  - load-generator/target/load-generator-1.0-jar-with-dependencies.jar
  - load-generator/test.sh


Running the experiment
======================

Execute test.sh in LOAD_NODE


Collecting data
===============

$ make time-tar time-download sar-upload sar-run

Wait all nodes finish sar-run (there will be output info)

$ make sar-download


Organizing all data
===================

$ make data-extract data-compress

Data will be available in data.tar.b2


Cleaning
========

$ make all-clean

P.S.: it won't erase data.tar.bz2
