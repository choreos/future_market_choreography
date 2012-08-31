timeouts <- 1:10
archs <- c("chor", "orch2", "orch")
exp <- read.csv("graphs.csv")

nrow <- nrow(unique(exp[,c("Arch", "Freq")]))
# result is matrix with failure counts for each timeout.
result <- data.frame(
  Arch = rep("", nrow),
  Freq = rep(NA, nrow),
  t1 = rep(NA, nrow),
  t2 = rep(NA, nrow),
  t3 = rep(NA, nrow),
  t4 = rep(NA, nrow),
  t5 = rep(NA, nrow),
  t6 = rep(NA, nrow),
  t7 = rep(NA, nrow),
  t8 = rep(NA, nrow),
  t9 = rep(NA, nrow),
  t10 = rep(NA, nrow),
  stringsAsFactors = FALSE)
i_res <- 1

for (arch in archs) {
  archData <- subset(exp, Arch == arch, select = -Arch)
  freqs <- unique(archData[,1])

  for (freq in freqs) {
    result[i_res, "Arch"] <- arch
    result[i_res, "Freq"] <- freq

    freqTimes <- subset(archData, Freq == freq, select = -Freq)
    total <- nrow(freqTimes)

    for (i in 1:length(timeouts)) {
      failures <- nrow(subset(freqTimes, Time >= timeouts[i]*1000))
      perc <- failures*100/total
      result[i_res, i + 2] <- floor(perc)
    }

    i_res <- i_res + 1
  }
}

# Begin definitions of each line to be plotted (var "line"), in "archs" order
colors <- c("red", "blue", "black")
ltys <- c("dashed", "twodash", "solid")
pchs <- c(2, 0, 1)
# End

# Plotting only one timeout so far. If more than one, there should be also
# files and subs arrays
timeouts <- 5:10
percs <- 1:10
for (timeout in timeouts) {
  lines <- matrix(nrow = length(archs), ncol = length(percs))
  for (a in 1:length(archs)) {
    arch <- archs[a];
    archData <- subset(result, result[,"Arch"] == arch)
    y <- array(dim = length(percs))
    for (x in 1:length(percs)) {
      perc <- percs[x]
      freqs <- subset(archData, archData[, timeout + 2] < perc, select = 2)
      freq <- max(freqs)
      y[x] <- freq
    }
    lines[a,] <- y
  }
  pdf(file=paste("graph", timeout, ".pdf"), height=4, width=6)
  par(xpd=TRUE, mar=par()$mar + c(0,0,0,3.5))
  plot(percs, lines[1,], type="o", col=colors[1], lty=ltys[1], pch=pchs[1],
      ylim=c(220,520), xlab="Timeout (%)", ylab="Requests/min",
      main="Maximum Request Rate", sub=paste(timeout, " ms"))
  lines(percs, lines[2,], type="o", col=colors[2], lty=ltys[2], pch=pchs[2])
  lines(percs, lines[3,], type="o", col=colors[3], lty=ltys[3], pch=pchs[3])
  legend(10.5, 505, archs, col=colors, pch=pchs, lty=ltys)
  par(xpd=FALSE, mar=c(5, 4, 4, 2) + 0.1)
  dev.off()
}
