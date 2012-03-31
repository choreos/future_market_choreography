library("ggplot2")

registry_data <- list(read.table("data/chor/050/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/100/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/150/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/200/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/250/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/300/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/350/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/400/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/450/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/500/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/550/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/600/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/650/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/700/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/750/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/800/registry/net_sar.out",head=FALSE,sep=" "))
broker_data <- list(read.table("data/chor/050/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/100/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/150/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/200/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/250/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/300/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/350/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/400/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/450/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/500/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/550/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/600/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/650/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/700/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/750/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/800/broker/net_sar.out",head=FALSE,sep=" ")) 
shipper_data <- list(read.table("data/chor/050/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/100/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/150/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/200/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/250/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/300/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/350/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/400/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/450/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/500/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/550/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/600/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/650/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/700/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/750/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/800/shipper/net_sar.out",head=FALSE,sep=" "))
sm1_data <- list(read.table("data/chor/050/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/100/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/150/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/200/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/250/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/300/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/350/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/400/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/450/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/500/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/550/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/600/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/650/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/700/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/750/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/800/sm1/net_sar.out",head=FALSE,sep=" ")) 
sm2_data <- list(read.table("data/chor/050/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/100/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/150/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/200/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/250/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/300/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/350/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/400/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/450/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/500/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/550/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/600/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/650/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/700/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/750/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/800/sm2/net_sar.out",head=FALSE,sep=" "))
sm3_data <- list(read.table("data/chor/050/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/100/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/150/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/200/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/250/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/300/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/350/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/400/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/450/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/500/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/550/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/600/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/650/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/700/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/750/sm3/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/800/sm3/net_sar.out",head=FALSE,sep=" ")) 


all_data <- list(registry_data, broker_data, shipper_data, sm1_data, sm2_data, sm3_data)


name <- c("chor_sar_net_registry.png", "chor_sar_net_broker.png", "chor_sar_net_shipper.png", "chor_sar_net_sm1.png", "chor_sar_net_sm2.png", "chor_sar_net_sm3.png")

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

	print(ggplot(banco, aes(x=Threads, linetype="legend")) + geom_line(aes(y=Mean_of_Upload, linetype="upload")) + geom_errorbar(ulimits, width=50) + geom_line(aes(y=Mean_of_Download, linetype="download")) + geom_errorbar(dlimits, width=50))
	dev.off()
}



