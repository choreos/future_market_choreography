library("ggplot2")

data <- list(read.table("data/chor/050/purchase.log",head=FALSE,sep=" "), read.table("data/chor/100/purchase.log",head=FALSE,sep=" "), read.table("data/chor/150/purchase.log",head=FALSE,sep=" "), read.table("data/chor/200/purchase.log",head=FALSE,sep=" "), read.table("data/chor/250/purchase.log",head=FALSE,sep=" "), read.table("data/chor/300/purchase.log",head=FALSE,sep=" "), read.table("data/chor/350/purchase.log",head=FALSE,sep=" "), read.table("data/chor/400/purchase.log",head=FALSE,sep=" "), read.table("data/chor/450/purchase.log",head=FALSE,sep=" "), read.table("data/chor/500/purchase.log",head=FALSE,sep=" "), read.table("data/chor/550/purchase.log",head=FALSE,sep=" "), read.table("data/chor/600/purchase.log",head=FALSE,sep=" "), read.table("data/chor/650/purchase.log",head=FALSE,sep=" "), read.table("data/chor/700/purchase.log",head=FALSE,sep=" "), read.table("data/chor/750/purchase.log",head=FALSE,sep=" "), read.table("data/chor/800/purchase.log",head=FALSE,sep=" "))


banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800),
			 Mean_of_Delay = c(mean(data[[1]][2]),mean(data[[2]][2]),mean(data[[3]][2]),mean(data[[4]][2]),mean(data[[5]][2]),mean(data[[6]][2]),mean(data[[7]][2]),mean(data[[8]][2]),mean(data[[9]][2]),mean(data[[10]][2]),mean(data[[11]][2]),mean(data[[12]][2]),mean(data[[13]][2]),mean(data[[14]][2]),mean(data[[15]][2]),mean(data[[16]][2])),
			 sigma=c(sd(data[[1]][2]),sd(data[[2]][2]),sd(data[[3]][2]),sd(data[[4]][2]),sd(data[[5]][2]),sd(data[[6]][2]),sd(data[[7]][2]),sd(data[[8]][2]),sd(data[[9]][2]),sd(data[[10]][2]),sd(data[[11]][2]),sd(data[[12]][2]),sd(data[[13]][2]),sd(data[[14]][2]),sd(data[[15]][2]),sd(data[[16]][2])))

# Mean Variation
	limits = aes(ymax = Mean_of_Delay + sigma, ymin= Mean_of_Delay - sigma)

# Plot
	png("png/chor_mean_purchase.png")
	p <- ggplot(banco, aes(x=Threads,y=Mean_of_Delay))
	p + geom_point() + geom_errorbar(limits, width=50)
	dev.off()
