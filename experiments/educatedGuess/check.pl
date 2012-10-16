#!/usr/bin/perl

use strict;

my ($arch, $portals, $clients, $lines) = ("none", 0, 0, 0);
my $warmup = 0;

while (<>) {
  if (/^# (\w+),(\d+),(\d+),(\d+)/) {
    if ($clients != $lines and $arch ne "none") {
      print "$arch,$portals,$clients has $lines results\n";
    }
    ($arch, $portals, $clients) = ($1, $2, $3);
    $lines = 0;
    $warmup = 0;
  } elsif (/^# warmup$/) {
    $warmup = 1;
  } elsif (not /^#/ and not $warmup) {
    $lines += 1;
  }
}
