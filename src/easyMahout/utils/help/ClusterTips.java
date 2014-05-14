package easyMahout.utils.help;

public class ClusterTips {
	public static final String CLUSTER = "docs/tooltips/clustering/cluster.html";

	public static final String CLUSTER_TYPE = "<html>"
			+ "<h4>Cluster Types</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates clusters using only information</p>"
			+ "<p>about rating profiles for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
			+ "<p>current user and generate clusters using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
			+ "</html>";

	public static final String CLUSTER_DATAMODEL = "<html>"
			+ "<h4>Cluster Data Model</h4>"
			+ "<br><p><u><b>Type of data model:</b></u> Usually <u>Extended</u> is the one used</p>"
			+ "<br><p><u><b>Delimiter:</b></u> Which normally is  <b>;</b>  <b>,</b>  <b>:</b> etc.</p>"
			+"</html>";

	public static final String CLUSTER_THRESHOLD = "<html>"
	+ "<h4>Convergence Threshold</h4>"
	+ "<br><p><u><b>Threshold:</b></u> <p>This is another input parameter for the algorithm used to determine when" + "<br>"
	+ "it is possible to add an item to an existing cluster and when to start a new one."+"<br>"
	+"Depending on the selected algorithm you will have to enter one or two thresholds . </p> " + "<br>"
	+"</html>";
	
	public static final String CLUSTER_ALGORITHM = "<html>"
			+ "<h4>Cluster Algorithms</h4>"
			+ "<br><p><u><b>Canopy:</b></u> The system generates clusters using Canopy algorithm</p>"
			+ "<br><p><u><b>K-Means:</b></u> The system generates clusters using K-Means algorithm</p>"
			+ "<br><p><u><b>Fuzzy K-Means:</b></u> The system generates clusters using Fuzzy K-Means algorithm</p>"
			+ "<br>This item shall be inhibited when distributed option is activated within the interface."
			+"</html>";
	
	
	public static final String CLUSTER_DIST = "<html>"+"<p>Here you have to choose the distance measure that will be used by the " + "<br>"+
			"algorithm in order to cluster your data. </p> " 
			;

	public static final String CLUSTER_NEIGHBORHOOD = "docs/tooltips/clustering/neighborhood.html";

	public static final String CLUSTER_EVALUATOR = "docs/tooltips/clustering/evaluator.html";

	public static final String CLUSTER_NCLUSTERS = "<html>"
			+ "<h4>Number of clusters</h4>"
			+"<p>Here you have to enter the number of clusters you wish the algorithm returns " + "<br>"+
			"This item shall be inhibited depending on the selected algorithm . </p> " 
			;
	
	public static final String CLUSTER_NITERATIONS = "<html>"
	+"<h4>Maximum iterations</h4>"
			+"<p>Here you have to enter the number of iterations you wish the algorithm give. " + "<br>"+
			"The higher it is, the better but keep in mind you need a powerful computer also. </p> ";

	//public static final String CLUSTER_QUERY = "docs/tooltips/clustering/query.html";

	public static final String CLUSTER_CONFIG = "<html>"
			+ "<h4>Steps to build clusters</h4>"
			+ "<br><p><u><b>1.</b></u> Choose the algorithm : K-Means,Canopy,Fuzzy K-Means </p>"
			+ "<br><p><u><b>2.</b></u> Select the distance measure:  Euclidean,Manhattan,Tanimoto, etc. </p>"
			+ "<br><p><u><b>3.</b></u> Insert the convergence threshold : between 0 and 1.</p>"
			+ "<br><p><u><b>4.</b></u> Write the number of clusters : you wish the algorithm structure your data"+"<br>(only available in some algorithms).</p>"
			+ "<br><p><u><b>5.</b></u> Write the number of iterations you wish the algorithm to give. </p>" 
			+ "<br><p><u><b>6.</b></u> Choose the data file you wish to cluster on </p>"
			+ "</html>";
	public static final String CLUSTER_CONFIG_DIST = "<html>"
			+ "<h4>Steps to build clusters within hadoop</h4>"
			
			+ "<br><p><u><b>1.</b></u> Select the distance measure:  Euclidean,Manhattan,Tanimoto, etc. </p>"
			+ "<br><p><u><b>2.</b></u> Insert the convergence threshold : between 0 and 1.</p>"
			+ "<br><p><u><b>3.</b></u> Write the number of iterations you wish the algorithm to give. </p>" 
			+ "<br><p><u><b>4.</b></u> Choose the data folder you wish to cluster on </p>"
			+ "</html>";
	public static final String CLUSTER_TYPE_DIST = "<html>"
			+ "<h4>Cluster Types Distributed</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates clusters using only information</p>"
			+ "<p>about rating profiles for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
			+ "<p>current user and generate clusters using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
			+ "</html>";

	public static final String CLUSTER_DATAMODEL_DIST = "<html>"
	+ "<h4>Data Model(Hadoop)</h4>"
	+"<p> Here you have to insert your data folder. " + "<br>"
	+ " in order to run hadoop clustering on that data</p> ";

	public static final String CLUSTER_SIMILARITY_DIST = "docs/tooltips/clustering/similaruty_dist.html";


	public static final String CLUSTER_JOB = "docs/tooltips/clustering/job_dist.html";


}
