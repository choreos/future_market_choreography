data <- list(read.table("data/orch/sar/broker/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/net_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/net_sar.out",head=FALSE,sep=" ")) 

roles <- c("orch_broker","orch_registry","orch_shipper","orch_sm1","orch_sm2","orch_sm3")

png("png/orch_sar_net%d.png")
for (i in 1:length(data)) {
 frame <- data[[i]]
 plot.ts(frame[,1],frame[,2],xlab="Timestamp",ylab="Net_usage(kB)",main=roles[i],type="l",col="blue")
lines(frame[,1],frame[,3],col="red")
legend(4300, c("received","transmitted"), col=c("blue","red"), lty=1, lwd=3, box.lty=0)
}
dev.off()

