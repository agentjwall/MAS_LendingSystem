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

monthsInRecession <- function(list) {
  recessionConstant <- 3
  
  monthsDecreasing <- 0
  monthsInRecession <- 0
  stopAt <- length(list) - 1
  for ( i in 2:stopAt) {
    # is decreasing?
    if (list[i-1] > list[i]) {
      monthsDecreasing <- monthsDecreasing + 1
    } else {
      monthsDecreasing <- 0
    }
    
    # update monthsInRec. if in a recession
    if (monthsDecreasing != 0) {
      if (monthsDecreasing == 3) {
        monthsInRecession <- monthsInRecession + 3
      } else if (monthsDecreasing > 3) {
        monthsInRecession <- monthsInRecession + 1
      }
    }
  }
  monthsInRecession
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
monthsInRecession(e1_tpc$tpc_1)

## Months in Recession per experiment
recessionData <- vector()
for (i in 1:12) {
  col_name <- paste("tpc", i, sep="_")
  recessionData[i] <- monthsInRecession(e1_tpc[[col_name]])
}
xnum <- c(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
barplot(recessionData, names=xnum, main="Months In Recession per Experiment",
        ylab="months", xlab="experiment", col=4)