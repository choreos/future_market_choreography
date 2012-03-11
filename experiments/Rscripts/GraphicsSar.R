customerchormem_sarout <- read.table("./customer/chor/mem_sar.out",head=FALSE,sep=" ")
customerchornet_sarout <- read.table("./customer/chor/net_sar.out",head=FALSE,sep=" ")
customerchorcpu_sarout <- read.table("./customer/chor/cpu_sar.out",head=FALSE,sep=" ")
customerorchmem_sarout <- read.table("./customer/orch/mem_sar.out",head=FALSE,sep=" ")
customerorchnet_sarout <- read.table("./customer/orch/net_sar.out",head=FALSE,sep=" ")
customerorchcpu_sarout <- read.table("./customer/orch/cpu_sar.out",head=FALSE,sep=" ")

png("customerorchcpu.png")
plot.ts(customerorchcpu_sarout[,1],customerorchcpu_sarout[,2],xlab="Timestamp",ylab="CPU_usage(%)",main="Orchestration",type="l")
dev.off()

png("customerchorcpu.png")
plot.ts(customerchorcpu_sarout[,1],customerchorcpu_sarout[,2],xlab="Timestamp",ylab="CPU_usage(%)",main="Choreography",type="l")
dev.off()

png("customerorchnet.png")
plot.ts(customerorchnet_sarout[,1],customerorchnet_sarout[,2],xlab="Timestamp",ylab="Net_usage(kB)",main="Orchestration",type="l",col="blue")
lines(customerorchnet_sarout[,1],customerorchnet_sarout[,3],col="red")
legend(4300, c("received","transmitted"), col=c("blue","red"), lty=1, lwd=3, box.lty=0)
dev.off()

png("customerchornet.png")
plot.ts(customerchornet_sarout[,1],customerchornet_sarout[,2],xlab="Timestamp",ylab="Net_usage(kB)",main="Choreography",type="l",col="blue")
lines(customerchornet_sarout[,1],customerchornet_sarout[,3],col="red")
legend(4300, c("received","transmitted"), col=c("blue","red"), lty=1, lwd=3, box.lty=0)
dev.off()


png("customerchormem.png")
plot.ts(customerchormem_sarout[,1],customerchormem_sarout[,2]-customerchormem_sarout[,3]-customerchormem_sarout[,4],xlab="Timestamp",ylab="Mem_usage",main="Choreography",type="l")
dev.off()

png("customerorchmem.png")
plot.ts(customerorchmem_sarout[,1],customerorchmem_sarout[,2]-customerorchmem_sarout[,3]-customerorchmem_sarout[,4],xlab="Timestamp",ylab="Mem_usage",main="Orchestration",type="l")
dev.off()


