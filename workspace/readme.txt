README
This project simulates the exchange of loans between consumers and bankers. A brief description of the interactions most pertinent to the simulation appears in this document. A more thorough discussion of agent design choices will appear in the final report.

To run this simulation:

Setup:
- Clone the repository
- Download Simphony Repast from http://repast.sourceforge.net/download.php
- Run Simphony and open the folder titled 'workspace'

Execution:
- Click the 'Run' button. A Repast window will appear. Within this window, press the power button to initialize the simulation, then the play or step button to either run the complete simulation or step through it tick by tick.
- Navigate to the parameters window if you would like to change the parameters that the simulation is run with.
- The pink circles are the consumer agents; the blue circles are the banker agents. The grid shows where agents are located spatially, but neighborhoods do not exactly correlate with grid locations - some agents are actually in multiple neighborhoods despite the fact that they can only appear in one location on the grid. Having agents in multiple neighborhoods increases the likelihood that spending habits and desires will spread among agents across the simulated economy. 

Paramters:
- Tick: Represents a month in the simulation
	- Years specifies how many years to simulate. Years * 20 = number of ticks the simulation will stop after
- Default Random Seed: Seed for the random number generators. Ignore this parameter for now.
- Cost of Living: the per-tick expenses for each consumer agent. This is also the minimum salary a consumer agent can earn per tick.
- Banker Count: the number of banker agents.
- Consumer Count: the number of consumer agents
- Max Income: the maximum income a consumer can earn per tick. 

Distribution Mean parameters - range from 0 to 1.
- Mean for Spending Distribution
- Mean Consumer Risk:
- Mean Bank Risk: A banker agent's risk level specifies the highest consumer risk level the bank is willing to accept from a consumer seeking a loan. The banker's risk is selected from a Normal distribution using this mean (see below for more details).
- Mean for Consmer Income Dist
- Mean Consumer Desire to Splurge: Affects how highly consumers will be influenced to splurge by the splurge decisions of other consumers
- Mean Starting Bank Assets: Affects the size of banks

Consumer income per tick is selected from a Normal distribution (see below) with cost of living as the min and max income as the max.

Random values: selected from a Normal distribution with the mean provided as a parameter and a standard deviation of 1/6 * (max - min). This is designed so that the probability of selecting a value greater than or less than the max/min values will approach zero. The mean provided for each of the parameters in the "distribution mean" section influences where the largest percentage of the Normally distributed values will come from. These parameters should range from 0 to 1. For example, if the provided mean is .75, the mid point of the Normal distribution will be located 3/4 of the way between the max and min values. The randomly selected values will still be cut off at the max and min provided values. 
This allows us to run experiments with higher concentrations of various types of agents.

Explanation of Charts:
Defaulted Bank Count: time-series aggregation of the number of banks that have defaulted
Default Info: Time series showing the total value of bank assets in the simulation, the total monetary value of bank assets which have defaulted, and the banks which have defaulted
Percent change in the economy: shows this value as defined in our project proposal

Our visualizations are not perfect and leave much to be desired; we hope to be able to improve these in order to perform our experiments. At this time we had decided to focus on our agent design.

Initial parameters were chosen in order to demonstrate a high number of bank failures. We found that a ratio of 2:3 cost of living to maximum income caused consumer agents to seek loans with a high frequency (because they didn't have the cash on hand to pay for their desired splurge purchases). We set the number of consumer agents very high in proportion to our banker agents in order to starkly show the banks failing under the high demand of consumers seeking loans. We set the initial mean for all distribution parameters to .5 in order to show how a standard Normal distribution centered between the minimum and maximum values would behave. 


Agent synchronization:
Each tick is composed of three steps: initial consumer actions, banker loan processing, and post-banker consumer actions. 
