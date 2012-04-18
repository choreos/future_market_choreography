library("ggplot2")

registry_data <- list(read.table("data/chor/sar/registry/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/800/net_sar.out",head=FALSE,sep=" ")) 

broker_data <- list(read.table("data/chor/sar/broker/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/broker/800/net_sar.out",head=FALSE,sep=" ")) 

shipper_data <- list(read.table("data/chor/sar/shipper/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/800/net_sar.out",head=FALSE,sep=" ")) 

sm1_data <- list(read.table("data/chor/sar/sm1/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/800/net_sar.out",head=FALSE,sep=" ")) 

sm2_data <- list(read.table("data/chor/sar/sm2/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/800/net_sar.out",head=FALSE,sep=" ")) 

sm3_data <- list(read.table("data/chor/sar/sm3/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/500/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/550/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/600/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/650/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/700/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/750/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/800/net_sar.out",head=FALSE,sep=" ")) 

all_data <- list(registry_data, broker_data, shipper_data, sm1_data, sm2_data, sm3_data)


name <- c("png/chor_sar_net_registry.png", "png/chor_sar_net_broker.png", "png/chor_sar_net_shipper.png", "png/chor_sar_net_sm1.png", "png/chor_sar_net_sm2.png", "png/chor_sar_net_sm3.png")

for (i in 1:length(all_data)) {
	data <- all_data[[i]]


	upload = rep(0, 16)
	for (j in 1:length(upload)) {
	   upload[j] = mean(data[[j]][3])
	}

	download = rep(0, 16)
	for (j in 1:length(download)) {
	   download[j] = mean(data[[j]][2])
	}
	ulsigma = rep(0, 16)
	for (j in 1:length(ulsigma)) {
	   ulsigma[j] = sd(data[[j]][3])
	}
	dlsigma = rep(0, 16)
	for (j in 1:length(dlsigma)) {
	   dlsigma[j] = sd(data[[j]][2])
	}

	banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800),
			   Mean_of_Upload=upload,
			   Mean_of_Download=download,
			   usigma=ulsigma,
			   dsigma=dlsigma
		          )

# Mean Variation
	ulimits = aes(ymax = Mean_of_Upload + usigma, ymin= Mean_of_Upload - usigma, linetype="sd")
	dlimits = aes(ymax = Mean_of_Download + dsigma, ymin= Mean_of_Download - dsigma, linetype="sd")

# Plot
	png(name[i])

	print(ggplot(banco, aes(x=Threads, linetype="legend")) + geom_line(aes(y=Mean_of_Upload, linetype="transmitted(kB)")) + geom_errorbar(ulimits, width=50) + geom_line(aes(y=Mean_of_Download, linetype="received(kB)")) + geom_errorbar(dlimits, width=50) + scale_y_continuous(limits=c(0,2000)))
	dev.off()
}



