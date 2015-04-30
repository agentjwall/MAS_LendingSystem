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

plotMonthsInRecession <- function(data, numRuns, experimentNum) {
  recessionData <- vector()
  for (i in 1:numRuns) {
    col_name <- paste("tpc", i, sep="_")
    recessionData[i] <- monthsInRecession(data[[col_name]])
  }
  xnum <- 1:numRuns
  bpLabel <- paste("Months in Recession per Run (Experiment ", experimentNum, ")", sep="")
  bp2 <- barplot(recessionData, names=xnum, main=bpLabel,
                 ylab="months", xlab="run", ylim=c(0,80), col=4)
  text(x = bp2, y = recessionData, label = recessionData, pos = 3, cex = 0.8, col = 4)
}

## execution section

## Experiment 1 section
e1_runNumbers <- getRunNumbers(e1_tpc)

for (run in e1_runNumbers) {
  indexes <- getIndexesByRun(e1_tpc, run)
  tpc <- e1_tpc$total.percent.change[indexes]
  tpc_name <- paste("tpc", run, sep="_")
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
plotMonthsInRecession(e1_tpc, length(e1_runNumbers), 1)


### Experiment 2 Section
e2_runNumbers <- getRunNumbers(e2_tpc)

for (run in e2_runNumbers) {
  indexes <- getIndexesByRun(e2_tpc, run)
  tpc <- e2_tpc$Total.Percent.Change[indexes]
  tpc_name <- paste("tpc", run, sep="_")
  e2_tpc[[tpc_name]] <- tpc
}

## 1, 2, 3, 4
plot(e2_tpc$tpc_1,type="l",col=1, xlim=c(0, 240), xlab="Tick", ylab="Total % Change in Economy",
     main="Experiment 2: Runs 1, 2, 3, and 4")
lines(e2_tpc$tpc_2,col=2)
lines(e2_tpc$tpc_3,col=3)
lines(e2_tpc$tpc_4,col=4)

## Months in Recession per experiment
plotMonthsInRecession(e2_tpc, length(e2_runNumbers), 2)


