#!/usr/bin/perl

use strict;

my ($req_start, $req_end, $res_start, $res_end);
my ($reqs, $line);
my ($req_speed, $res_speed);
my $diff;
my ($max_diff, $min_diff) = (-999999, 999999);

while (<>) {
  if (/^# (\d+)/) {
    $reqs = $1;
    $line = 1;
    next;
  }

  if ($line == 1) {
    /(\d+) (\d+)/ or die "Could not parse times of line '$_'\n";
    $req_start = $1;
    $res_start = $2;
  } elsif ($line == $reqs) {
    /(\d+) (\d+)/ or die "Could not parse times of line '$_'\n";
    $req_end = $1;
    $res_end = $2;

    $req_speed = 60*1000*$reqs/($req_end - $req_start);
    $res_speed = 60*1000*$reqs/($res_end - $res_start);
    $diff = $res_speed - $req_speed;
    $max_diff = $diff if $diff gt $max_diff;
    $min_diff = $diff if $diff lt $min_diff;

    print "$reqs: $req_speed $res_speed ($diff)\n";
  }

  $line++;
}

print "Max (res - req speed): $max_diff\n";
print "Min (res - req speed): $min_diff\n";
