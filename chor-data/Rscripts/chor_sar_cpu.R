data <- list(read.table("data/chor/sar/broker/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/cpu_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/cpu_sar.out",head=FALSE,sep=" ")) 

roles <- c("chor_broker","chor_registry","chor_shipper","chor_sm1","chor_sm2","chor_sm3")


png("png/chor_sar_cpu%d.png")
for (i in 1:length(data)) {
 frame <- data[[i]]
plot.ts(frame[frame[,2]==0,1],100-frame[frame[,2]==0,3],xlab="Timestamp",ylab="CPU_usage(%)",main=roles[i],type="l")
lines(frame[frame[,2]==0,1],100-frame[frame[,2]==1,3],col="red")
lines(frame[frame[,2]==0,1],100-frame[frame[,2]==2,3],col="blue")
lines(frame[frame[,2]==0,1],100-frame[frame[,2]==3,3],col="green")
}
dev.off()

