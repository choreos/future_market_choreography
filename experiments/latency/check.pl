#!/usr/bin/perl

use strict;

my ($arch, $portals, $clients, $cur_line, $lines, $execs) = ("none", 0, 0, 0, 0, 0);
my $warmup = 0;

while (<>) {
  $cur_line += 1;

  if (/^# (\w+),(\d+),(\d+),(\d+)/) {
    if ($clients != $lines and $arch ne "none") {
      print "$arch,$portals,$clients has $lines results. Current line: $cur_line.\n";
    }
    ($arch, $portals, $clients) = ($1, $2, $3);
    $lines = 0;
    $warmup = 0 if $warmup;
    $execs += 1;
  } elsif (/^# warmup$/) {
    $warmup = 1;
  } elsif (not /^#/ and not $warmup) {
    $lines += 1;
  }
}

print "Experiments: $execs\n";
