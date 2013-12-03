package MahoutInAction.Recommender;

import java.io.File;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.common.RandomUtils;

@SuppressWarnings("deprecation")
class Recommender100k26 {

	public static void main(String[] args) throws Exception {

		RandomUtils.useTestSeed();	
		
		DataModel model = new FileDataModel(new File("data/ml-100k/ua.base"));
		
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator ();
		
		RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel model)
			throws TasteException {
			return new SlopeOneRecommender(model);
			}
		};
		
		double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);
		System.out.println(score);

	}
}