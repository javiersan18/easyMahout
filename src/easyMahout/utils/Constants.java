package easyMahout.utils;

public class Constants {

	public class EasyMahout {

		public static final String VERSION = "0.9b";

		public static final String VERSION_DATE = "May 2014";

		public static final String WEBSITE = "www.easymahout.coomingsoon.xxx";

	}

	public class Log {

		public static final String ERROR = "error";

		public static final String RESULT = "result";

		public static final String WARNING = "warning";

		public static final String INFO = "info";

		public static final String EMPTY = "";
	}

	public class RecommType {

		public static final String USERBASED = "User based";

		public static final String ITEMBASED = "Item based";

		public static final String ITEMSIMILARITY = "Item Similarities";

		public static final String FACTORIZED_RECOMMENDER = "Rating Matrix Factorization";

		public static final String ITEMBASED_DISTRIBUTED = "Item Based Collaborative Filtering";

	}

	public class RecommFactorizer {

		public static final String ALSWR = "Alternating Least Squares with Weighted-lambda-Regulations";

		public static final String ALSWR_SHORT = "Alternating Least Squares WR (ALSWR)";

		public static final String PARALLEL_SGD = "Parallel Stochastic Gradient Descent";

		public static final String RATING_SGD = "Rating Stochastic Gradient Descent";

		public static final String SVD_PLUS_PLUS = "Singular Value Decomposition ++ (SVD++)";

		public static final String SVD = "Singular Value Decomposition (SVD)";
	}

	public class RecommCandidate {

		public static final String ALL_SIMILAR_ITEMS = "All Similar Items";

		public static final String ALL_UKNOWN_ITEMS = "All Uknown Items";

		public static final String PREFERRED_ITEMS = "Preferred Items";

		public static final String SAMPLING_ITEMS = "Sampling Items";

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

		public static final String CITYBLOCK = "City Block (Manhattan Distance)";

		public static final String EUCLIDEAN = "Euclidean Distance";

		public static final String LOGARITHMIC = "Logarithmic Likelihood";

		public static final String PEARSON = "Pearson Correlation";

		public static final String SPEARMAN = "Spearman Correlation";

		public static final String TANIMOTO = "Tanimoto Coeficient";

		public static final String COSINE = "Uncentered Cosine";

	}

	public class SimilarityDistributed {

		public static final String EUCLIDEAN = "Euclidean Distance";

		public static final String COOCURRENCE = "Coocurrence";

		public static final String LOGARITHMIC = "Loglikelihood";

		public static final String PEARSON = "Pearson Correlation";

		public static final String TANIMOTO = "Tanimoto Coefficient";

		public static final String COSINE = "Uncentered Cosine";

		public static final String ZERO_COSINE = "Uncentered Zero Assuming Cosine";

	}

	public class SimilarityDistributedParameter {

		public static final String EUCLIDEAN = "SIMILARITY_EUCLIDEAN_DISTANCE";

		public static final String COOCURRENCE = "SIMILARITY_COOCCURRENCE";

		public static final String LOGARITHMIC = "SIMILARITY_LOGLIKELIHOOD";

		public static final String PEARSON = "SIMILARITY_PEARSON_CORRELATION";

		public static final String TANIMOTO = "SIMILARITY_TANIMOTO_COEFFICIENT";

		public static final String COSINE = "SIMILARITY_UNCENTERED_COSINE";

		public static final String ZERO_COSINE = "SIMILARITY_UNCENTERED_ZERO_ASSUMING_COSINE";

	}

	public class Neighborhood {

		public static final String NEAREST = "Nearest N";

		public static final String THRESHOLD = "Threshold";
	}

	public class Evaluator {

		public static final String AVERAGE_ABSOLUTE_DIFFERENCE = "Average Absolute Difference";

		public static final String GENERIC_IRSTATS = "IR Statistics";

		public static final String RMS_RECOMMENDER = "Root Mean Square";

	}

	public class RecommenderXML {

		public static final String RECOMMENDER = "recommender";

		public static final String RECOMM_NAME = "name";

		public static final String RECOMM_NAME_VALUE = "value";

		public static final String RECOMM_TYPE = "type";

		public static final String RECOMM_TYPE_VALUE = "value";

		public static final String RECOMM_DISTRIBUTED = "distributed";

		public static final String RECOMM_DISTRIBUTED_VALUE = "value";

		public static final String RECOMM_DATAMODEL = "datamodel";

		public static final String RECOMM_DATAMODEL_BOOLEAN = "boolean";

		public static final String RECOMM_DATAMODEL_MODEL = "model";

		public static final String RECOMM_DATAMODEL_DELIMITER = "delimiter";

		public static final String RECOMM_DATAMODEL_INPUTPATH = "inputPath";

		public static final String RECOMM_DATAMODEL_OUTPUTPATH = "outputPath";

		public static final String RECOMM_SIMILARITY = "similarity";

		public static final String RECOMM_SIMILARITY_METRIC = "metric";

		public static final String RECOMM_SIMILARITY_WEIGHTED = "weighted";

		public static final String RECOMM_SIMILARITY_MAXSIM = "maxsim";

		public static final String RECOMM_SIMILARITY_MAXPREFS = "maxprefs";

		public static final String RECOMM_SIMILARITY_MINPREFS = "minprefs";

		public static final String RECOMM_SIMILARITY_THRESHOLD = "threshold";

		public static final String RECOMM_NEIGHBORHOOD = "neighborhood";

		public static final String RECOMM_NEIGHBORHOOD_FUNCTION = "function";

		public static final String RECOMM_NEIGHBORHOOD_SIZE = "size";

		public static final String RECOMM_NEIGHBORHOOD_MINIMUM = "minimum";

		public static final String RECOMM_NEIGHBORHOOD_SAMPLING = "sampling";

		public static final String RECOMM_NEIGHBORHOOD_THRESHOLD = "threshold";

		public static final String RECOMM_EVALUATOR = "evaluator";

		public static final String RECOMM_EVALUATOR_TYPE = "type";

		public static final String RECOMM_EVALUATOR_TRAINING = "training";

		public static final String RECOMM_EVALUATOR_EVALUATION = "evaluation";

		public static final String RECOMM_EVALUATOR_AT = "topN";

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

		public static final String CLASSIFIER = "saves/classifier/";

		public static final String EXTENSION = ".xml";

	}

	public class ClusterAlg {

		public static final String CANOPY = "Canopy";

		public static final String KMEANS = "K-Means";

		public static final String FUZZYKMEANS = "Fuzzy K-Means";

		public static final String DIRICHLET = "DIRICHLET";

		public static final String USER_DEFINED = "Please choose an algorithm";

		public static final int NUMBER_ALG = 3;

	}

	public class ClusterDist {

		public static final String EUCLIDEAN = "Euclidean";

		public static final String SQUAREDEUCLIDEAN = "Squared Euclidean";

		public static final String MANHATTAN = "Manhattan";

		public static final String COSINE = "Cosine";

		public static final String TANIMOTO = "Tanimoto";

		public static final String WEIGHTED = "Weighted";
	}

	public class ClusterKmeans {

		public static final String DISTANCE = ClusterDist.EUCLIDEAN;

		public static final String THRESHOLD = "0.01";

		public static final String CLUSTERS = "3";

		public static final String ITERATIONS = "10";
	}

	public class ClassificatorAlg {

		public static final String SGD = "SGD";

		public static final String SVM = "SVM";

		public static final String NAIVEBAYES = "NAIVE BAYES";

		public static final String COMPNAIVEBAYES = "COMPLEMENTARY NAIVE BAYES";

		public static final String RAMDOMFOREST = "RAMDOM FOREST";
	}

	public class ClassificatorPredictorTypes {

		public static final String WORD = "WORD";

		public static final String TEXT = "TEXT";

		public static final String NUMERIC = "NUMERIC";

	}

}
