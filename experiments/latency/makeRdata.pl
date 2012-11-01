#!/usr/bin/perl

# input:
# # orch,1,1,1348962453617
# 123
# # chor,1,2,1348962653617
# 42
# 50
# # started ...
#
# output:
# Arch,Portals,Clients,Execution,Time,Timestamp
# orch,1,1,1,123,1348962453617
# chor,1,2,2,42,1348962653617
# chor,1,2,2,50,1348962653617
# ...

use strict;

my ($arch, $portals, $clients, $execution, $timestamp) = ('none', 0, 0, 0, 0);
my $warmup = 0;

print "Arch,Portals,Clients,Execution,Time,Timestamp\n";

while (<>) {
  if (/^# (\w+),(\d+),(\d+),(\d+)$/) {
    ($arch, $portals, $clients, $timestamp) = ($1, $2, $3, $4);
    $execution += 1;
    $warmup = 0;
  } elsif (/^# warmup/) {
    $warmup = 1;
  } elsif (/^(\d+)$/ and not $warmup) {
    print "$arch,$portals,$clients,$execution,$1,$timestamp\n";
  }
}
