library("ggplot2")

chor_data <- list(read.table("data/chor/time/001/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/002/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/003/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/004/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/005/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/006/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/007/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/008/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/009/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/010/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/011/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/012/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/013/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/014/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/015/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/016/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/017/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/018/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/019/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/020/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/050/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/100/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/150/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/200/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/250/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/300/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/350/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/400/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/450/purchase.log",head=FALSE,sep=" "), read.table("data/chor/time/500/purchase.log",head=FALSE,sep=" "))

per95_chor <- rep(0, length(chor_data))
for (i in 1:length(chor_data)) {
	per95_chor[i] = sort(chor_data[[i]][,2])[round(length(chor_data[[i]][,2])*0.95)]	
}

banco = data.frame(Threads=c(1:20,seq(50, 500, 50)),
			response_time = per95_chor)


# Plot
	png("png/xtended_95_chor_time_purchase.png")
	p <- ggplot(banco, aes(x=Threads))
	p + geom_line(aes(y=response_time))
	dev.off()

