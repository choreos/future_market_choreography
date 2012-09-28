# Archs are hard coded to control plot params
archs <- c("chor", "orch")
results <- read.csv("lg.csv")
# Removing Timestamp column
results <- subset(results, select = -Timestamp)
# Detecting how many portals were used
portals <- 1:max(results $ Portals)

# How many repetitions were made (same order as archs)
# Experiment should be not be incomplete for an arch
nrows = array(dim = length(archs))
for (i in 1:length(archs)) {
  archData <- subset(results, Arch == archs[i])
  nrows[i] <- nrow(archData)/max(portals)
}

means <- matrix(nrow = length(archs), ncol = length(portals))
sds   <- matrix(nrow = length(archs), ncol = length(portals))
for (a in 1:length(archs)) {
  archData <- subset(results, Arch == archs[a], select = -Arch)
  for (p in portals) {
    clients <- subset(archData, Portals == p, select = -Portals)
    means[a, p] <- sapply(clients, mean)
    sds[a, p]   <- sapply(clients, sd)
  }
}

# Begin definitions of each line to be plotted (var "line"), in "archs" order
colors <- c("red", "blue")
ltys <- c("solid", "dashed")
pchs <- c(2, 0)
# End

pdf("clientsXreplications.pdf", height=4, width=6.5)
par(xpd=TRUE, mar=par()$mar + c(0,0,0,4.25))
plot(portals, means[1,], type="o", col=colors[1], lty=ltys[1], pch=pchs[1],
    ylim=c(0,600), xlab="Portal replications", ylab="Clients",
    main="Maximum Simultaneous Clients", xaxt="no")
lines(portals, means[2,], type="o", col=colors[2], lty=ltys[2], pch=pchs[2])
axis(1, at=portals)
legend(4.25, 625, archs, col=colors, pch=pchs, lty=ltys)

# function for plotting error bars
# http://monkeysuncle.stanford.edu/?p=485
error.bar <- function(x, y, upper, color, lower=upper, length=0.1, ...){
  if(length(x) != length(y) | length(y) !=length(lower) | length(lower) != length(upper))
  stop("vectors must be same length")
  arrows(x,y+upper, x, y-lower, angle=90, code=3, length=length, col=color, ...)
}

# Error bars CI = 95% (normal)
for (a in 1:length(archs)) {
  error.bar(portals, means[a,], 1.96*sds[a,]/sqrt(nrows[1]), colors[a])
}
par(xpd=FALSE, mar=c(5, 4, 4, 2) + 0.1)
dev.off()
