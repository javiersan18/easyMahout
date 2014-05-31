package easyMahout.GUI.clustering.builder;

import easyMahout.GUI.clustering.DataModelClusterPanel;

public class JobClusterBuilder {

	private static final int ARGS_SPLITDATA = 10;

	private static final int ARGS_SEQUENCE_FROM_DIRECTORY = 6;

	private static final int ARGS_SPARSEVECTOR = 9;

	private static final String SLASH = System.getProperty("file.separator");

	public static final String[] buildSequenceFromDirectoryJob() {
		int i = 0;
		String[] args = new String[ARGS_SEQUENCE_FROM_DIRECTORY];
		args[i++] = "--input";
		args[i++] = DataModelClusterPanel.getInputPath();

		args[i++] = "--output";
		args[i++] = DataModelClusterPanel.getOutputPath();

		args[i++] = "--method";
		args[i++] = "sequential";
		return args;
	}

	public static final String[] buildSparseVectorsJob() {
		int i = 0;
		String[] args = new String[ARGS_SPARSEVECTOR];
		args[i++] = "--input";
		args[i++] = DataModelClusterPanel.getOutputPath();

		args[i++] = "--output";
		String vec = DataModelClusterPanel.getOutputPath() + SLASH + "vectors";
		args[i++] = vec;

		args[i++] = "--overwrite";

		args[i++] = "--logNormalize";
		args[i++] = "--namedVector";
		args[i++] = "--weight";
		args[i++] = "tfidf";
		return args;
	}

	public static final String[] buildSplitJob() {
		int i = 0;
		String[] args = new String[ARGS_SPLITDATA];
		args[i++] = "--input";
		args[i++] = DataModelClusterPanel.getOutputPath() + SLASH + "vectors" + SLASH + "tfidf-vectors";

		args[i++] = "--randomSelectionPct";
		args[i++] = "20";

		String clusterIn2 = DataModelClusterPanel.getOutputPath() + SLASH + "clusters";

		args[i++] = "--overwrite";
		args[i++] = "--sequenceFiles";

		args[i++] = "--method";
		args[i++] = "mapreduce";

		args[i++] = "-mro";
		args[i++] = clusterIn2 + "s";
		return args;
	}

}
