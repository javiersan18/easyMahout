package MahoutInAction.Recommender;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Recommender22 {

	public static void main(String[] args) throws Exception {

		DataModel model = new FileDataModel(new File("data/intro.csv"));

		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);

		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(1, 1);

		if (recommendations != null && !recommendations.isEmpty()) {

			Iterator<RecommendedItem> it = recommendations.iterator();

			while (it.hasNext()) {
				System.out.println(it.next());
			}
		}

	}
}