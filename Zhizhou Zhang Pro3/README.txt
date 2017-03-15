Zhizhou Zhang
CSC242
Project 3

I did not collaborate on this project and all work is my own.

Files:
	README.txt  --this document
	WRITEUP.pdf --formal summary of the projects
	exact.jar   --exact inference algorithm
	RS.jar      --part 1 of approximate inference, reject sampling
	LW.jar      --part 2 of approximate inference, Likelihood Weighting
	code.zip    --zip file of source code

	inside of code.zip:
		most of the code I directly use what Professor Ferguson uploaded,
		what I change is in bn.inference folder:
		1. MyBNInferencer.java          --exact inference algorithm
		2. MyBNRejectionSampler.java    --reject sampling approximate
		3. MyBNLikelihoodWeighting.java --likelihood sampling approximate

How to run:
	1. I hope you can run .jar directly in the terminal, like:
	"java -jar exact.jar aima-alarm.xml B J true M true"
	means to run exact inference algorithm to query B, with J and M are true
	which follows the pattern:
	java -jar exact.jar (name of network file) (query variable) (list of evidence variables and the values)
	
	the approximate inferece is like:
	"java -jar RS.jar 1000 aima-alarm.xml B J true M true"
	means to run Reject Sampling approximate algorithm 1000 times in network alarm, query variable B with evidence variable J and M are true.
	the pattern is:
	java -jar RS.jar(or LW.jar) (time of trial) (name of network file) (query variable) (list of evidence variables and the values)

	please keep the network files and the inference jar at the same folder

	2. if you can't run jar, you can run Eclipse with import the source code from code.zip,
	run with configuration and follows above, like in exact inference do aima-alarm.xml B J true M true
	inside of bn.inference folder, you can run it correspondingly:
		1. MyBNInferencer.java          --exact inference algorithm
		2. MyBNRejectionSampler.java    --reject sampling approximate
		3. MyBNLikelihoodWeighting.java --likelihood sampling approximate
	Also please keep the network files in the root of project folder

Extra credit:
	I finish two algorithm, both Reject Sampling and Likelihood Weighting in part II

Bugs:
	none

Resources:
	code from Professor Ferguson.