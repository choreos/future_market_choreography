#!/usr/bin/perl

my $type = "none";
my $freq = 0;

print "Arch,Freq,Time\n";

while (<>) {
  if (/^# (\w+) (\d+) reqs\/min/) {
    ($type, $freq) = ($1, $2);
  } elsif (/^(\d+)$/) {
    print "$type,$freq,$1\n";
  }
}
