package easyMahout.utils.help;

public class RecommenderTips {

	public static final String RECOMM = "docs/tooltips/recommender/recommender.html";

	public static final String RECOMM_TYPE = "<html>"
			+ "<h4>Recommender Types</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates recommendations using only</p>"
			+ "<p>information about rating profiles for different users.</p><p>User based </p>"
			+ "<p>recommenders locate peer users with a rating history similar to the </p>"
			+ "<p>current user and generate recommendations using this neighborhood.</p><br>" 
			+ "<p><u><b>Item based:</b></u> The similarities between different items in the</p>"
			+ "<p>dataset are calculated by using one of a number of similarity</p>"
			+ "<p>measures, and then these similarity values are used to predict</p>"
			+ "<p>ratings for user-item pairs not present in the dataset.</p><br>"
			+ "<p><u><b>Matrix factorization:</b></u> Firstly matrix factorization of Matrix</p>"
			+ "<p>U and M is done through ALS-WR or SVD algorithm. Then predictions</p>"
			+ "<p>are calculated with U and M precalculated matrix.</p>"
			+ "</html>";
	
	public static final String RECOMM_TYPE_DIST = "<html>"
			+ "<h4>MapReduce Recommenders</h4>"
			+ "<p><u><b>Item based:</b></u> The similarities between different items in the</p>"
			+ "<p>dataset are calculated by using one of a number of similarity</p>"
			+ "<p>measures, and then these similarity values are used to predict</p>"
			+ "<p>ratings for user-item pairs not present in the dataset.</p><br>"
			+ "<p><u><b>Matrix factorization:</b></u> Firstly matrix factorization of Matrix</p>"
			+ "<p>U and M is done through ALS-WR or SVD algorithm. Then predictions</p>"
			+ "<p>are calculated with U and M precalculated matrix.</p>"
			+ "</html>";

	public static final String RECOMM_DATAMODEL = "<html>"
			+ "<h4>Datamodel (Inputs and Outputs)</h4><br>"
			+ "<p>Depending on the delimiter of data, we must select Generic (for CSV </p>"
			+ "<p>files) or Extended if your data's delimiter is not a comma (colon,</p>"
			+ "<p> semicolon, etc.)</p><br>"
			+ "<p><u><b>Input:</b></u> Algorithm's input must be a unique file with the following</p>"
			+ "<p>structure: \"userId,itemId,rating\".</p><br>"
			+ "<p><u><b>Output:</b></u> Recommender's output will be written in the app log.</p><br>"
			+ "<p>If your ratings are boolean, check the correspondent option.</p><br>"
			+ "<p>Press button Create Model before continue configuring recommender.</p>"
			+ "</html>";
	
	public static final String RECOMM_DATAMODEL_DIST = "<html>"
			+ "<h4>Datamodel (Inputs and Outputs)</h4><br>"
			+ "<p><u><b>Input:</b></u> Algorithm's input must be a directory with one or more files.</p><br>"
			+ "<p><u><b>Output:</b></u> MapReduce sequence file output will be put in that directory.</p><br>"
			+ "<p>If output directory already exits, recommender job will be crash. If you </p>"
			+ "<p>desire, check the option to delete this folder before running the</p>"
			+ "<p>recommender.</p><br>"
			+ "<p>If your ratings are boolean, check the correspondent option.</p>"			
			+ "</html>";

	public static final String RECOMM_SIMILARITY = "<html>"
			+ "<h4>Similarity metric</h4><br>"
			+ "<p>Select one similarity metric from list. Some metrics can be weighted</p>"		
			+ "<p>to achieve better results.</p><br>"
			+ "<p>To find the best metric for your data, a good practice is try every</p>"	
			+ "<p>metric with a small portion of data and evaluate the recommender.</p>"
			+ "</html>";
	
	public static final String RECOMM_SIMILARITY_DIST = "<html>"
			+ "<h4>Similarity metric</h4><br>"
			+ "<p>Select one similarity metric from list. Some metrics can be weighted</p>"		
			+ "<p>to achieve better results.</p><br>"
			+ "<p>To find the best metric for your data, a good practice is try every</p>"	
			+ "<p>metric with a small portion of data and evaluate the recommender.</p>"
			+ "</html>";

	public static final String RECOMM_NEIGHBORHOOD = "docs/tooltips/recommender/neighborhood.html";

	public static final String RECOMM_EVALUATOR = "docs/tooltips/recommender/evaluator.html";

	public static final String RECOMM_QUERY = "<html>"
			+ "<h4>Running the recommender</h4><br>"
			+ "<p>Add as many rows as predictions you want to produce. Then write the</p>"		
			+ "<p>userId and the number of prediction for these user you desire.</p><br>"
			+ "<p>Before you run the recommender, don't forget to select which users</p>"
			+ "<p>will be recommend.</p>"
			+ "</html>";

	public static final String RECOMM_CONFIG = "<html>"
			+ "<h4>Steps to build a recommender</h4>"
			+ "<br><p><u><b>1.</b></u> Select the type of Recommender (Item-based, User-based, Matrix Factorization).</p>"
			+ "<br><p><u><b>2.</b></u> Create the data model selecting a .csv file (It's possible to use differents delimiters). Mark check if boolean preferences.</p>"
			+ "<br><p><u><b>3.</b></u> Choose the similarity metric from the list. </p>" 
			+ "<br><p><u><b>4.1.</b></u> Select neighborhood function and fill the parameters. (Only for user-based recommender)</p>" 
			+ "<br><p><u><b>4.2.</b></u> Select ALSWR or SVD factorization and fill the parameters. (Only for factorized recommender)</p>"
			+ "<br><p><u><b>5.</b></u> Write from which users you expect to receive recommendations, and how many.</p>" 
			+ "<br><p><u><b>6.</b></u> RUN!.</p>" + "</html>";	

	public static final String RECOMM_CONFIG_DIST = "<html>"
			+ "<h4>Steps to build a DISTRIBUTED recommender</h4>"
			+ "<br><p><u><b>1.</b></u> Select the type of Recommender (Item-based, Matriz Factorization).</p>"
			+ "<br><p><u><b>2.</b></u> Select the input directory with the .csv files and the output dir.Mark check if boolean preferences.</p>"
			+ "<br><p><u><b>3.</b></u> Choose the similarity metric from the list and fill the parameters. </p>" 
			+ "<br><p><u><b>4.</b></u> Select ALSWR or SVD factorization and fill the parameters. (Only for factorized recommender)</p>"
			+ "<br><p><u><b>5.</b></u> Fill the number of recommendations for each user.</p>" 
			+ "<br><p><u><b>6.</b></u> RUN HADOOP JOB!.</p>" + "</html>";

	public static final String RECOMM_JOB = "<html>"
			+ "<h4>Running the recommender job</h4><br>"
			+ "<p>Firstly, write the number of predictions for each user that</p>"		
			+ "<p>the recommender will produce.</p><br>"
			+ "<p>Clicking on \"Show\" button, you can see the list of commands that</p>"
			+ "<p>easyMahout is executing internally, or export as shell script to</p>"
			+ "<p>execute it later in a terminal clicking \"Export\" button.</p><br>"
			+ "<p>Click on \"Run\" button to execute the MapReduce recommender.</p>"
			+ "</html>";

	public static final String RECOMM_FACTORIZER = "docs/tooltips/recommender/factorizer.html";

	public static final String RECOMM_FACTORIZER_DIST = "docs/tooltips/recommender/factorizer_dist.html";

}
