library("ggplot2")

chor_node1_data <- list(read.table("data/chor/sar/node1/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node2_data <- list(read.table("data/chor/sar/node2/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node3_data <- list(read.table("data/chor/sar/node3/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node4_data <- list(read.table("data/chor/sar/node4/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node5_data <- list(read.table("data/chor/sar/node5/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node6_data <- list(read.table("data/chor/sar/node6/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node7_data <- list(read.table("data/chor/sar/node7/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node8_data <- list(read.table("data/chor/sar/node8/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/500/net_sar.out",head=FALSE,sep=" ")) 

chor_node9_data <- list(read.table("data/chor/sar/node9/050/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/100/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/150/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/200/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/250/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/300/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/350/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/400/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/450/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/500/net_sar.out",head=FALSE,sep=" "))

chor_data <- list(chor_node1_data, chor_node2_data, chor_node3_data, chor_node4_data, chor_node5_data, chor_node6_data, chor_node7_data, chor_node8_data, chor_node9_data)

name <- c("png/xtended_mean_chor_sar_net_trn_node1.png", "png/xtended_mean_chor_sar_net_trn_node2.png", "png/xtended_mean_chor_sar_net_trn_node3.png", "png/xtended_mean_chor_sar_net_trn_node4.png", "png/xtended_mean_chor_sar_net_trn_node5.png", "png/xtended_mean_chor_sar_net_trn_node6.png", "png/xtended_mean_chor_sar_net_trn_node7.png", "png/xtended_mean_chor_sar_net_trn_node8.png", "png/xtended_mean_chor_sar_net_trn_node9.png")

for (i in 1:length(chor_data)) {
	chor_table <- chor_data[[i]]


	chor_trans = rep(0, length(chor_table))
	chor_sigma = rep(0, length(chor_table))
	for (j in 1:length(chor_table)) {
	   chor_trans[j] = mean(chor_table[[j]][3])
	   chor_sigma[j] = sd(chor_table[[j]][3])
	}

	banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500),
			   chor_Mean=chor_trans,
			   chor_sig=chor_sigma
		          )

# Mean Variation
	chor_limits = aes(ymax = chor_Mean + chor_sig, ymin= chor_Mean - chor_sig, linetype="sd")
	

# Plot
	png(name[i])

	print(ggplot(banco, aes(x=Threads, linetype="legend")) + geom_line(aes(y=chor_Mean, linetype="chor(kB/s)")) + geom_errorbar(chor_limits, width=50) + scale_y_continuous(limits=c(0,1000)))
	dev.off()
}


name <- c("png/xtended_mean_chor_sar_net_rcv_node1.png", "png/xtended_mean_chor_sar_net_rcv_node2.png", "png/xtended_mean_chor_sar_net_rcv_node3.png", "png/xtended_mean_chor_sar_net_rcv_node4.png", "png/xtended_mean_chor_sar_net_rcv_node5.png", "png/xtended_mean_chor_sar_net_rcv_node6.png", "png/xtended_mean_chor_sar_net_rcv_node7.png", "png/xtended_mean_chor_sar_net_rcv_node8.png", "png/xtended_mean_chor_sar_net_rcv_node9.png")

for (i in 1:length(chor_data)) {
	chor_table <- chor_data[[i]]


	chor_trans = rep(0, length(chor_table))
	chor_sigma = rep(0, length(chor_table))
	for (j in 1:length(chor_table)) {
	   chor_trans[j] = mean(chor_table[[j]][2])
	   chor_sigma[j] = sd(chor_table[[j]][2])
	}

	banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500),
			   chor_Mean=chor_trans,
			   chor_sig=chor_sigma
		          )

# Mean Variation
	chor_limits = aes(ymax = chor_Mean + chor_sig, ymin= chor_Mean - chor_sig, linetype="sd")
	

# Plot
	png(name[i])

	print(ggplot(banco, aes(x=Threads, linetype="legend")) + geom_line(aes(y=chor_Mean, linetype="chor(kB/s)")) + geom_errorbar(chor_limits, width=50) + scale_y_continuous(limits=c(0,1000)))
	dev.off()
}



