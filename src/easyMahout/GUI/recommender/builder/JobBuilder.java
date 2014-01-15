package easyMahout.GUI.recommender.builder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.RowSimilarityJob;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.DataModelRecommenderPanel;
import easyMahout.GUI.recommender.SimilarityRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;

public class JobBuilder {
	
	private final static Logger log = Logger.getLogger(JobBuilder.class);

	public final static String[] buildRecommenderJob() {
		if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMBASED_DISTRIBUTED)) {

			String inputPath = DataModelRecommenderPanel.getInputPath();
			String outputPath = DataModelRecommenderPanel.getOutputPath();
			String similarityClassname = SimilarityRecommenderPanel.getDistributedSimilarity();
			String maxSimilaritiesPerItem = SimilarityRecommenderPanel.getMaxSimilarities();
			String maxPrefsPerUser = SimilarityRecommenderPanel.getMaxPreferences();
			String minPrefsPerUser = SimilarityRecommenderPanel.getMinPreferences();
			String booleanData = DataModelRecommenderPanel.getBooleanPrefs();
			String threshold = SimilarityRecommenderPanel.getThreshold();

			String[] args = new String[16];

			if (StringUtils.isBlank(inputPath)) {
				MainGUI.writeResult("Input folder is empty, please choose one before run the recommender", Constants.Log.ERROR);
				return null;
			} else if (StringUtils.isBlank(outputPath)) {
				MainGUI.writeResult("Output folder is empty, please choose one before run the recommender", Constants.Log.ERROR);
				return null;
			} else {
				args[0] = "--input";
				args[1] = inputPath;
				args[2] = "--output";
				args[3] = outputPath;
				args[4] = "--similarityClassname";
				args[5] = similarityClassname;
				args[6] = "--maxSimilaritiesPerItem";
				args[7] = StringUtils.isBlank(maxSimilaritiesPerItem) ? "100" : maxSimilaritiesPerItem;
				args[8] = "--maxPrefsPerUser";
				args[9] = StringUtils.isBlank(maxPrefsPerUser) ? "500" : maxPrefsPerUser;
				args[10] = "--minPrefsPerUser";
				args[11] = StringUtils.isBlank(minPrefsPerUser) ? "1" : minPrefsPerUser;
				args[12] = "--booleanData";
				args[13] =StringUtils.isBlank(booleanData) ? String.valueOf(Boolean.FALSE) : booleanData;
				//TODO: threshold can be wrong in this way "4.9E-324"
				args[14] = "--threshold";
				args[15] = StringUtils.isBlank(threshold) ? String.valueOf(RowSimilarityJob.NO_THRESHOLD) : threshold;					
				
				for(int i=0; i<16; i++){
					log.info(args[i].toString());
				}				
				return args;
			}			
			
		} else if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMSIMILARITY)) {
			// TODO
			return null;
		} else {
			// if(Constants.RecommType.FACTORIZED_RECOMMENDER)
			// TODO
			return null;
		}

	}
	
}
