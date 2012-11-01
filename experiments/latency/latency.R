source('../functions.R')

csv_archs <- c('orch', 'chor')
file <- 'output.pdf'
leg_arch_names <- c('Orch', 'Chor')
colors <- c('red', 'blue')
line_type <- 'l'
ylim <- c(0,20)

xmax <- -1
#xmax <- 250

ymax <- -1
#ymax <- 20
#x_axis <- seq(25,550,25)

# Global variables
plot_is_run <- FALSE
legpos <- NULL
leg_line_names <- NULL
leg_line_colors <- NULL
leg_line_ltys <- NULL

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

plot_line <- function(arch, portals, color, lty, x, means, ics) {
  if (xmax > 0) {
    x <- x[x <= xmax]
    range <- 1:length(x)
    means <- means[,range]
    ics <- ics[,range]
  }

  if (ymax > 0) {
    means <- means[means <= ymax]
    range <- 1:length(means)
    x <- x[range]
    ics <- ics[range]
  }

  plot_setup(x)

  lines(x, means, type=line_type, col=color, lty=lty)
  #error.bar(x, means, ics, "black")
  leg_line_names  <<- c(leg_line_names, line_name(arch, portals))
  leg_line_colors <<- c(leg_line_colors, color)
  leg_line_ltys <<- c(leg_line_ltys, lty)
}

plot_setup <- function(x) {
  if (!plot_is_run) {
    plot(x, x, type='n', xaxt='no', ylim=ylim, xlab='Simultaneous Clients', ylab='Service time (sec)')
    axis(1,x)
    legpos <<- c(275/256*max(x), ylim[2])
    plot_is_run <<- TRUE
  }
}

plot_arch_portal <- function(arch, portals, lty) {
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
  plot_line(arch, portals, color, lty, all_clients, means, ics)
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
  legend(legpos[1], legpos[2], leg_line_names, col=leg_line_colors, lty=leg_line_ltys)

  par(xpd=FALSE, mar=c(5, 4, 4, 2) + 0.1)
  dev.off()
}

# df = Arch,Portals,Clients,Execution,Time[,Timestamp]
#df <- read.csv('lg.csv')

plot_start()
plot_arch_portal('orch', 1, 'dotted')
plot_arch_portal('orch', 2, 'dotdash')
plot_arch_portal('orch', 3, 'dashed')
plot_arch_portal('orch', 4, 'solid')
plot_arch_portal('chor', 1, 'solid')
plot_end()

