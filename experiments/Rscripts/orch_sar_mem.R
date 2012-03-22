data <- list(read.table("data/orch/sar/broker/mem_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/registry/mem_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/shipper/mem_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm1/mem_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm2/mem_sar.out",head=FALSE,sep=" "), read.table("data/orch/sar/sm3/mem_sar.out",head=FALSE,sep=" ")) 

roles <- c("orch_broker","orch_registry","orch_shipper","orch_sm1","orch_sm2","orch_sm3")

png("png/orch_sar_mem%d.png")
for (i in 1:length(chor_mem)) {
 frame <- chor_mem[[i]]
 plot.ts(frame[,1],frame[,2]-frame[,3]-frame[,4],xlab="Timestamp",ylab="Mem_usage(kb)",main="Choreography",type="l")
}
dev.off()

