library("ggplot2")

chor_data <- list(read.table("data/chor/time/001/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/002/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/003/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/004/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/005/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/006/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/007/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/008/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/009/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/010/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/011/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/012/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/013/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/014/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/015/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/016/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/017/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/018/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/019/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/020/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/050/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/100/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/150/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/200/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/250/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/300/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/350/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/400/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/450/lowest_price.log",head=FALSE,sep=" "), read.table("data/chor/time/500/lowest_price.log",head=FALSE,sep=" "))

per95_chor <- rep(0, length(chor_data))
for (i in 1:length(chor_data)) {
	per95_chor[i] = sort(chor_data[[i]][,2])[round(length(chor_data[[i]][,2])*0.95)]	
}

banco = data.frame(Threads=c(1:20,seq(50, 500, 50)),
			response_time = per95_chor)


# Plot
	png("png/xtended_95_chor_time_lowestPrice.png")
	p <- ggplot(banco, aes(x=Threads))
	p + geom_line(aes(y=response_time))
	dev.off()

