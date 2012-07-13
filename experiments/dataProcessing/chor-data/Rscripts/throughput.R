table <- read.table("throughput.log", header = FALSE, sep = " ")

lengths <- rle(as.character(table$V1))

png("throughput.png")
plot(strptime(lengths$values, "%H:%M:%S"),lengths$lengths)
dev.off()

mean(lengths$lengths)

sort(lengths$lengths)[round(length(lengths$lengths)*0.95)]


