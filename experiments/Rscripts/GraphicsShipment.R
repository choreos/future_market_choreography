
# Libraries
	install.packages("ggplot2") 
	library("ggplot2")

# Reading data
	dados50 <- read.table("50_purchase.log",head=FALSE,sep=" ")
	dados100 <- read.table("100_shipment.log",head=FALSE,sep=" ")
	dados150 <- read.table("150_shipment.log",head=FALSE,sep=" ")
	dados200 <- read.table("200_shipment.log",head=FALSE,sep=" ")
	dados300 <- read.table("300_shipment.log",head=FALSE,sep=" ")
	dados400 <- read.table("400_shipment.log",head=FALSE,sep=" ")
	dados500 <- read.table("500_shipment.log",head=FALSE,sep=" ")
	dados600 <- read.table("600_shipment.log",head=FALSE,sep=" ")
	dados700 <- read.table("700_shipment.log",head=FALSE,sep=" ")
	dados800 <- read.table("800_shipment.log",head=FALSE,sep=" ")

# Descript analysis
	
	minimo = min(dados50[,2],dados200[,2],dados400[,2],dados800[,2])
	maximo = max(dados50[,2],dados200[,2],dados400[,2],dados800[,2])

	par(mfrow=c(2,2))
		plot(dados50[,1],dados50[,2],xlab="Timestamp",ylab="Delay",main="Shipment - 50 threads",ylim=c(minimo,maximo))
		plot(dados200[,1],dados200[,2],xlab="Timestamp",ylab="Delay",main="Shipment - 200 threads",ylim=c(minimo,maximo))
		plot(dados400[,1],dados400[,2],xlab="Timestamp",ylab="Delay",main="Shipment - 400 threads",ylim=c(minimo,maximo))
		plot(dados800[,1],dados800[,2],xlab="Timestamp",ylab="Delay",main="Shipment - 800 threads",ylim=c(minimo,maximo))

# Data
	banco = data.frame(Shipment_Threads=c(50,100,150,200,300,400,500,600,700,800),
			 Mean_of_Delay = c(mean(dados50[2]),mean(dados100[2]),mean(dados150[2]),mean(dados200[2]),mean(dados300[2]),mean(dados400[2]),mean(dados500[2]),mean(dados600[2]),mean(dados700[2]),mean(dados800[2])),
			 sigma=c(sd(dados50[2]),sd(dados100[2]),sd(dados150[2]),sd(dados200[2]),sd(dados300[2]),sd(dados400[2]),sd(dados500[2]),sd(dados600[2]),sd(dados700[2]),sd(dados800[2])))

# Mean Variation
	limits = aes(ymax = Mean_of_Delay + sigma, ymin= Mean_of_Delay - sigma)

# Plot
	p <- ggplot(banco, aes(x=Shipment_Threads,y=Mean_of_Delay))
	p + geom_point() + geom_errorbar(limits, width=50)

# Linear Fit
	lm(banco$Threads ~banco$Mean_of_Delay)




