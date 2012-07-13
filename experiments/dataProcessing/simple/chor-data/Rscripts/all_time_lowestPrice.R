library("ggplot2")

chor_data <- list(read.table("data/chor/time/050/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/100/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/150/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/200/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/250/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/300/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/350/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/400/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/450/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/500/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/550/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/600/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/650/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/700/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/750/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/800/lowest_price.log",head=FALSE,sep=" "))

orch_data <- list(read.table("data/orch/time/050/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/100/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/150/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/200/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/250/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/300/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/350/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/400/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/450/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/500/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/550/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/600/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/650/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/700/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/750/lowest_price.log",head=FALSE,sep=" "), read.table("data/orch/time/800/lowest_price.log",head=FALSE,sep=" ")) 

banco = data.frame(Threads=c(050,100,150,200,250,300,350,400,450,500,550,600,650,700,750,800),
			 chor_Mean_of_Delay = c(mean(chor_data[[1]][2]),mean(chor_data[[2]][2]),mean(chor_data[[3]][2]),mean(chor_data[[4]][2]),mean(chor_data[[5]][2]),mean(chor_data[[6]][2]),mean(chor_data[[7]][2]),mean(chor_data[[8]][2]),mean(chor_data[[9]][2]),mean(chor_data[[10]][2]),mean(chor_data[[11]][2]),mean(chor_data[[12]][2]),mean(chor_data[[13]][2]),mean(chor_data[[14]][2]),mean(chor_data[[15]][2]),mean(chor_data[[16]][2])),
			 chor_sigma=c(sd(chor_data[[1]][2]),sd(chor_data[[2]][2]),sd(chor_data[[3]][2]),sd(chor_data[[4]][2]),sd(chor_data[[5]][2]),sd(chor_data[[6]][2]),sd(chor_data[[7]][2]),sd(chor_data[[8]][2]),sd(chor_data[[9]][2]),sd(chor_data[[10]][2]),sd(chor_data[[11]][2]),sd(chor_data[[12]][2]),sd(chor_data[[13]][2]),sd(chor_data[[14]][2]),sd(chor_data[[15]][2]),sd(chor_data[[16]][2])),
			orch_Mean_of_Delay = c(mean(orch_data[[1]][2]),mean(orch_data[[2]][2]),mean(orch_data[[3]][2]),mean(orch_data[[4]][2]),mean(orch_data[[5]][2]),mean(orch_data[[6]][2]),mean(orch_data[[7]][2]),mean(orch_data[[8]][2]),mean(orch_data[[9]][2]),mean(orch_data[[10]][2]),mean(orch_data[[11]][2]),mean(orch_data[[12]][2]),mean(orch_data[[13]][2]),mean(orch_data[[14]][2]),mean(orch_data[[15]][2]),mean(orch_data[[16]][2])),
			 orch_sigma=c(sd(orch_data[[1]][2]),sd(orch_data[[2]][2]),sd(orch_data[[3]][2]),sd(orch_data[[4]][2]),sd(orch_data[[5]][2]),sd(orch_data[[6]][2]),sd(orch_data[[7]][2]),sd(orch_data[[8]][2]),sd(orch_data[[9]][2]),sd(orch_data[[10]][2]),sd(orch_data[[11]][2]),sd(orch_data[[12]][2]),sd(orch_data[[13]][2]),sd(orch_data[[14]][2]),sd(orch_data[[15]][2]),sd(orch_data[[16]][2])))


# Mean Variation
	chor_limits = aes(ymax = chor_Mean_of_Delay + chor_sigma, ymin= chor_Mean_of_Delay - chor_sigma, linetype="chor_sd")
	orch_limits = aes(ymax = orch_Mean_of_Delay + orch_sigma, ymin= orch_Mean_of_Delay - orch_sigma, linetype="orch_sd")

# Plot
	png("png/all_mean_lowestPrice.png")
	p <- ggplot(banco, aes(x=Threads, linetype="legend"))
	p + geom_line(aes(y=chor_Mean_of_Delay, linetype="chor")) + geom_errorbar(chor_limits, width=50) + geom_line(aes(y=orch_Mean_of_Delay, linetype="orch")) + geom_errorbar(orch_limits, width=50)  
	dev.off()

