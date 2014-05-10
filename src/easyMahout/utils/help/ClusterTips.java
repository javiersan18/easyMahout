package easyMahout.utils.help;

public class ClusterTips {
	public static final String CLUSTER = "docs/tooltips/clustering/cluster.html";

	public static final String CLUSTER_TYPE = "<html>"
			+ "<h4>Cluster Types</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates clusters using only information</p>"
			+ "<p>about rating profiles for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
			+ "<p>current user and generate clusters using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
			+ "</html>";

	public static final String CLUSTER_DATAMODEL = "docs/tooltips/clustering/datamodel.html";

	public static final String CLUSTER_SIMILARITY = "docs/tooltips/clustering/similarity.html";
	
	public static final String CLUSTER_ALGORITHM = "docs/tooltips/clustering/algorithm.html";
	
	public static final String CLUSTER_DIST = "docs/tooltips/clustering/distance.html";

	public static final String CLUSTER_NEIGHBORHOOD = "docs/tooltips/clustering/neighborhood.html";

	public static final String CLUSTER_EVALUATOR = "docs/tooltips/clustering/evaluator.html";

	public static final String CLUSTER_NCLUSTERS = "docs/tooltips/clustering/numberOfClusters.html";
	
	public static final String CLUSTER_NITERATIONS = "docs/tooltips/clustering/numberOfIterations.html";

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

	public static final String CLUSTER_TYPE_DIST = "<html>"
			+ "<h4>Cluster Types Distributed</h4>"
			+ "<br><p><u><b>User based:</b></u> The system generates clusters using only information</p>"
			+ "<p>about rating profiles for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
			+ "<p>current user and generate clusters using this neighborhood.</p>" + "<br>" + "<p><u><b>Item based:</b></u></p>"
			+ "</html>";

	public static final String CLUSTER_DATAMODEL_DIST = "docs/tooltips/clustering/datamodel_dist.html";

	public static final String CLUSTER_SIMILARITY_DIST = "docs/tooltips/clustering/similaruty_dist.html";

	public static final String CLUSTER_CONFIG_DIST = "<html>"
			+ "<h4>Steps to build a DISTRIBUTED cluster</h4>"
			+ "<br><p><u><b>1.</b></u> The system generates clusters using only information</p>"
			+ "<br><p><u><b>2.</b></u> about rating profiles for different users.</p><p>User based clusters locate peer users with a rating history similar to the </p>"
			+ "<br><p><u><b>3.</b></u> current user and generate clusters using this neighborhood.</p>" + "<br>"
			+ "<p><u><b>4.</b></u></p>" + "</html>";

	public static final String CLUSTER_JOB = "docs/tooltips/clustering/job_dist.html";


}
