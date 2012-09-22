library("ggplot2")
chor_data <- chor_data <- list(read.table("data/chor/time/001/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/002/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/003/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/004/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/005/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/006/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/007/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/008/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/009/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/010/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/011/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/012/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/013/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/014/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/015/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/016/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/017/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/018/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/019/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/020/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/050/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/100/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/150/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/200/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/250/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/300/all_operations.log",head=FALSE,sep=" "), read.table("data/chor/time/350/all_operations.log",head=FALSE,sep=" "))

# r <- rle(floor(chor_data[[20]][,1]/1000))
# plot(r$values, r$lengths, type='l')

requests <- rep(0, length(chor_data))
responses <- rep(0, length(chor_data))
for (i in 1:length(chor_data)) {
	data <- rle(floor(chor_data[[i]][,1]/1000))$lengths
	requests[i] = sort( data )[round(length(data)*0.95)]
	data <- rle(floor((chor_data[[i]][,1]+chor_data[[i]][,2])/1000))$lengths
	responses[i] = sort( data )[round(length(data)*0.95)]
}

database = data.frame(threads=c(1:20,seq(50, 350, 50)),
	req = requests,
	res = responses
)

png("png/xtended_95_chor_req.png")
ggplot(database, aes(x=threads, linetype="legend")) + geom_line(aes(y=req, linetype="requests/sec")) + geom_line(aes(y=res, linetype="responses/sec")) + scale_y_continuous(limits=c(0,120))
dev.off()
