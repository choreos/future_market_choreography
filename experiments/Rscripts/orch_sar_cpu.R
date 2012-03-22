data <- list(read.table("data/orch/sar/broker/cpu_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/cpu_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/cpu_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/cpu_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/cpu_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/cpu_sar.out",head=FALSE,sep=" ")) 

roles <- c("orch_broker","orch_registry","orch_shipper","orch_sm1","orch_sm2","orch_sm3")

png("png/orch_sar_cpu%d.png")
for (i in 1:length(data)) {
 frame <- data[[i]]
plot.ts(frame[frame[,2]==0,1],100-frame[frame[,2]==0,3],xlab="Timestamp",ylab="CPU_usage(%)",main=roles[i],type="l")
lines(frame[frame[,2]==0,1],100-frame[frame[,2]==1,3],col="red")
lines(frame[frame[,2]==0,1],100-frame[frame[,2]==2,3],col="blue")
lines(frame[frame[,2]==0,1],100-frame[frame[,2]==3,3],col="green")
}
dev.off()

