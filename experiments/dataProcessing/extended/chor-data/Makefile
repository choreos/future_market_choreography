LOAD_NODE=opencirrus-07901.hpl.hp.com
NODE1=opencirrus-07904.hpl.hp.com
NODE2=opencirrus-07905.hpl.hp.com
NODE3=opencirrus-07906.hpl.hp.com
NODE4=opencirrus-07907.hpl.hp.com
NODE5=opencirrus-07908.hpl.hp.com
NODE6=opencirrus-07912.hpl.hp.com
NODE7=opencirrus-07913.hpl.hp.com
NODE8=opencirrus-07914.hpl.hp.com
NODE9=opencirrus-07915.hpl.hp.com
WS_NODES=07904 07905 07906 07907 07908 07912 07913 07914 07915

SAR_DATA_DIR=~/sar
EXPERIMENT_DIR=~/experiments

data_dir:
	if ! test -d data/chor; then \
		mkdir -p data/chor; \
	fi

load-logs: data_dir
    scp $(LOAD_NODE):$(EXPERIMENT_DIR)/{load-generator,matrix}.log data/chor/

apache-log: data_dir
	@echo Copying broker apache log. This may take a while...
	ssh $(NODE3) "cd apache-tomcat*/logs; tar cjf - localhost_access_log*" | tar xjf - -C data/chor/

time-tar:
	ssh $(LOAD_NODE) "cd $(EXPERIMENT_DIR) && tar cjf chor-time.tar.bz2 data/chor/time/*"

time-download: load-log
	scp $(LOAD_NODE):$(EXPERIMENT_DIR)/chor-time.tar.bz2 .

time-clean:
	rm chor-time.tar.bz2

sar-download-matrix:
	if test ! -e matrix.log; then \
		scp $(LOAD_NODE):experiments/matrix.log . ; \
	fi

sar-create-script: sar-download-matrix
	head -n 2 sar.template.sh >sar.sh
	cat matrix.log >>sar.sh
	tail -n 24 sar.template.sh >>sar.sh
	chmod 755 sar.sh

sar-upload:
	if test ! -e sar.sh; then \
		make sar-create-script; \
	fi
	scp sar.sh opencirrus-07901.hpl.hp.com:/tmp 
	ssh opencirrus-07901.hpl.hp.com "~/scp_to_all.sh /tmp/sar.sh" 
	for host in $(WS_NODES); do \
    echo $$host; \
    ssh opencirrus-$$host.hpl.hp.com "cp /tmp/sar.sh $(SAR_DATA_DIR)"; \
	done

sar-run:
	for host in $(WS_NODES); do \
		(ssh opencirrus-$$host.hpl.hp.com "cd $(SAR_DATA_DIR); find chor/ -type f -exec rm {} \;; ./sar.sh chor; cd chor; tar cjf sar.tar.bz2 *"; \
		echo "sar-run: $$host finished")& \
	done
	@echo 'When finished, the node number will be shown.'

sar-download: apache-log
	scp $(NODE1):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node1.tar.bz2
	scp $(NODE2):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node2.tar.bz2
	scp $(NODE3):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node3.tar.bz2
	scp $(NODE4):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node4.tar.bz2
	scp $(NODE5):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node5.tar.bz2
	scp $(NODE6):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node6.tar.bz2
	scp $(NODE7):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node7.tar.bz2
	scp $(NODE8):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node8.tar.bz2
	scp $(NODE9):$(SAR_DATA_DIR)/chor/sar.tar.bz2 chor-sar-node9.tar.bz2

sar-clean:
	rm sar.sh matrix.log chor-sar-node?.tar.bz2

data-extract:
	tar xjf chor-time.tar.bz2
	mkdir data/chor/sar
	for i in `seq 1 9`; do \
		mkdir data/chor/sar/node$$i; \
		cd data/chor/sar/node$$i; \
		tar xjf ../../../../chor-sar-node$$i.tar.bz2; \
		cd -; \
	done

data-compress:
	tar cjf data.tar.bz2 data

all-clean: time-clean sar-clean
	if test -d data; then rm -rf data; fi
