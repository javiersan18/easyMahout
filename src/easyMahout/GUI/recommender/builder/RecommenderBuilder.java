package easyMahout.GUI.recommender.builder;

import org.apache.log4j.Logger;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.svd.Factorizer;
import org.apache.mahout.cf.taste.impl.recommender.svd.SVDRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.CandidateItemsStrategy;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.DataModelRecommenderPanel;
import easyMahout.GUI.recommender.FactorizerRecommenderPanel;
import easyMahout.GUI.recommender.NeighborhoodRecommenderPanel;
import easyMahout.GUI.recommender.SimilarityRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;

public class RecommenderBuilder {

	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(RecommenderBuilder.class);

	public static Recommender buildRecommender() {
		if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.USERBASED)) {
			DataModel model = DataModelRecommenderPanel.getDataModel();
			if (model != null) {
				UserSimilarity similarity = SimilarityRecommenderPanel.getUserSimilarity(model);
				UserNeighborhood neighborhood = NeighborhoodRecommenderPanel.getNeighborhood(similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood, similarity);
			} else {
				MainGUI.writeResult("Trying to run a recommender without a dataModel loaded.", Constants.Log.ERROR);
				return null;
			}

		} else if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.ITEMBASED)) {
			DataModel model = DataModelRecommenderPanel.getDataModel();
			if (model != null) {
				ItemSimilarity similarity = SimilarityRecommenderPanel.getItemSimilarity(model);
				return new GenericItemBasedRecommender(model, similarity);
			} else {
				MainGUI.writeResult("Trying to run a recommender without a dataModel loaded.", Constants.Log.ERROR);
				return null;
			}

		} else if (TypeRecommenderPanel.getSelectedType().equals(Constants.RecommType.FACTORIZED_RECOMMENDER)) {
			DataModel model = DataModelRecommenderPanel.getDataModel();
			if (model != null) {				
				Factorizer factorizer = FactorizerRecommenderPanel.getFactorizer();
				if (factorizer != null) {
					CandidateItemsStrategy candidate = FactorizerRecommenderPanel.getCandidate();
					if (candidate != null) {
						try {
							return new SVDRecommender(model, factorizer, candidate);
						} catch (TasteException e) {
							MainGUI.writeResult("Error building the recommender.", Constants.Log.ERROR);
							e.printStackTrace();							
						}
					}
				} else {
					MainGUI.writeResult("Factorizer couldn't be built successfully.", Constants.Log.ERROR);
					return null;
				}
			} else {
				MainGUI.writeResult("Trying to run a recommender without a dataModel loaded.", Constants.Log.ERROR);
				return null;
			}

		} else {
			return null;
		}
		return null;		
	}

}
