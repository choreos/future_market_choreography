REGISTRY_HOST=opencirrus-07912.hpl.hp.com
SHIPPER_HOST=opencirrus-07904.hpl.hp.com
SM1_HOST=opencirrus-07905.hpl.hp.com
SM2_HOST=opencirrus-07906.hpl.hp.com
SM3_HOST=opencirrus-07907.hpl.hp.com
BROKER_HOST=opencirrus-07908.hpl.hp.com
LOAD_HOST=opencirrus-07915.hpl.hp.com
ALL_HOSTS=07912 07904 07905 07906 07907 07908

SAR_DATA_DIR=~/sar
EXPERIMENT_DIR=~/experiments

all-tar: time-tar sar-tar

time-tar:
	ssh $(LOAD_HOST) "cd $(EXPERIMENT_DIR) && tar cjf chor-time.tar.bz2 data/chor/time/*"

sar-tar:
	for host in $(ALL_HOSTS); do \
    	echo $$host; \
		ssh opencirrus-$$host.hpl.hp.com "cd $(SAR_DATA_DIR)/chor && tar cjf sar.tar.bz2 *"; \
	done

all-download: time-download sar-download

time-download:
	scp $(LOAD_HOST):$(EXPERIMENT_DIR)/chor-time.tar.bz2 .

sar-download:
	scp $(REGISTRY_HOST):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-registry.tar.bz2
	scp $(SM1_HOST):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-sm1.tar.bz2
	scp $(SM2_HOST):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-sm2.tar.bz2
	scp $(SM3_HOST):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-sm3.tar.bz2
	scp $(BROKER_HOST):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-broker.tar.bz2
	scp $(SHIPPER_HOST):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-shipper.tar.bz2

data-extract:
	tar xjf chor-time.tar.bz2
	mkdir data/chor/sar
	for i in registry broker shipper sm1 sm2 sm3; do \
		mkdir data/chor/sar/$$i; \
		cd data/chor/sar/$$i; \
		tar xjf ../../../../chor-sar-$$i.tar.bz2; \
		cd -; \
	done

sar-upload:
	scp sar.sh opencirrus-07901.hpl.hp.com:/tmp 
	ssh opencirrus-07901.hpl.hp.com "~/scp_to_all.sh /tmp/sar.sh" 
	for host in $(ALL_HOSTS); do \
    	echo $$host; \
		ssh opencirrus-$$host.hpl.hp.com "cp /tmp/sar.sh $(SAR_DATA_DIR)"; \
	done
