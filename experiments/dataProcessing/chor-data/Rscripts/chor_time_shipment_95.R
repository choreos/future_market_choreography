library("ggplot2")

chor_data <- list(read.table("data/chor/time/001/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/002/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/003/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/004/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/005/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/006/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/007/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/008/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/009/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/010/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/011/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/012/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/013/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/014/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/015/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/016/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/017/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/018/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/019/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/020/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/050/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/100/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/150/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/200/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/250/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/300/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/350/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/400/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/450/shipment.log",head=FALSE,sep=" "), read.table("data/chor/time/500/shipment.log",head=FALSE,sep=" "))

per95_chor <- rep(0, length(chor_data))
for (i in 1:length(chor_data)) {
	per95_chor[i] = sort(chor_data[[i]][,2])[round(length(chor_data[[i]][,2])*0.95)]	
}

banco = data.frame(Threads=c(1:20,seq(50, 500, 50)),
			response_time = per95_chor)


# Plot
	png("png/xtended_95_chor_time_shipment.png")
	p <- ggplot(banco, aes(x=Threads))
	p + geom_line(aes(y=response_time))
	dev.off()

