source('../functions.R')

csv_archs <- c('orch', 'chor')
leg_arch_names <- c('Orch', 'Chor')
colors <- c('red', 'blue')
cex <- 0.8
lwd <- 2
ylim <- c(0,20)
xmax <- -1
ymax <- -1

# Global variables
plot_is_run <- FALSE
leg_colors <- NULL
leg_ltys <- NULL
leg_lwds <- NULL
leg_names <- NULL
leg_pchs <- NULL
leg_pos <- NULL

line_name <- function(csv_arch, num_portals) {
  leg_arch_name <- leg_arch_names[which(csv_archs == csv_arch)]
  name <- paste(leg_arch_name, num_portals)
  if (num_portals > 1) {
    name <- paste(name, 'portals')
  } else {
    name <- paste(name, 'portal')
  }

  return(name)
}

plot_line <- function(arch, portals, color, lty, x, means, ics, type, pch) {
  if (xmax > 0) {
    x <- x[x <= xmax]
    range <- 1:length(x)
    means <- means[range]
    ics <- ics[range]
  }

  if (ymax > 0) {
    means <- means[means <= ymax]
    range <- 1:length(means)
    x <- x[range]
    ics <- ics[range]
  }

  plot_setup(x)

  lines(x, means, type=type, col=color, lty=lty, pch=pch, cex=cex, lwd=lwd)
  #error.bar(x, means, ics, "black")
  leg_colors <<- c(leg_colors, color)
  leg_ltys <<- c(leg_ltys, lty)
  leg_lwds <<- c(leg_lwds, lwd)
  leg_names  <<- c(leg_names, line_name(arch, portals))
  leg_pchs <<- c(leg_pchs, pch)
}

plot_setup <- function(x) {
  if (!plot_is_run) {
    plot(x, x, type='n', xaxt='no', ylim=ylim, xlab='Simultaneous Clients', ylab='Service time (sec)')
    axis(1,x)
    leg_pos <<- c(275/256*max(x), ylim[2])
    plot_is_run <<- TRUE
  }
}

plot_arch_portal <- function(arch, portals, lty, type='l', pch=-1) {
  df <- subset(df, Arch == arch & Portals == portals, select = c("Clients", "Time"))

  all_clients <- unique(df$Clients)
  all_clients <- as.numeric(as.character(all_clients))

  means <- array(dim=length(all_clients))
  ics <- array(dim=length(all_clients))

  for (c in 1:length(all_clients)) {
    clients <- all_clients[c]
    times <- subset(df, Clients == clients, select = Time)
    times <- as.numeric(as.character(times$Time))/1000
    means[c] <- mean(times)
    #ics[c] <- sd(times)*1.96/sqrt(length(times))
  }

  color <- colors[which(csv_archs == arch)]
  plot_line(arch, portals, color, lty, all_clients, means, ics, type, pch)
  cat(paste('Plotted portal replications =', portals, '\n'))
}

plot_start <- function() {
  pdf(file, height=4, width=7.5)
  par(xpd=TRUE, mar=par()$mar + c(0,0,0,8))

  if (ymax > 0) {
    plot_setup(x_axis)
  }
}

plot_end <- function() {
  legend(leg_pos[1], leg_pos[2], leg_names, col=leg_colors, lwd=leg_lwds, lty=leg_ltys, pch=leg_pchs)

  par(xpd=FALSE, mar=c(5, 4, 4, 2) + 0.1)
  dev.off()
}

# df = Arch,Portals,Clients,Execution,Time[,Timestamp]
#df <- read.csv('lg.csv')
