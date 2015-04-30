setwd("/Users//clottman//Documents//Code//MAS_LendingSystem");

e1_assets <- read.csv("Assets Output E1 600a.csv")
e2_assets <- read.csv("Assets Output E2 600a.csv")
e1_tpc <- read.csv("TPC Output E1 600agents.csv")
e2_tpc <- read.csv("TPC Output E2 600a.csv")

## helper function section

# get all the unique values in the run column
getRunNumbers <- function(data) {
  unique(unlist(data$run, use.names = FALSE))
}

# gets the indexes of the rows containing data for a given run
# example: getRunNumbers(2) will return all the indexes where the run column contains 2
getIndexesByRun <- function(list, desiredRun) {
  which(list$run == desiredRun)
}

                      
## execution section
runNumbers <- getRunNumbers(e1_tpc)

for (run in runNumbers) {
  indexes <- getIndexesByRun(e1_assets, run)
  tpc <- e1_tpc$total.percent.change[indexes]
  tpc_name <- paste("tpc", run, sep="_")
  run_name <- paste("run", run, sep="_")
  e1_tpc[[tpc_name]] <- tpc
}

## Graph 1, 5, 9, 2, 6, 10
plot(e1_tpc$tpc_1,type="l",col=1, xlim=c(0, 240), xlab="Tick", ylab="Total % Change in Economy",
     main="Experiment 1: Runs 1, 2, 5, 6, 9, and 10")
lines(e1_tpc_force, col=2)
lines(e1_tpc$tpc_5,col=2)
lines(e1_tpc$tpc_9, col=3)
lines(e1_tpc$tpc_2, col=4)
lines(e1_tpc$tpc_6, col=5)
lines(e1_tpc$tpc_12, col=2)

## Graph 1, 5, and 9
plot(e1_tpc$tpc_1,type="l",col=1, xlim=c(0, 240), xlab="Tick", ylab="Total % Change in Economy",
     main="Experiment 1: Runs 1, 5, and 9")
lines(e1_tpc$tpc_5,col=2)
lines(e1_tpc$tpc_9, col=4)

