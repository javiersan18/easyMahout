#!/bin/bash

MAHOUT=./bin/mahout

cd mahout-distribution-0.8/

$MAHOUT splitDataset \
	--input /home/javi/data \
	--output /home/javi/output31111/dataset \
	--trainingPercentage 0.9 \
	--probePercentage 0.1 \
	--tempDir /home/javi/output31111/dataset/tmp

$MAHOUT parallelALS \
	--input /home/javi/output31111/dataset/trainingSet/ \
	--output /home/javi/output31111/als/out/ \
	--tempDir /home/javi/output31111/als/tmp \
	--numFeatures 10 \
	--numIterations 5 \
	--lambda 0.065 \
	--numThreadsPerSolver 1 \

$MAHOUT evaluateFactorization \
	--input /home/javi/output31111/dataset/probeSet/ \
	--output /home/javi/output31111/als/rmse/ \
	--tempDir /home/javi/output31111/als/tmp \
	--userFeatures /home/javi/output31111/als/out/U/ \
	--itemFeatures /home/javi/output31111/als/out/M/ \

$MAHOUT recommendfactorized \
	--input /home/javi/output31111/als/out/userRatings/ \
	--output /home/javi/output31111/recommendations \
	--numRecommendations 10 \
	--userFeatures /home/javi/output31111/als/out/U/ \
	--itemFeatures /home/javi/output31111/als/out/M/ \
	--maxRating 5 \
	--numThreads 1
