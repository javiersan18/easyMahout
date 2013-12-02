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

		//public static final String CLUSTERING = "Clustering";
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

		//public static final String ABSTRACT_ITEM = "Abstract Item";

		//public static final String ABSTRACT = "Abstract";

		//public static final String AVERAGING = "Averaging Preference Inferrer";

		//public static final String CACHING_ITEM = "Caching Item";

		//public static final String CACHING_USER = "Caching User";

		public static final String CITYBLOCK = "City Block (Manhattan Distance)";

		public static final String EUCLIDEAN = "Euclidean Distance";

		//public static final String GENERIC_ITEM = "Generic Item";

		//public static final String GENERIC_USER = "Generic User";

		public static final String LOGARITHMIC = "Logarithmic Likelihood";

		//public static final String LONGPAIR = "Long Pair Match Predicate";

		public static final String PEARSON = "Pearson Correlation";

		public static final String SPEARMAN = "Spearman Correlation";

		public static final String TANIMOTO = "Tanimoto Coeficient";

		public static final String COSINE = "Uncentered Cosine";

	}

	public class Neighborhood {

		//public static final String ABSTRACT = "Abstract";

		//public static final String CACHING = "Caching";

		public static final String NEAREST = "Nearest N";

		public static final String THRESHOLD = "Threshold";

	}

}
