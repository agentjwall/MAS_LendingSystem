e1_assets <- read.csv("Assets Output E1 600a.csv")
e2_assets <- read.csv("Assets Output E2 600a.csv")
e1_tpc <- read.csv("TPC Output E1 600agents.csv")
e2_tpc <- read.csv("TPC Output E2 600a.csv")
e2_tpcUpdate <- read.csv("TPC E2 Update.csv")
e3_tpc <- read.csv("TPC Output E3 600a.csv")
e4_tpc <- read.csv("TPC E4.csv")

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

plotMonthsInRecession <- function(data, numRuns, experimentNum, ylimMax = 100) {
  recessionData <- vector()
  for (i in 1:numRuns) {
    col_name <- paste("tpc", i, sep="_")
    recessionData[i] <- monthsInRecession(data[[col_name]])
  }
  xnum <- 1:numRuns
  bpLabel <- paste("Months in Recession per Run (Experiment ", experimentNum, ")", sep="")
  bp2 <- barplot(recessionData, names=xnum, main=bpLabel,
                 ylab="months", xlab="run", ylim=c(0, ylimMax), col=4)
  text(x = bp2, y = recessionData, label = recessionData, pos = 3, cex = 0.8, col = 4)
}

## execution section

## Experiment 1 section
e1_runNumbers <- getRunNumbers(e1_tpc)
e1_tpc_frame <- as.data.frame(matrix(NA, nrow=240))
for (run in e1_runNumbers) {
  indexes <- getIndexesByRun(e1_tpc, run)
  tpc <- e1_tpc$TotalPercentChange[indexes]
  tpc_name <- paste("tpc", run, sep="_")
  e1_tpc_frame[[tpc_name]] <- tpc
}

## Graph 1, 5, 9, 2, 6, 10
plot(e1_tpc_frame$tpc_1,type="l",col=1, xlim=c(0, 240), xlab="Tick", ylab="Total % Change in Economy",
     main="Experiment 1: Runs 1, 2, 5, 6, 9, and 10")
lines(e1_tpc_frame$tpc_5,col=2)
lines(e1_tpc_frame$tpc_9, col=3)
lines(e1_tpc_frame$tpc_2, col=4)
lines(e1_tpc_frame$tpc_6, col=5)
lines(e1_tpc_frame$tpc_12, col=2)

## Graph 1, 5, and 9
plot(e1_tpc_frame$tpc_1,type="l",col=1, xlim=c(0, 240), xlab="Tick", ylab="Total % Change in Economy",
     main="Experiment 1: Runs 1, 5, and 9")
lines(e1_tpc_frame$tpc_5,col=2)
lines(e1_tpc_frame$tpc_9, col=4)
monthsInRecession(e1_tpc_frame$tpc_1)

## Months in Recession per experiment
plotMonthsInRecession(e1_tpc_frame, length(e1_runNumbers), 1, 80)


### Experiment 2 Section
e2Update_runNumbers <- getRunNumbers(e2_tpcUpdate)
e2_tpcUpdate_frame <- as.data.frame(matrix(NA, nrow=240))
for (run in e2Update_runNumbers) {
  indexes <- getIndexesByRun(e2_tpcUpdate, run)
  tpc <- e2_tpcUpdate$TotalPercentChange[indexes]
  tpc_name <- paste("tpc", run, sep="_")
  e2_tpcUpdate_frame[[tpc_name]] <- tpc
}

## 1, 2, 3, 4
plot(e2_tpc_frame$tpc_1,type="l",col=1, xlim=c(0, 240), xlab="Tick", ylab="Total % Change in Economy",
     main="Experiment 2: Runs 1, 2, 3, and 4")
lines(e2_tpc_frame$tpc_2,col=2)
lines(e2_tpc_frame$tpc_3,col=3)
lines(e2_tpc_frame$tpc_4,col=4)

## Months in Recession per experiment
plotMonthsInRecession(e2_tpcUpdate_frame, length(e2Update_runNumbers), 2, 80)


### Experiment 3 Section
e3_runNumbers <- getRunNumbers(e3_tpc)
e3_tpc_frame <- as.data.frame(matrix(NA, nrow=240))

for (run in e3_runNumbers) {
  indexes <- getIndexesByRun(e3_tpc, run)
  tpc <- e3_tpc$TotalPercentChange[indexes]
  tpc_name <- paste("tpc", run, sep="_")
  e3_tpc_frame[[tpc_name]] <- tpc[1:240]
  
}
plot(e3_tpc_frame$tpc_3,type="l",col=1, xlim=c(0, 240), xlab="Tick", ylab="Total % Change in Economy",
     main="Baseline Environment")
plotMonthsInRecession(e3_tpc_frame, length(e3_runNumbers), 3, 80)


### Experiment 4 Section
e4_runNumbers <- getRunNumbers(e4_tpc)
e4_tpc_frame <- as.data.frame(matrix(NA, nrow=240))
for (run in e4_runNumbers) {
  indexes <- getIndexesByRun(e4_tpc, run)
  tpc <- e4_tpc$TotalPercentChange[indexes]
  tpc_name <- paste("tpc", run, sep="_")
  e4_tpc_frame[[tpc_name]] <- tpc[1:240]
}

plotMonthsInRecession(e4_tpc_frame, length(e4_runNumbers), 4, 80)

