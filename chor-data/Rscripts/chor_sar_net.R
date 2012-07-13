data <- list(read.table("data/chor/sar/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/net_sar.out",head=FALSE,sep=" ")) 

roles <- c("chor_broker","chor_registry","chor_shipper","chor_sm1","chor_sm2","chor_sm3")

png("png/chor_sar_net%d.png")
for (i in 1:length(data)) {
 frame <- data[[i]]
 plot.ts(frame[,1],frame[,2],xlab="Timestamp",ylab="Net_usage(kB)",main=roles[i],type="l",col="blue")
lines(frame[,1],frame[,3],col="red")
legend(4300, c("received","transmitted"), col=c("blue","red"), lty=1, lwd=3, box.lty=0)
}
dev.off()

