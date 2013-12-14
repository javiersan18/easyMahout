package easyMahout.utils;

public class Constants {

	public class Log {

		public static final String ERROR = "error";

		public static final String RESULT = "result";

		public static final String WARNING = "warning";

		public static final String INFO = "info";
	}

	public class RecommType {

		public static final String USERBASED = "User based";

		public static final String ITEMBASED = "Item based";

	}

	public class DataModel {

		public static final String FILE = "File";

		public static final String GENERIC = "Generic";

		public static final String GENERIC_BOOLEAN = "Generic Boolean Preferences";

		public static final String EXTENDED = "Extended";

		public static final String CASSANDRA = "Cassandra";

		public static final String HBASE = "HBase";

		public static final String KDDCUP = "KDD Cup";

		public static final String MONGOL_DB = "Mongo DB";

		public static final String PLUS_ANONYMOUS = "Plus Anonymous Concurrent User";

	}

	public class Similarity {

		// public static final String ABSTRACT_ITEM = "Abstract Item";

		// public static final String ABSTRACT = "Abstract";

		// public static final String AVERAGING =
		// "Averaging Preference Inferrer";

		// public static final String CACHING_ITEM = "Caching Item";

		// public static final String CACHING_USER = "Caching User";

		public static final String CITYBLOCK = "City Block (Manhattan Distance)";

		public static final String EUCLIDEAN = "Euclidean Distance";

		// public static final String GENERIC_ITEM = "Generic Item";

		// public static final String GENERIC_USER = "Generic User";

		public static final String LOGARITHMIC = "Logarithmic Likelihood";

		// public static final String LONGPAIR = "Long Pair Match Predicate";

		public static final String PEARSON = "Pearson Correlation";

		public static final String SPEARMAN = "Spearman Correlation";

		public static final String TANIMOTO = "Tanimoto Coeficient";

		public static final String COSINE = "Uncentered Cosine";

	}

	public class Neighborhood {

		// public static final String ABSTRACT = "Abstract";

		// public static final String CACHING = "Caching";

		public static final String NEAREST = "Nearest N";

		public static final String THRESHOLD = "Threshold";

	}

	public class RecommenderXML {

		public static final String RECOMMENDER = "recommender";

		public static final String RECOMM_NAME = "name";

		public static final String RECOMM_NAME_VALUE = "value";

		public static final String RECOMM_TYPE = "type";

		public static final String RECOMM_TYPE_VALUE = "value";

		public static final String RECOMM_DATAMODEL = "datamodel";

		public static final String RECOMM_DATAMODEL_BOOLEAN = "boolean";

		public static final String RECOMM_DATAMODEL_MODEL = "model";

		public static final String RECOMM_DATAMODEL_DELIMITER = "delimiter";

		public static final String RECOMM_DATAMODEL_PATH = "path";

		public static final String RECOMM_SIMILARITY = "similarity";

		public static final String RECOMM_SIMILARITY_METRIC = "metric";

		public static final String RECOMM_SIMILARITY_WEIGHTED = "weighted";

		public static final String RECOMM_NEIGHBORHOOD = "neighborhood";

		public static final String RECOMM_NEIGHBORHOOD_FUNCTION = "function";

		public static final String RECOMM_NEIGHBORHOOD_SIZE = "size";

		public static final String RECOMM_NEIGHBORHOOD_MINIMUM = "minimum";

		public static final String RECOMM_NEIGHBORHOOD_SAMPLING = "sampling";

		public static final String RECOMM_NEIGHBORHOOD_THRESHOLD = "threshold";

		public static final String RECOMM_EVALUATOR = "evaluator";

		public static final String RECOMM_QUERY = "query";

		public static final String RECOMM_QUERY_SELECTED = "selected";

		public static final String RECOMM_QUERY_USERID = "userID";

		public static final String RECOMM_QUERY_HOWMANY = "howMany";

		public static final String CLASSIFICATION = "classification";

		public static final String CLUSTERING = "clustering";

	}
	
	public class SavesPaths {

		public static final String RECOMMENDER = "saves/recommender/";

		public static final String CLUSTERING = "saves/clustering/";
		
		public static final String CLASSIFICATION = "saves/classification/";
		
		public static final String EXTENSION = ".xml";
	
	}

	public class ClusterAlg {

		public static final String CANOPY = "CANOPY";

		public static final String KMEANS = "K-MEANS";

		public static final String FUZZYKMEANS = "Fuzzy K-MEANS";

	}

	public class ClusterDist {

		public static final String EUCLIDEAN = "EUCLIDEAN";

		public static final String SQUAREDEUCLIDEAN = "SQUARED EUCLIDEAN";

		public static final String MANHATTAN = "MANHATTAN";

		public static final String COSINE = "COSINE";

		public static final String TANIMOTO = "TANIMOTO";

		public static final String WEIGHTED = "WEIGHTED";
	}
}
