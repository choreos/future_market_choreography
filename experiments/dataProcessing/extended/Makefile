orch: replace

replace: copy
	sed -i 's/chor/orch/g' orch-data/Makefile
	sed -i 's/chor/orch/g' orch-data/Rscripts/chor*.R
# The chor broker/customer will be called orchestrator
	sed -i 's/customer/orchestrator/g' orch-data/deploy/Makefile
	sed -i 's/broker/orchestrator/g' orch-data/deploy/Makefile
	sed -i 's/BROKER/ORCH/g' orch-data/deploy/Makefile

copy:
	cp -r chor-data orch-data

clean:
	if test -d orch-data; then \
    rm -r orch-data; \
  fi
