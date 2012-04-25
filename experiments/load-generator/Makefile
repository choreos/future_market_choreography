SHIPPER_HOST=198.55.33.3
SM1_HOST=198.55.33.4
SM2_HOST=198.55.33.5
SM3_HOST=198.55.33.6
BROKER_HOST=198.55.33.7
REGISTRY_HOST=198.55.33.11

stats: registry_stat shipper_stat sm1_stat sm2_stat sm3_stat broker_stat

stats_begin: stats
	rename 's/_stat.out/_stat_begin.out/' *_stat.out

stats_end: stats
	rename 's/_stat.out/_stat_end.out/' *_stat.out

registry_stat:
	ssh $(REGISTRY_HOST) "cat /proc/stat" > $@.out

shipper_stat:
	ssh $(SHIPPER_HOST) "cat /proc/stat" > $@.out

sm1_stat:
	ssh $(SM1_HOST) "cat /proc/stat" > $@.out

sm2_stat:
	ssh $(SM2_HOST) "cat /proc/stat" > $@.out

sm3_stat:
	ssh $(SM3_HOST) "cat /proc/stat" > $@.out

broker_stat:
	ssh $(BROKER_HOST) "cat /proc/stat" > $@.out
