package easyMahout.GUI.recommender.builder;

import easyMahout.GUI.recommender.FactorizerRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;

public class ShowCommandlineBuilder {
	public static final StringBuilder buildCommandline() {
		if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMBASED_DISTRIBUTED)) {

			String[] argsItemBased = JobBuilder.buildRecommenderJob();
			if (argsItemBased != null) {

				StringBuilder textBuilder = new StringBuilder();
				textBuilder.append("#!/bin/bash")
						.append("\n\n")
						.append("MAHOUT=\"../../bin/mahout\"")
						.append("\n\n")
						.append("$MAHOUT recommenditembased")
						.append("\n\t")
						.append(argsItemBased[0] + " " + argsItemBased[1])
						.append("\n\t")
						.append(argsItemBased[2] + " " + argsItemBased[3])
						.append("\n\t")
						.append(argsItemBased[4] + " " + argsItemBased[5])
						.append("\n\t")
						.append(argsItemBased[6] + " " + argsItemBased[7])
						.append("\n\t")
						.append(argsItemBased[8] + " " + argsItemBased[9])
						.append("\n\t")
						.append(argsItemBased[10] + " " + argsItemBased[11])
						.append("\n\t")
						.append(argsItemBased[12] + " " + argsItemBased[13])
						.append("\n\t")
						.append(argsItemBased[14] + " " + argsItemBased[15]);

				return textBuilder;

			} else {
				new StringBuilder();
			}
		} else if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.FACTORIZED_RECOMMENDER)) {

			String[] argsSplitdata = JobBuilder.buildSplitDatasetJob();
			if (argsSplitdata != null) {
				String[] argsFactorization = JobBuilder.buildFactorizerJob();
				String[] argsEvaluator = JobBuilder.buildEvaluatorJob();
				String[] argsRecommender = JobBuilder.buildRecommenderJob();

				StringBuilder textBuilder = new StringBuilder();
				textBuilder.append("#!/bin/bash")
						.append("\n\n")
						.append("MAHOUT=\"../../bin/mahout\"")
						.append("\n\n")
						.append("$MAHOUT splitDataset")
						.append("\n\t")
						.append(argsSplitdata[0] + " " + argsSplitdata[1])
						.append("\n\t")
						.append(argsSplitdata[2] + " " + argsSplitdata[3])
						.append("\n\t")
						.append(argsSplitdata[4] + " " + argsSplitdata[5])
						.append("\n\t")
						.append(argsSplitdata[6] + " " + argsSplitdata[7])
						.append("\n\t")
						.append(argsSplitdata[8] + " " + argsSplitdata[9])
						.append("\n\n")
						.append("$MAHOUT parallelALS")
						.append("\n\t")
						.append(argsFactorization[0] + " " + argsFactorization[1])
						.append("\n\t")
						.append(argsFactorization[2] + " " + argsFactorization[3])
						.append("\n\t")
						.append(argsFactorization[4] + " " + argsFactorization[5])
						.append("\n\t")
						.append(argsFactorization[6] + " " + argsFactorization[7])
						.append("\n\t")
						.append(argsFactorization[8] + " " + argsFactorization[9])
						.append("\n\t")
						.append(argsFactorization[10] + " " + argsFactorization[11])
						.append("\n\t")
						.append(argsFactorization[12] + " " + argsFactorization[13]);

				if (FactorizerRecommenderPanel.getSelectedFunction().equals(Constants.RecommFactorizer.SVD)) {
					textBuilder.append("\n\t")
							.append(argsFactorization[14] + " " + argsFactorization[15])
							.append("\n\t")
							.append(argsFactorization[16] + " " + argsFactorization[17]);
				}

				textBuilder.append("\n\n")
						.append("$MAHOUT evaluateFactorization")
						.append("\n\t")
						.append(argsEvaluator[0] + " " + argsEvaluator[1])
						.append("\n\t")
						.append(argsEvaluator[2] + " " + argsEvaluator[3])
						.append("\n\t")
						.append(argsEvaluator[4] + " " + argsEvaluator[5])
						.append("\n\t")
						.append(argsEvaluator[6] + " " + argsEvaluator[7])
						.append("\n\t")
						.append(argsEvaluator[8] + " " + argsEvaluator[9])
						.append("\n\n")
						.append("$MAHOUT recommendfactorized")
						.append("\n\t")
						.append(argsRecommender[0] + " " + argsRecommender[1])
						.append("\n\t")
						.append(argsRecommender[2] + " " + argsRecommender[3])
						.append("\n\t")
						.append(argsRecommender[4] + " " + argsRecommender[5])
						.append("\n\t")
						.append(argsRecommender[6] + " " + argsRecommender[7])
						.append("\n\t")
						.append(argsRecommender[8] + " " + argsRecommender[9])
						.append("\n\t")
						.append(argsRecommender[10] + " " + argsRecommender[11])
						.append("\n\t")
						.append(argsRecommender[12] + " " + argsRecommender[13])
						.append("\n\n");

				return textBuilder;
			} else {
				return new StringBuilder();
			}
		}
		return new StringBuilder();
	}
}
