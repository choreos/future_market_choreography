chor_mem <- list(read.table("data/chor/sar/broker/mem_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/registry/mem_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/shipper/mem_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm1/mem_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm2/mem_sar.out",head=FALSE,sep=" "), read.table("data/chor/sar/sm3/mem_sar.out",head=FALSE,sep=" ")) 

roles = c("chor_broker","chor_registry","chor_shipper","chor_sm1","chor_sm2","chor_sm3")

png("png/chor_sar_mem%d.png")
for (i in 1:length(chor_mem)) {
 frame <- chor_mem[[i]]
 plot.ts(frame[,1],frame[,2]-frame[,3]-frame[,4],xlab="Timestamp",ylab="Mem_usage(kb)",main="Choreography",type="l")
}
dev.off()

