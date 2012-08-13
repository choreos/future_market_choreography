#!/bin/bash

find . -type d | grep -v '^\.$' | xargs rm -rf
