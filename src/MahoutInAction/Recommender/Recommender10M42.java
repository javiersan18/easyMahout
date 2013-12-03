package MahoutInAction.Recommender;

import java.io.File;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.apache.mahout.cf.taste.impl.eval.LoadEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import easyMahout.recommender.ExtendedDataModel;

class Recommender10M42 {

	public static void main(String[] args) throws Exception {

		RandomUtils.useTestSeed();
		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/easyMahout/log4j.properties");

		DataModel model = new ExtendedDataModel(new File("data/ml-10M100K/ratings.dat"), "::");

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, model);
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
		LoadEvaluator.runLoad(recommender);

	}
}