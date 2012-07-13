library("ggplot2")

chor_node1_data <- list(read.table("data/chor/sar/node1/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node1/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node2_data <- list(read.table("data/chor/sar/node2/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node2/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node3_data <- list(read.table("data/chor/sar/node3/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node3/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node4_data <- list(read.table("data/chor/sar/node4/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node4/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node5_data <- list(read.table("data/chor/sar/node5/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node5/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node6_data <- list(read.table("data/chor/sar/node6/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node6/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node7_data <- list(read.table("data/chor/sar/node7/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node7/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node8_data <- list(read.table("data/chor/sar/node8/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node8/500/cpu_sar.out",head=FALSE,sep=" ")) 

chor_node9_data <- list(read.table("data/chor/sar/node9/050/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/100/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/150/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/200/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/250/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/300/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/350/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/400/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/450/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/node9/500/cpu_sar.out",head=FALSE,sep=" "))


chor_data <- list(chor_node1_data, chor_node2_data, chor_node3_data, chor_node4_data, chor_node5_data, chor_node6_data, chor_node7_data, chor_node8_data, chor_node9_data)

name <- c("png/xtended_mean_chor_sar_cpu_node1.png", "png/xtended_mean_chor_sar_cpu_node2.png", "png/xtended_mean_chor_sar_cpu_node3.png", "png/xtended_mean_chor_sar_cpu_node4.png", "png/xtended_mean_chor_sar_cpu_node5.png", "png/xtended_mean_chor_sar_cpu_node6.png", "png/xtended_mean_chor_sar_cpu_node7.png", "png/xtended_mean_chor_sar_cpu_node8.png", "png/xtended_mean_chor_sar_cpu_node9.png")

for (i in 1:length(chor_data)) {
	chor_table <- chor_data[[i]]

	chor_mean = rep(0, length(chor_table))
	chor_sigma = rep(0, length(chor_table))
	for (j in 1:length(chor_table)) {
	   chor_mean[j] = mean(100-chor_table[[j]][,3])
	   chor_sigma[j] = sd(100-chor_table[[j]][,3])
	}

banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500),
			 chor_Mean_of_cpu_usage = chor_mean,
			 chor_sigma = chor_sigma
		  )

# Mean Variation
	chor_limits = aes(ymax = chor_Mean_of_cpu_usage + chor_sigma, ymin= chor_Mean_of_cpu_usage - chor_sigma, linetype="chor_sd")

# Plot
	png(name[i])

	print(ggplot(banco, aes(x=Threads, linetype="legend")) + geom_line(aes(y=chor_Mean_of_cpu_usage, linetype="chor")) + geom_errorbar(chor_limits, width=50) + scale_y_continuous(limits=c(0,100)))
	dev.off()
}

