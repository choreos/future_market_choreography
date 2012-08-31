#!/usr/bin/perl

my $type = "none";
my $freq = 0;
my $lines = 0;

while (<>) {
  if ($_ =~ /^# (\w+) (\d+) reqs\/min/) {
    if ($freq != $lines and $type ne "none") {
      print "$type $freq reqs/min has $lines results\n";
    }
    $type = $1;
    $freq = $2;
    $lines = 0;
  } else {
    $lines += 1 if not /^#/;
  }
}
