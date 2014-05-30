package easyMahout.GUI.recommender.builder;

import java.io.File;

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
import easyMahout.GUI.recommender.inputDialogs.SVDFactorizerInputDialog;
import easyMahout.utils.Constants;

public class JobBuilder {

	private static final Logger log = Logger.getLogger(JobBuilder.class);

	private static final int ARGS_ITEMBASED_SIZE = 20;

	private static final int ARGS_FACTORIZEDBASED_SIZE = 14;

	private static final int ARGS_SPLIDATA_SIZE = 10;

	private static final int ARGS_ALSFACTORIZATION_SIZE = 14;

	private static final int ARGS_EVALUATOR_SIZE = 10;

	private static final char slash = File.separatorChar;

	private static final int ARGS_SVDFACTORIZATION_SIZE = 18;

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
				args[++p] = "--numRecommendations";
				args[++p] = StringUtils.isBlank(numRecommendations) ? "10" : numRecommendations;
				args[++p] = "--output";
				args[++p] = outputPath;
				args[++p] = "--similarityClassname";
				args[++p] = similarityClassname;
				args[++p] = "--maxSimilaritiesPerItem";
				args[++p] = StringUtils.isBlank(maxSimilaritiesPerItem) ? "100" : maxSimilaritiesPerItem;
				args[++p] = "--maxPrefsPerUser";
				args[++p] = StringUtils.isBlank(maxPrefsPerUser) ? "500" : maxPrefsPerUser;
				args[++p] = "--minPrefsPerUser";
				args[++p] = StringUtils.isBlank(minPrefsPerUser) ? "1" : minPrefsPerUser;
				args[++p] = "--maxPrefsPerUserInItemSimilarity";
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
		} else {
			String inputPath = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "out" + slash + "userRatings" + slash;
			String outputPath = DataModelRecommenderPanel.getOutputPath() + slash + "recommendations";
			String userFeatures = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "out" + slash + "U" + slash;
			String itemFeatures = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "out" + slash + "M" + slash;
			String numRecommendations = JobRecommenderPanel.getNumRecommendations();

			String[] args = new String[ARGS_FACTORIZEDBASED_SIZE];

			int p = 0;
			args[p] = "--input";
			args[++p] = inputPath;
			args[++p] = "--output";
			args[++p] = outputPath;
			args[++p] = "--numRecommendations";
			args[++p] = StringUtils.isBlank(numRecommendations) ? "10" : numRecommendations;
			args[++p] = "--userFeatures";
			args[++p] = userFeatures;
			args[++p] = "--itemFeatures";
			args[++p] = itemFeatures;
			args[++p] = "--maxRating";
			args[++p] = "5";
			args[++p] = "--numThreads";
			args[++p] = "1";

			for (int i = 0; i < ARGS_FACTORIZEDBASED_SIZE; i++) {
				log.info(args[i].toString());
			}
			return args;
		}
	}

	public static final String[] buildSplitDatasetJob() {
		String inputPath = DataModelRecommenderPanel.getInputPath();
		String outputPath = DataModelRecommenderPanel.getOutputPath() + slash + "dataset";

		if (StringUtils.isBlank(inputPath)) {
			MainGUI.writeResult("Input folder is empty, please choose one before run the recommender.", Constants.Log.ERROR);
			return null;
		} else if (StringUtils.isBlank(outputPath)) {
			MainGUI.writeResult("Output folder is empty, please choose one before run the recommender.", Constants.Log.ERROR);
			return null;
		}

		String[] args = new String[ARGS_SPLIDATA_SIZE];

		int p = 0;
		args[p] = "--input";
		args[++p] = inputPath;
		args[++p] = "--output";
		args[++p] = outputPath;
		args[++p] = "--trainingPercentage";
		args[++p] = "0.9";
		args[++p] = "--probePercentage";
		args[++p] = "0.1";
		args[++p] = "--tempDir";
		args[++p] = outputPath + slash + "tmp";

		for (int i = 0; i < ARGS_SPLIDATA_SIZE; i++) {
			log.info(args[i].toString());
		}
		return args;
	}

	public static String[] buildFactorizerJob() {
		String inputPath = null;
		if (FactorizerRecommenderPanel.getEvaluateFactorizer()) {
			inputPath = DataModelRecommenderPanel.getOutputPath() + slash + "dataset" + slash + "trainingSet" + slash + "";
		} else {
			inputPath = DataModelRecommenderPanel.getInputPath();
		}
		String outputPath = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "out" + slash + "";
		String numFeatures = ALSWRFactorizerInputDialog.getNoFeaturesArg();
		String numIterations = ALSWRFactorizerInputDialog.getNoIterationsArg();
		String lambda = ALSWRFactorizerInputDialog.getLambdaArg();
		String numThreadsPerSolver = ALSWRFactorizerInputDialog.getNoTrainingThreadsArg();

		if (FactorizerRecommenderPanel.getSelectedFunction().equals(Constants.RecommFactorizer.ALSWR_SHORT)) {
			String[] args = new String[ARGS_ALSFACTORIZATION_SIZE];

			int p = 0;
			args[p] = "--input";
			args[++p] = inputPath;
			args[++p] = "--output";
			args[++p] = outputPath;
			args[++p] = "--tempDir";
			args[++p] = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "tmp";
			args[++p] = "--numFeatures";
			args[++p] = numFeatures;
			args[++p] = "--numIterations";
			args[++p] = numIterations;
			args[++p] = "--lambda";
			args[++p] = lambda;
			args[++p] = "--numThreadsPerSolver";
			args[++p] = StringUtils.isBlank(numThreadsPerSolver) ? "1" : numThreadsPerSolver;

			for (int i = 0; i < ARGS_ALSFACTORIZATION_SIZE; i++) {
				log.info(args[i].toString());
			}
			return args;
		} else { // SVD
			String[] args = new String[ARGS_SVDFACTORIZATION_SIZE];

			String alpha = SVDFactorizerInputDialog.getAlphaArg();

			int p = 0;
			args[p] = "--input";
			args[++p] = inputPath;
			args[++p] = "--output";
			args[++p] = outputPath;
			args[++p] = "--tempDir";
			args[++p] = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "tmp";
			args[++p] = "--numFeatures";
			args[++p] = numFeatures;
			args[++p] = "--numIterations";
			args[++p] = numIterations;
			args[++p] = "--lambda";
			args[++p] = lambda;
			args[++p] = "--numThreadsPerSolver";
			args[++p] = StringUtils.isBlank(numThreadsPerSolver) ? "1" : numThreadsPerSolver;
			args[++p] = "--implicitFeedback";
			args[++p] = "true";
			args[++p] = "--alpha";
			args[++p] = StringUtils.isBlank(alpha) ? "40" : alpha;

			for (int i = 0; i < ARGS_SVDFACTORIZATION_SIZE; i++) {
				log.info(args[i].toString());
			}
			return args;
		}
	}

	public static String[] buildEvaluatorJob() {
		String inputPath = DataModelRecommenderPanel.getOutputPath() + slash + "dataset" + slash + "probeSet" + slash + "";
		String outputPath = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "rmse" + slash + "";
		String userFeatures = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "out" + slash + "U" + slash + "";
		String itemFeatures = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "out" + slash + "M" + slash + "";

		String[] args = new String[ARGS_EVALUATOR_SIZE];

		int p = 0;
		args[p] = "--input";
		args[++p] = inputPath;
		args[++p] = "--output";
		args[++p] = outputPath;
		args[++p] = "--tempDir";
		args[++p] = DataModelRecommenderPanel.getOutputPath() + slash + "als" + slash + "tmp";
		args[++p] = "--userFeatures";
		args[++p] = userFeatures;
		args[++p] = "--itemFeatures";
		args[++p] = itemFeatures;

		for (int i = 0; i < ARGS_EVALUATOR_SIZE; i++) {
			log.info(args[i].toString());
		}
		return args;
	}

}
