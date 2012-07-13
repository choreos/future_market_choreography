library("ggplot2")

chor_data <- list(read.table("data/chor/time/050/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/100/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/150/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/200/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/250/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/300/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/350/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/400/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/450/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/500/purchase.log",head=FALSE,sep=" "))

chor_mean = rep(0, length(chor_data))
chor_sigma = rep(0, length(chor_data))
for (j in 1:length(chor_data)) {
   chor_mean[j] = mean(chor_data[[j]][2])
   chor_sigma[j] = sd(chor_data[[j]][2])
}


banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500),
			 chor_Mean_of_Delay = chor_mean,
			 chor_sigma = chor_sigma)


# Mean Variation
	chor_limits = aes(ymax = chor_Mean_of_Delay + chor_sigma, ymin= chor_Mean_of_Delay - chor_sigma, linetype="chor_sd")

# Plot
	png("png/xtended_mean_chor_purchase.png")
	p <- ggplot(banco, aes(x=Threads, linetype="legend"))
	p + geom_line(aes(y=chor_Mean_of_Delay, linetype="chor")) + geom_errorbar(chor_limits, width=50) 
	dev.off()

