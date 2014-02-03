package easyMahout.GUI.recommender.builder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.RowSimilarityJob;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.DataModelRecommenderPanel;
import easyMahout.GUI.recommender.FactorizerRecommenderPanel;
import easyMahout.GUI.recommender.JobRecommenderPanel;
import easyMahout.GUI.recommender.SimilarityRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.GUI.recommender.inputDialogs.ALSWRFactorizerInputDialog;
import easyMahout.GUI.recommender.inputDialogs.FactorizerInputDialog;
import easyMahout.GUI.recommender.inputDialogs.SVDFactorizerInputDialog;
import easyMahout.utils.Constants;

public class JobBuilder {

	private static final int ARGS_ITEMBASED_SIZE = 20;

	private static final int ARGS_FACTORIZEDBASED_SIZE = 18;

	private static final Logger log = Logger.getLogger(JobBuilder.class);

	public static final String[] buildRecommenderJob() {
		if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMBASED_DISTRIBUTED)) {

			String inputPath = DataModelRecommenderPanel.getInputPath();
			String outputPath = DataModelRecommenderPanel.getOutputPath();
			String numRecommendations = JobRecommenderPanel.getNumRecommendations();
			String similarityClassname = SimilarityRecommenderPanel.getDistributedSimilarity();
			String maxSimilaritiesPerItem = SimilarityRecommenderPanel.getMaxSimilarities();
			String maxPrefsPerUser = SimilarityRecommenderPanel.getMaxPreferences();
			String minPrefsPerUser = SimilarityRecommenderPanel.getMinPreferences();
			String maxPrefPerUserInItemSimilarity = SimilarityRecommenderPanel.getMaxPrefsInItemSimilarity();
			String booleanData = DataModelRecommenderPanel.getBooleanPrefs();
			String threshold = SimilarityRecommenderPanel.getThreshold();

			String[] args = new String[ARGS_ITEMBASED_SIZE];

			if (StringUtils.isBlank(inputPath)) {
				MainGUI.writeResult("Input folder is empty, please choose one before run the recommender.", Constants.Log.ERROR);
				return null;
			} else if (StringUtils.isBlank(outputPath)) {
				MainGUI.writeResult("Output folder is empty, please choose one before run the recommender.", Constants.Log.ERROR);
				return null;
			} else {
				int p = 0;
				args[p] = "--input";
				args[++p] = inputPath;
				args[++p] = "--output";
				args[++p] = outputPath;
				args[++p] = "--numRecommendations";
				args[++p] = StringUtils.isBlank(numRecommendations) ? "10" : numRecommendations;
				args[++p] = "--similarityClassname";
				args[++p] = similarityClassname;
				args[++p] = "--maxSimilaritiesPerItem";
				args[++p] = StringUtils.isBlank(maxSimilaritiesPerItem) ? "100" : maxSimilaritiesPerItem;
				args[++p] = "--maxPrefsPerUser";
				args[++p] = StringUtils.isBlank(maxPrefsPerUser) ? "500" : maxPrefsPerUser;
				args[++p] = "--minPrefsPerUser";
				args[++p] = StringUtils.isBlank(minPrefsPerUser) ? "1" : minPrefsPerUser;
				args[++p] = "--maxPrefPerUserInItemSimilarity";
				args[++p] = StringUtils.isBlank(maxPrefPerUserInItemSimilarity) ? "1000" : maxPrefPerUserInItemSimilarity;
				args[++p] = "--booleanData";
				args[++p] = StringUtils.isBlank(booleanData) ? String.valueOf(Boolean.FALSE) : booleanData;
				// TODO: threshold can be wrong in this way "4.9E-324"
				args[++p] = "--threshold";
				args[++p] = StringUtils.isBlank(threshold) ? String.valueOf(RowSimilarityJob.NO_THRESHOLD) : threshold;

				for (int i = 0; i < ARGS_ITEMBASED_SIZE; i++) {
					log.info(args[i].toString());
				}
				return args;
			}

			// } else if
			// (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMSIMILARITY))
			// {
			// // TODO
			// return null;
		} else {

			String inputPath = DataModelRecommenderPanel.getInputPath();
			String outputPath = DataModelRecommenderPanel.getOutputPath();
			//String numRecommendations = JobRecommenderPanel.getNumRecommendations();
			String numFeatures;
			String numIterations;
			String lambda;
			String numThreadsPerSolver;
			String implicitFeedback;
			String alpha = null;

			if (FactorizerRecommenderPanel.getSelectedFunction().equals(Constants.RecommFactorizer.ALSWR)) {
				numFeatures = ALSWRFactorizerInputDialog.getNoFeaturesArg();
				numIterations = ALSWRFactorizerInputDialog.getNoIterationsArg();
				lambda = ALSWRFactorizerInputDialog.getLambdaArg();
				numThreadsPerSolver = ALSWRFactorizerInputDialog.getNoTrainingThreadsArg();
				implicitFeedback = String.valueOf(false);
			} else { // SVD
				numFeatures = SVDFactorizerInputDialog.getNoFeaturesArg();
				numIterations = SVDFactorizerInputDialog.getNoIterationsArg();
				lambda = SVDFactorizerInputDialog.getLambdaArg();
				numThreadsPerSolver = SVDFactorizerInputDialog.getNoTrainingThreadsArg();
				implicitFeedback = String.valueOf(false);
				alpha = SVDFactorizerInputDialog.getAlphaArg();
			}

			String[] args = new String[ARGS_FACTORIZEDBASED_SIZE];

			if (StringUtils.isBlank(inputPath)) {
				MainGUI.writeResult("Input folder is empty, please choose one before run the recommender.", Constants.Log.ERROR);
				return null;
			} else if (StringUtils.isBlank(outputPath)) {
				MainGUI.writeResult("Output folder is empty, please choose one before run the recommender.", Constants.Log.ERROR);
				return null;
			} else {
				
				int p = 0;
				args[p] = "--input";
				args[++p] = inputPath;
				args[++p] = "--output";
				args[++p] = outputPath;
//				args[++p] = "--numRecommendations";
//				args[++p] = StringUtils.isBlank(numRecommendations) ? "10" : numRecommendations;
				args[++p] = "--numFeatures";
				args[++p] = numFeatures;
				args[++p] = "--numIterations";
				args[++p] = numIterations;
				args[++p] = "--lambda";
				args[++p] = lambda;
				args[++p] = "--numThreadsPerSolver";
				args[++p] = StringUtils.isBlank(numThreadsPerSolver) ? "1" : numThreadsPerSolver;
				args[++p] = "--implicitFeedback";
				args[++p] = StringUtils.isBlank(implicitFeedback) ? String.valueOf(Boolean.FALSE) : String.valueOf(Boolean.TRUE);
				args[++p] = "--alpha";
				args[++p] = StringUtils.isBlank(alpha) ? String.valueOf(40) : alpha;

				for (int i = 0; i < ARGS_FACTORIZEDBASED_SIZE; i++) {
					log.info(args[i].toString());
				}
				return args;
			}
		}
	}

}
