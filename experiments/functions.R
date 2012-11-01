# function for plotting error bars
# http://monkeysuncle.stanford.edu/?p=485
error.bar <- function(x, y, upper, color, lower=upper, length=0.1, ...){
  if(length(x) != length(y) | length(y) !=length(lower) | length(lower) != length(upper))
  stop("vectors must be same length")
  arrows(x,y+upper, x, y-lower, angle=90, code=3, length=length, col=color, ...)
}

# Progress bar. close(pb) in the end!
# pb <- txtProgressBar(style=3, max=x)
pbCurrent <- 0
progress1 <- function() {
  pbCurrent <<- pbCurrent + 1
  setTxtProgressBar(pb, pbCurrent)
}

# Begin parallel functions
library(multicore)
forks <- multicore:::detectCores() - 2
children <- list()
i_children <- 0

# TODO how to use 'function_name' and its 'args'
add_parallel_job <- function(function_name, args) {
  if (full_of_jobs()) {
    barrier()
  }
  i_children <<- i_children + 1
  child <- parallel(function_name(args))
  children[[i_children]] <<- child
}

full_of_jobs <- function() {
  return (i_children == forks)
}

barrier <- function() {
  collect(children)
  children <<- list()
  i_children <<- 0
}
# End of parallel functions
