source("functions.R")

results <- read.csv("lg.csv")
# Removing Timestamp column
results <- subset(results, select = -Timestamp)

plot_clientsXportals(results, ylim=c(0,500), legpos=c(4.25, 500))
