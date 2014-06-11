package easyMahout.utils.help;

public class RecommenderTips {

	public static final String RECOMM = "docs/tooltips/recommender/recommender.html";

	public static final String RECOMM_TYPE = "<html>"
			+ "<h4>Recommender Types</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates recommendations</p>"
			+ "<p>using only information about rating profiles for different</p>"
			+ "<p>users. User based recommenders locate peer users</p>"
			+ "<p> with a rating history similar to the current user and</p>"
			+ "<p> generate recommendations using this neighborhood.</p><br>" 
			+ "<p><u><b>Item based:</b></u> The similarities between different items in </p>"
			+ "<p>the dataset are calculated by using one of a number of </p>"
			+ "<p>similarity measures, and then these similarity values </p>"
			+ "<p>are used to predict ratings for user-item pairs not </p>"
			+ "<p>present in the dataset.</p><br>"
			+ "<p><u><b>Matrix factorization:</b></u> Firstly matrix </p>"
			+ "<p>factorization of Matrix U and M is done through ALS-WR </p>"
			+ "<p>or SVD algorithm. Then predictions are calculated with</p>"
			+ "<p> U and M precalculated matrix.</p>"
			+ "</html>";
	
	public static final String RECOMM_TYPE_DIST = "<html>"
			+ "<h4>MapReduce Recommenders</h4>"
			+ "<p><u><b>Item based:</b></u> The similarities between different items</p>"
			+ "<p>in the dataset are calculated by using one of a number </p>"
			+ "<p>of similarity measures, and then these similarity values</p>"
			+ "<p> are used to predict ratings for user-item pairs not</p>"
			+ "<p> present in the dataset.</p><br>"
			+ "<p><u><b>Matrix factorization:</b></u> Matrix factorization of Matrix U </p>"
			+ "<p>and M is done through ALS-WR or SVD algorithm. </p>"
			+ "<p>Then predictions are calculated with U and M </p>"
			+ "<p>precalculated matrix.</p>"
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
			+ "<p>Select one similarity metric from list.</p><br>"	
			+ "<p>- Maximum number of similarities considered per item.</p><br>"
			+ "<p>- Maximum number of preferences considered per user</p>"
			+ "<p>in final recommendation phase.</p><br>"
			+ "<p>- Ignore user swithless preferences than this in the</p>"
			+ "<p>similarity computation.</p><br>"
			+ "<p>- Max number of preferences to consider per user in</p>"
			+ "<p>the item similarity computation phase , user swith</p>"
			+ "<p> more preferencesb will be sampled down.</p><br>"
			+ "<p>- Discard item pairs with a similarity value below this.</p>"
			+ "</html>";

	public static final String RECOMM_NEIGHBORHOOD = "<html>"
			+ "<h4>Neighborhood Function</h4><br>"
			+ "<p>Select \"Nearest-N\" or \"Threshold\" function.</p><br>"		
			+ "<p>If you selected Nearest N, you have to choose the size</p>"
			+ "<p>of the neighborhood (N).</p><br>"
			+ "<p>Otherwise you have to configure the threshold value.</p>"
			+ "</html>";

	public static final String RECOMM_EVALUATOR = "<html>"
			+ "<h4>Evaluate recommender</h4><br>"
			+ "<p>Select your chosen kind of evaluator.</p><br>"		
			+ "<p>If it corresponds, write the size of the training set.</p>"
			+ "<p>\"0.7\" means 70% of data set.</p><br>"
			+ "<p>If IR statistics was chosen, you have to write the</p>"
			+ "number N of top relevant documents.</p>"
			+ "</html>";

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
			+ "<br><p><u><b>2.</b></u> Create the data model selecting a .csv file (It's possible to use different delimiters). Mark check if boolean preferences.</p>"
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
			+ "<p>Firstly, write the number of predictions per user that the</p>"		
			+ "<p>recommender will produce.</p><br>"
			+ "<p>Clicking on \"Show\" button, you can see the list of commands that</p>"
			+ "<p>easyMahout is executing internally, or export as shell script to</p>"
			+ "<p>execute it later in a terminal clicking \"Export\" button.</p><br>"
			+ "<p>Click on \"Run\" button to execute the MapReduce recommender.</p>"
			+ "</html>";

	public static final String RECOMM_FACTORIZER = "<html>"
			+ "<h4>Configure the factorizer</h4><br>"
			+ "<p>Select the factorizer model between ALSWR or SVD. Use</p>"		
			+ "<p>SVD for implicit feedback dataset.</p><br>"
			+ "<p>- Dimension of the feature space.</p><br>"
			+ "<p>- Number of iteraions of algorithm.</p><br>"
			+ "<p>- Lambda: Regularization parameter to avoid overffiting.</p><br>"
			+ "<p>- Alpha: Confidence parameter (only in SVD).</p><br>"
			+ "<p>- Moreover you can choose de candidate item strategy.</p><br>"
			+ "</html>";

	public static final String RECOMM_FACTORIZER_DIST = "<html>"
			+ "<h4>Configure the factorizer</h4><br>"
			+ "<p>Select the factorizer model between ALSWR or SVD. Use</p>"		
			+ "<p>SVD for implicit feedback dataset.</p><br>"
			+ "<p>- Dimension of the feature space.</p><br>"
			+ "<p>- Number of iteraions of algorithm.</p><br>"
			+ "<p>- Lambda: Regularization parameter to avoid overffiting.</p><br>"
			+ "<p>- Alpha: Confidence parameter (only in SVD).</p><br>"
			+ "<p>- Number of threads per solver mapper. (Optional)</p><br>"
			+ "</html>";

}
