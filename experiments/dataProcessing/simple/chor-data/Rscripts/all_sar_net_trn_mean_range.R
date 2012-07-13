library("ggplot2")

chor_registry_data <- list(read.table("data/chor/sar/registry/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/800/net_sar.out",head=FALSE,sep=" "))

chor_broker_data <- list(read.table("data/chor/sar/broker/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/800/net_sar.out",head=FALSE,sep=" "))

chor_shipper_data <- list(read.table("data/chor/sar/shipper/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/800/net_sar.out",head=FALSE,sep=" "))

chor_sm1_data <- list(read.table("data/chor/sar/sm1/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/800/net_sar.out",head=FALSE,sep=" "))

chor_sm2_data <- list(read.table("data/chor/sar/sm2/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/800/net_sar.out",head=FALSE,sep=" "))

chor_sm3_data <- list(read.table("data/chor/sar/sm3/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/800/net_sar.out",head=FALSE,sep=" "))

orch_registry_data <- list(read.table("data/orch/sar/registry/050/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/100/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/150/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/200/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/250/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/300/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/350/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/400/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/450/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/500/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/550/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/600/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/650/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/700/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/750/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/800/net_sar.out",head=FALSE,sep=" "))

orch_broker_data <- list(read.table("data/orch/sar/broker/050/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/100/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/150/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/200/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/250/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/300/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/350/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/400/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/450/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/500/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/550/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/600/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/650/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/700/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/750/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/broker/800/net_sar.out",head=FALSE,sep=" "))

orch_shipper_data <- list(read.table("data/orch/sar/shipper/050/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/100/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/150/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/200/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/250/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/300/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/350/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/400/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/450/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/500/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/550/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/600/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/650/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/700/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/750/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/800/net_sar.out",head=FALSE,sep=" "))

orch_sm1_data <- list(read.table("data/orch/sar/sm1/050/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/100/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/150/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/200/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/250/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/300/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/350/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/400/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/450/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/500/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/550/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/600/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/650/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/700/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/750/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/800/net_sar.out",head=FALSE,sep=" "))

orch_sm2_data <- list(read.table("data/orch/sar/sm2/050/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/100/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/150/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/200/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/250/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/300/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/350/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/400/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/450/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/500/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/550/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/600/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/650/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/700/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/750/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/800/net_sar.out",head=FALSE,sep=" "))

orch_sm3_data <- list(read.table("data/orch/sar/sm3/050/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/100/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/150/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/200/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/250/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/300/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/350/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/400/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/450/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/500/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/550/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/600/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/650/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/700/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/750/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/800/net_sar.out",head=FALSE,sep=" "))


chor_data <- list(chor_registry_data, chor_broker_data, chor_shipper_data, chor_sm1_data, chor_sm2_data, chor_sm3_data)
orch_data <- list(orch_registry_data, orch_broker_data, orch_shipper_data, orch_sm1_data, orch_sm2_data, orch_sm3_data)

name <- c("png/all_sar_net_trn_registry.png", "png/all_sar_net_trn_broker.png", "png/all_sar_net_trn_shipper.png", "png/all_sar_net_trn_sm1.png", "png/all_sar_net_trn_sm2.png", "png/all_sar_net_trn_sm3.png")

for (i in 1:length(chor_data)) {
	chor_table <- chor_data[[i]]
	orch_table <- orch_data[[i]]


	chor_trans = rep(0, 16)
	for (j in 1:length(chor_trans)) {
	   chor_trans[j] = mean(chor_table[[j]][3])
	}

	orch_trans = rep(0, 16)
	for (j in 1:length(orch_trans)) {
	   orch_trans[j] = mean(orch_table[[j]][3])
	}

	chor_sigma = rep(0, 16)
	for (j in 1:length(chor_sigma)) {
	   chor_sigma[j] = sd(chor_table[[j]][3])
	}

	orch_sigma = rep(0, 16)
	for (j in 1:length(orch_sigma)) {
	   orch_sigma[j] = sd(orch_table[[j]][3])
	}

	banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800),
			   chor_Mean=chor_trans,
			   orch_Mean=orch_trans,
			   chor_sig=chor_sigma,
			   orch_sig=orch_sigma
		          )

# Mean Variation
	chor_limits = aes(ymax = chor_Mean + chor_sig, ymin= chor_Mean - chor_sig, linetype="sd")
	orch_limits = aes(ymax = orch_Mean + orch_sig, ymin= orch_Mean - orch_sig, linetype="sd")
	

# Plot
	png(name[i])

	print(ggplot(banco, aes(x=Threads, linetype="legend")) + geom_line(aes(y=chor_Mean, linetype="chor(kB/s)")) + geom_errorbar(chor_limits, width=50) + geom_line(aes(y=orch_Mean, linetype="orch(kB/s)")) + geom_errorbar(orch_limits, width=50) + scale_y_continuous(limits=c(0,7000)))
	dev.off()
}


name <- c("png/all_sar_net_rcv_registry.png", "png/all_sar_net_rcv_broker.png", "png/all_sar_net_rcv_shipper.png", "png/all_sar_net_rcv_sm1.png", "png/all_sar_net_rcv_sm2.png", "png/all_sar_rcv_trn_sm3.png")

for (i in 1:length(chor_data)) {
	chor_table <- chor_data[[i]]
	orch_table <- orch_data[[i]]


	chor_trans = rep(0, 16)
	for (j in 1:length(chor_trans)) {
	   chor_trans[j] = mean(chor_table[[j]][2])
	}

	orch_trans = rep(0, 16)
	for (j in 1:length(orch_trans)) {
	   orch_trans[j] = mean(orch_table[[j]][2])
	}

	chor_sigma = rep(0, 16)
	for (j in 1:length(chor_sigma)) {
	   chor_sigma[j] = sd(chor_table[[j]][2])
	}

	orch_sigma = rep(0, 16)
	for (j in 1:length(orch_sigma)) {
	   orch_sigma[j] = sd(orch_table[[j]][2])
	}

	banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800),
			   chor_Mean=chor_trans,
			   orch_Mean=orch_trans,
			   chor_sig=chor_sigma,
			   orch_sig=orch_sigma
		          )

# Mean Variation
	chor_limits = aes(ymax = chor_Mean + chor_sig, ymin= chor_Mean - chor_sig, linetype="sd")
	orch_limits = aes(ymax = orch_Mean + orch_sig, ymin= orch_Mean - orch_sig, linetype="sd")
	

# Plot
	png(name[i])

	print(ggplot(banco, aes(x=Threads, linetype="legend")) + geom_line(aes(y=chor_Mean, linetype="chor(kB/s)")) + geom_errorbar(chor_limits, width=50) + geom_line(aes(y=orch_Mean, linetype="orch(kB/s)")) + geom_errorbar(orch_limits, width=50) + scale_y_continuous(limits=c(0,7000)))
	dev.off()
}



