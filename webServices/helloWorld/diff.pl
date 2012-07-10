#!/usr/bin/perl

while(<>) {
  print ''.($2 - $1)."\n" if (/^(\d+) (\d+)$/);
}
