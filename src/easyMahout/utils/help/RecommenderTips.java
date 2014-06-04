package easyMahout.utils.help;

public class RecommenderTips {

	public static final String RECOMM = "docs/tooltips/recommender/recommender.html";

	public static final String RECOMM_TYPE = "<html>"
			+ "<h4>Recommender Types</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates recommendations using only information</p>"
			+ "<p>about rating profiles for different users.</p><p>User based recommenders locate peer users with a rating history similar to the </p>"
			+ "<p>current user and generate recommendations using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
			+ "</html>";

	public static final String RECOMM_DATAMODEL = "docs/tooltips/recommender/datamodel.html";

	public static final String RECOMM_SIMILARITY = "docs/tooltips/recommender/similaruty.html";

	public static final String RECOMM_NEIGHBORHOOD = "docs/tooltips/recommender/neighborhood.html";

	public static final String RECOMM_EVALUATOR = "docs/tooltips/recommender/evaluator.html";

	public static final String RECOMM_QUERY = "docs/tooltips/recommender/query.html";

	public static final String RECOMM_CONFIG = "<html>"
			+ "<h4>Steps to build a recommender</h4>"
			+ "<br><p><u><b>1.</b></u> Select the type of Recommender (Item-based, User-based, Matrix Factorization).</p>"
			+ "<br><p><u><b>2.</b></u> Create the data model selecting a .csv file (It's possible to use differents delimiters). Mark check if boolean preferences.</p>"
			+ "<br><p><u><b>3.</b></u> Choose the similarity metric from the list. </p>" 
			+ "<br><p><u><b>4.1.</b></u> Select neighborhood function and fill the parameters. (Only for user-based recommender)</p>" 
			+ "<br><p><u><b>4.2.</b></u> Select ALSWR or SVD factorization and fill the parameters. (Only for factorized recommender)</p>"
			+ "<br><p><u><b>5.</b></u> Write from which users you expect to receive recommendations, and how many.</p>" 
			+ "<br><p><u><b>6.</b></u> RUN!.</p>" + "</html>";

	public static final String RECOMM_TYPE_DIST = "<html>"
			+ "<h4>Recommender Types Distributed</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates recommendations using only information</p>"
			+ "<p>about rating profiles for different users.</p><p>User based recommenders locate peer users with a rating history similar to the </p>"
			+ "<p>current user and generate recommendations using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
			+ "</html>";

	public static final String RECOMM_DATAMODEL_DIST = "docs/tooltips/recommender/datamodel_dist.html";

	public static final String RECOMM_SIMILARITY_DIST = "docs/tooltips/recommender/similaruty_dist.html";

	public static final String RECOMM_CONFIG_DIST = "<html>"
			+ "<h4>Steps to build a DISTRIBUTED recommender</h4>"
			+ "<br><p><u><b>1.</b></u> Select the type of Recommender (Item-based, Matriz Factorization).</p>"
			+ "<br><p><u><b>2.</b></u> Select the input directory with the .csv files and the output dir.Mark check if boolean preferences.</p>"
			+ "<br><p><u><b>3.</b></u> Choose the similarity metric from the list and fill the parameters. </p>" 
			+ "<br><p><u><b>4.</b></u> Select ALSWR or SVD factorization and fill the parameters. (Only for factorized recommender)</p>"
			+ "<br><p><u><b>5.</b></u> Fill the number of recommendations for each user.</p>" 
			+ "<br><p><u><b>6.</b></u> RUN HADOOP JOB!.</p>" + "</html>";

	public static final String RECOMM_JOB = "docs/tooltips/recommender/job_dist.html";

	public static final String RECOMM_FACTORIZER = "docs/tooltips/recommender/factorizer.html";

	public static final String RECOMM_FACTORIZER_DIST = "docs/tooltips/recommender/factorizer_dist.html";

}
