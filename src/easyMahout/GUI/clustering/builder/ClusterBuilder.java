package easyMahout.GUI.clustering.builder;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;

import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import static java.nio.file.FileVisitResult.*;

import javax.swing.JOptionPane;

import org.apache.avro.mapred.Pair;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobContext;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.Canopy;
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.classify.WeightedPropertyVectorWritable;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.dirichlet.DirichletDriver;
import org.apache.mahout.clustering.dirichlet.models.DistributionDescription;
import org.apache.mahout.clustering.display.DisplayCanopy;
import org.apache.mahout.clustering.display.DisplayFuzzyKMeans;
import org.apache.mahout.clustering.display.DisplayKMeans;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.clustering.iterator.ClusterWritable;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.TanimotoDistanceMeasure;
import org.apache.mahout.common.distance.WeightedEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.WeightedManhattanDistanceMeasure;
import org.apache.mahout.math.NamedVector;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.text.MultipleTextFileInputFormat;
import org.apache.mahout.text.PrefixAdditionFilter;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.apache.mahout.text.SequenceFilesFromDirectoryMapper;
import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

import MahoutInAction.Clustering.Clustering72;

import easyMahout.GUI.MainGUI;


import easyMahout.GUI.clustering.AlgorithmClusterPanel;
import easyMahout.GUI.clustering.DataModelClusterPanel;
import easyMahout.GUI.clustering.DistanceMeasurePanel;
import easyMahout.GUI.clustering.MainClusterPanel;
import easyMahout.GUI.recommender.DataModelRecommenderPanel;
import easyMahout.GUI.recommender.NeighborhoodRecommenderPanel;
import easyMahout.GUI.recommender.SimilarityRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;

public class ClusterBuilder {

	private static boolean hayError;

	private static String algoritmo;

	private static DistanceMeasure d;

	private static String treshold1;

	private static String treshold2;

	private static double t1 = 0;
	private static double t2 = 0;

	private static String it;

	private static int iteraciones;

	private static FileSystem fs;

	private static Configuration conf;

	private static String numberClusters;

	private static int numero;

	private static boolean hadoop = MainGUI.isDistributed();// hadoop =true

	private static boolean esCanopy;

	private final static Logger log = Logger.getLogger(ClusterBuilder.class);

	private static File testData;

	private static final String[] KEY_PREFIX_OPTION = null;

	private static final Object[] CHUNK_SIZE_OPTION = null;



	public static Cluster buildCluster() throws ClassNotFoundException, InterruptedException, IOException {

		hayError = false;

		algoritmo = MainClusterPanel.getAlgorithmClusterPanel().getSelectedType();
		String dist = MainClusterPanel.getDistanceClusterPanel().getSelectedType();
		// -------------------------------------------------------------------------------------------
		// cogemos el tipo de distancia

		switch (dist) {
		case Constants.ClusterDist.EUCLIDEAN: {
			MainClusterPanel.getDistanceClusterPanel();
			if (DistanceMeasurePanel.getChckbxWeighted().isSelected())
				d = new WeightedEuclideanDistanceMeasure();
			else
				d = new EuclideanDistanceMeasure();

			break;
		}
		case Constants.ClusterDist.SQUAREDEUCLIDEAN: {
			d = new SquaredEuclideanDistanceMeasure();
			break;
		}
		case Constants.ClusterDist.MANHATTAN: {
			MainClusterPanel.getDistanceClusterPanel();
			if (DistanceMeasurePanel.getChckbxWeighted().isSelected())
				d = new WeightedManhattanDistanceMeasure();
			else
				d = new ManhattanDistanceMeasure();
			break;
		}
		case Constants.ClusterDist.TANIMOTO: {
			d = new TanimotoDistanceMeasure();
			break;
		}
		case Constants.ClusterDist.COSINE: {
			d = new CosineDistanceMeasure();
			break;
		}

		default:
			d = new EuclideanDistanceMeasure();
		}
		// fin tipo distancia
		// algoritmo=CANOPY
		if (algoritmo.equals(Constants.ClusterAlg.CANOPY)) {

			// -------------------------------------------------------------------------------------------
			esCanopy = true;
			// cogemos las valores treshold
			treshold1 = MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch (treshold1) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold 1");
				hayError = true;
				break;
			}
			default: {
				t1 = Double.parseDouble(treshold1);
			}
			}

			treshold2 = MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch (treshold2) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold 2");
				hayError = true;
				break;
			}
			default: {
				t2 = Double.parseDouble(treshold2);
			}
			}
			// fin treshold
			// -------------------------------------------------------------------------------------------

			// nº iterations
			it = MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch (it) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError = true;
				break;
			}
			default: {
				iteraciones = Integer.parseInt(it);
			}
			}

			// fin nº iterations

			// -------------------------------------------------------------------------------------------

			// construimos el cluster CANOPY
			CanopyClusterer cluster = null;
			if (!hayError) {
				cluster = new CanopyClusterer(d, t1, t2);
			}

			if (cluster != null) {
				constructCluster(false);
				/*MainGUI.writeResult("OK building the clusters :Algorithm " + algoritmo + " Distance  " + d + " Treshold " + treshold1
						+ " Iterations " + it, Constants.Log.INFO);
				*/
				}

			else
				MainGUI.writeResult("Error building the clusters", Constants.Log.ERROR);
		}
		// fin CANOPY
		// --------------------------------------------------------------------------------------------
		else if (MainClusterPanel.getAlgorithmClusterPanel().getSelectedType().equals(Constants.ClusterAlg.KMEANS)) {
			// nº clusters
			if (!esCanopy) {
				numberClusters = MainClusterPanel.getNumberClusterPanel().getCampoNum().getText();
				switch (numberClusters) {
				case "": {
					JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
					hayError = true;
					break;
				}
				default: {
					numero = Integer.parseInt(numberClusters);
				}
				}
			}

			// fin nº clusters

			treshold1 = MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch (treshold1) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
				hayError = true;
				break;
			}
			default: {
				t1 = Double.parseDouble(treshold1);
			}
			}
			// nº iterations
			it = MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch (it) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError = true;
				break;
			}
			default: {
				iteraciones = Integer.parseInt(it);
			}
			}

			// fin nº iterations

			Kluster kluster = new Kluster();

			if (kluster != null) {
				Path output = new Path("output");
				conf = new Configuration();
				try {
					fs = FileSystem.get(conf);
				} catch (IOException e2) {

					MainGUI.writeResult("Not able to configure the job", Constants.Log.ERROR);
				}

				constructCluster(true);

			}

			else
				MainGUI.writeResult("error building the clusters", Constants.Log.ERROR);
		}
		// if user choose Fuzzy K-Means
		else if (MainClusterPanel.getAlgorithmClusterPanel().getSelectedType().equals(Constants.ClusterAlg.FUZZYKMEANS)) {
			// nº clusters
			if (!esCanopy) {
				numberClusters = MainClusterPanel.getNumberClusterPanel().getCampoNum().getText();
				switch (numberClusters) {
				case "": {
					JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
					hayError = true;
					break;
				}
				default: {
					numero = Integer.parseInt(numberClusters);
				}
				}
			}

			// fin nº clusters

			treshold1 = MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch (treshold1) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
				hayError = true;
				break;
			}
			default: {
				t1 = Double.parseDouble(treshold1);
			}
			}
			// nº iterations
			it = MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch (it) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError = true;
				break;
			}
			default: {
				iteraciones = Integer.parseInt(it);
			}
			}

			// fin nº iterations

			Kluster kluster = new Kluster();

			if (kluster != null) {
				Path output = new Path("output");
				conf = new Configuration();
				try {
					fs = FileSystem.get(conf);
				} catch (IOException e2) {

					MainGUI.writeResult("Not able to configure the job", Constants.Log.ERROR);

				}

				constructCluster(false);

			}

			else
				MainGUI.writeResult("error building the clusters", Constants.Log.ERROR);
		}
		// fin Fuzzy K-Means
		else if (hadoop) {

			treshold1 = MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch (treshold1) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
				hayError = true;
				break;
			}
			default: {
				t1 = Double.parseDouble(treshold1);
			}
			}
			// nº iterations
			it = MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch (it) {
			case "": {
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError = true;
				break;
			}
			default: {
				iteraciones = Integer.parseInt(it);
			}
			}

			constructCluster(false);
		}

		else if (MainClusterPanel.getAlgorithmClusterPanel().getSelectedType().equals(Constants.ClusterAlg.DIRICHLET)) {
			Double treshold1 = 0.1;
			Double treshold2 = 0.2;
			CanopyClusterer cluster = new CanopyClusterer(new EuclideanDistanceMeasure(), treshold1, treshold2);

			if (cluster != null) {
				MainGUI.writeResult("OK building the clusters USER_Defined", Constants.Log.INFO);
			}

			else
				MainGUI.writeResult("error building the clusters", Constants.Log.ERROR);
		}

		return null;
	}

	public static void writeResult() {
		SequenceFile.Reader reader2 = null;
		if (hadoop) {
			try {
				reader2 = new SequenceFile.Reader(fs, new Path("output" + System.getProperty("file.separator")
						+ Cluster.CLUSTERED_POINTS_DIR + System.getProperty("file.separator") + "part-m-0"), conf);
			} catch (IOException e) {
				MainGUI.writeResult("Not able to read the clustered points file", Constants.Log.ERROR);
			}
		} else {
			try {
				reader2 = new SequenceFile.Reader(fs, new Path("output" + System.getProperty("file.separator")
						+ Cluster.CLUSTERED_POINTS_DIR + System.getProperty("file.separator") + "part-m-0"), conf);
			} catch (IOException e) {
				MainGUI.writeResult("Not able to read the clustered points file", Constants.Log.ERROR);
			}
		}
		IntWritable key2 = new IntWritable();
		WeightedVectorWritable value2 = new WeightedVectorWritable();
		try {
			while (reader2.next(key2, value2)) {
				System.out.println(value2.toString() + " belongs to cluster " + key2.toString());
			}
		} catch (IOException e) {
			MainGUI.writeResult("Not able to read the clustered points file", Constants.Log.ERROR);
		}
		try {
			reader2.close();
		} catch (IOException e) {
			MainGUI.writeResult("Not able to close the SequenceFile reader", Constants.Log.ERROR);
		}
	}

	public static void constructCluster(boolean kmeans) {
		List<Vector> vectors;

		if (isSequential()) {
			vectors = CreateSequenceFile.getVectors();

			testData = new File("testdata");
			if (!testData.exists()) {
				testData.mkdir();
			} else {
				// trying to delete testData every time a mahout job is launched
				startDeleting("testdata");

			}
			System.out.println("hola");
			testData = new File("testdata" + System.getProperty("file.separator") + "points");

			if (!testData.exists()) {
				testData.mkdir();
				Configuration conf = new Configuration();
				FileSystem fs = null;
				try {
					fs = FileSystem.get(conf);
				} catch (IOException e) {
					MainGUI.writeResult("Not able to configure the job", Constants.Log.ERROR);
				}
				try {
					writePointsToFile(vectors,
							"testdata" + System.getProperty("file.separator") + "points" + System.getProperty("file.separator") + "file1",
							fs, conf);
				} catch (IOException e) {
					MainGUI.writeResult("Not able to write the vector points to file", Constants.Log.ERROR);
				}
				// hadoop?
				Path path = new Path("help");
				if (!hadoop) {
					path = new Path("testdata" + System.getProperty("file.separator") + "clusters" + System.getProperty("file.separator")
							+ "part-00000");
				} else {
					path = new Path("testdata" + File.separatorChar + "clusters" + File.separatorChar + "part-m-0");
				}
				SequenceFile.Writer writer = null;
				try {
					writer = new SequenceFile.Writer(fs, conf, path, Text.class, Kluster.class);
				} catch (IOException e) {
					MainGUI.writeResult("Not able to create SequenceFile writer", Constants.Log.ERROR);
				}
				// ¿hadoop?
				if (!hadoop) {
					for (int i = 0; i < numero; i++) {
						Vector vec = vectors.get(i);
						Kluster cluster = new Kluster(vec, i, d);
						try {
							writer.append(new Text(cluster.getIdentifier()), cluster);
						} catch (IOException e) {
							MainGUI.writeResult("Not able to append the actual cluster to the writer", Constants.Log.ERROR);
						}
					}
				} else {
					for (int i = 0; i < numero; i++) {
						Vector vec = vectors.get(i);
						Kluster cluster = new Kluster(vec, i, d);
						try {
							writer.append(new Text(cluster.getIdentifier()), cluster);
						} catch (IOException e) {
							MainGUI.writeResult("Not able to append the actual cluster to the writer", Constants.Log.ERROR);
						}
					}
				}
				try {
					writer.close();
				} catch (IOException e) {
					MainGUI.writeResult("Not able to close the SequenceFile writer", Constants.Log.ERROR);
				}

				SequenceFile.Reader reader = null;
				try {
					reader = new SequenceFile.Reader(fs, path, conf);
				} catch (IOException e) {
					MainGUI.writeResult("Not able to generate a SequenceFile reader", Constants.Log.ERROR);
				}
				Text key = new Text();
				Kluster value = new Kluster();
				try {
					while (reader.next(key, value)) {
						System.out.println(value.toString());
					}
				} catch (IOException e) {
					MainGUI.writeResult("Not able to read <K,V> from the SequenceFile reader", Constants.Log.ERROR);
				}
				try {
					reader.close();
				} catch (IOException e) {
					MainGUI.writeResult("Not able to close the SequenceFile reader", Constants.Log.ERROR);
				}

				Path output = new Path("output");
				try {
					HadoopUtil.delete(conf, output);
				} catch (IOException e) {
					MainGUI.writeResult("Not able to delete configuration from output", Constants.Log.ERROR);
				}

				try {
					if (kmeans) {
						Path pointsPath = new Path(DataModelClusterPanel.getOutputPath());
						DisplayGraphicKMeans.runSequentialKMeansClusterer(conf, pointsPath, output, d, numero, iteraciones, t1);
						//MainGUI.writeResult("OK executing the task", Constants.Log.INFO);
					}

					else if (esCanopy) {
						Path pointsPath = new Path(DataModelClusterPanel.getOutputPath());
						CanopyDriver.run(conf, pointsPath, output, d, t1, t2, true, t1, !hadoop);
						//MainGUI.writeResult("OK executing the task", Constants.Log.INFO);
					}

					else {

						Path pointsPath = new Path(DataModelClusterPanel.getOutputPath());
						boolean emitMostLikely = AlgorithmClusterPanel.getEmitMostLikely().isSelected();
						String s = AlgorithmClusterPanel.getFuzzyFactor().getText();
						float fuzzyFactor = Float.parseFloat(s);

						DisplayGraphicFuzzy.runSequentialFuzzyKClusterer(conf, pointsPath, output, d, iteraciones, fuzzyFactor, t1);
						//MainGUI.writeResult("OK executing the task", Constants.Log.INFO);
					}
				} catch (ClassNotFoundException e) {
					MainGUI.writeResult("Not able to build the clusters", Constants.Log.ERROR);
				} catch (IOException e) {
					MainGUI.writeResult("Not able to build the clusters", Constants.Log.ERROR);
				} catch (InterruptedException e) {
					MainGUI.writeResult("Not able to build the clusters", Constants.Log.ERROR);
				}

				// write result
				SequenceFile.Reader reader2 = null;
				if (hadoop) {
					try {
						reader2 = new SequenceFile.Reader(fs, new Path("output" + File.separatorChar + Cluster.CLUSTERED_POINTS_DIR
								+ File.separatorChar + "part-m-00000"), conf);
					} catch (IOException e1) {
						MainGUI.writeResult("Not able to read the clustered points file", Constants.Log.ERROR);
					}
				} else {
					try {

						reader2 = new SequenceFile.Reader(fs, new Path("output" + File.separatorChar + Cluster.CLUSTERED_POINTS_DIR
								+ File.separatorChar + "part-m-0"), conf);
					} catch (IOException e1) {
						MainGUI.writeResult("Not able to read the clustered points file", Constants.Log.ERROR);
					}
				}
				IntWritable key2 = new IntWritable();
				WeightedVectorWritable value2 = new WeightedVectorWritable();
				try {

					while (reader2.next(key2, value2)) {
						String s = value2.toString() + " belongs to cluster " + key2.toString();
						System.out.println(s);// canopy
						MainGUI.writeResult(s, Constants.Log.RESULT);
					}

				} catch (IOException e) {
					MainGUI.writeResult("Not able to read a <K,V> from the reader", Constants.Log.ERROR);
				}
				try {
					reader.close();
				} catch (IOException e) {
					MainGUI.writeResult("Not able to close the reader", Constants.Log.ERROR);
				}
				MainGUI.writeResult("OK executing the task", Constants.Log.INFO);
			}
		} else {
			vectors = null;
			try {
				getHadoopJob();

			} catch (Exception e) {
				MainGUI.writeResult("Not able to run Hadoop Job", Constants.Log.ERROR);
			}
		}

	}

	public static void writePointsToFile(List<Vector> vectori, String fileName, FileSystem fs, Configuration conf) throws IOException {
		Path path = new Path(fileName);
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, LongWritable.class, VectorWritable.class);
		long recNum = 0;
		VectorWritable vec = new VectorWritable();
		for (Vector point : vectori) {
			vec.set(point);
			writer.append(new LongWritable(recNum++), vec);
		}
		writer.close();
		// log testdata/points/file1
		SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);
		LongWritable key = new LongWritable();
		VectorWritable value = new VectorWritable();
		while (reader.next(key, value)) {
			System.out.println(value.toString());
		}
		reader.close();

	}

	public static File getTestData() {
		return testData.getParentFile();
	}

	public static void setTestData(File testData) {
		ClusterBuilder.testData = testData;
	}

	public static boolean isSequential() {
		return !hadoop;
	}

	public static void setHadoop(boolean hadoop) {
		ClusterBuilder.hadoop = hadoop;
	}

	public static void getHadoopJob() {
		Configuration confHadoop = new Configuration();

		int i = 0;
		String[] args1 = new String[6];
		args1[i++] = "--input";
		args1[i++] = DataModelClusterPanel.getInputPath();

		args1[i++] = "--output";
		args1[i++] = DataModelClusterPanel.getOutputPath();

		args1[i++] = "--method";
		args1[i++] = "sequential";

		try {
			// crear el chunk
			ToolRunner.run(new SequenceFilesFromDirectory(), args1);

			i = 0;
			String[] args2 = new String[9];
			args2[i++] = "--input";
			args2[i++] = DataModelClusterPanel.getOutputPath();

			args2[i++] = "--output";
			String vec = DataModelClusterPanel.getOutputPath() + System.getProperty("file.separator") + "vectors";
			args2[i++] = vec;

			args2[i++] = "--overwrite";

			args2[i++] = "--logNormalize";
			args2[i++] = "--namedVector";
			args2[i++] = "--weight";
			args2[i++] = "tfidf";
			// crear los vectores
			ToolRunner.run(new SparseVectorsFromSequenceFiles(), args2);

			String clusterIn = DataModelClusterPanel.getOutputPath() + System.getProperty("file.separator") + "clusters";
			// runMapReduce(new Path(DataModelClusterPanel.getInputPath()),new
			// Path(DataModelClusterPanel.getOutputPath()));
			// crear los clusters iniciales
			CanopyDriver.run(confHadoop, new Path(vec + System.getProperty("file.separator") + "tfidf-vectors"), new Path(clusterIn), d,
					t1, 0.9, true, t1, !hadoop);

			i = 0;
			String[] args3 = new String[10];
			args3[i++] = "--input";
			args3[i++] = vec + System.getProperty("file.separator") + "tfidf-vectors";

			args3[i++] = "--randomSelectionPct";
			args3[i++] = "20";

			// args3[i++] = "--clusters";
			String clusterIn2 = DataModelClusterPanel.getOutputPath() + System.getProperty("file.separator") + "clusters";
			// args3[i++] = clusterIn2;

			args3[i++] = "--overwrite";
			args3[i++] = "--sequenceFiles";

			args3[i++] = "--method";
			args3[i++] = "mapreduce";

			args3[i++] = "-mro";
			args3[i++] = clusterIn2 + "s";

			ToolRunner.run(new Configuration(), new SplitInput(), args3);

			String inputPoints = vec + System.getProperty("file.separator") + "tfidf-vectors";// clusterIn2+"s";//
			String initialClusters = clusterIn + System.getProperty("file.separator") + "clusters-0-final";

			String outputK = vec + System.getProperty("file.separator") + "output";

			KMeansDriver.run(confHadoop, new Path(inputPoints), new Path(initialClusters), new Path(outputK), d, t1, iteraciones, true, t1,
					!hadoop);

			String read = outputK + System.getProperty("file.separator") + "clusters-" + /*(iteraciones-1)*/"1-final" + System.getProperty("file.separator")
					+ "part-r-00000";
		
			writeResultHadoop(confHadoop, read);
			MainGUI.writeResult("Hadoop Job OK", Constants.Log.INFO);
		} catch (Exception e) {

			MainGUI.writeResult("Not able to run Hadoop Job", Constants.Log.ERROR);
		}
	}

	private static int runMapReduce(Path input, Path output) throws IOException, ClassNotFoundException, InterruptedException {
		final String PREFIX_ADDITION_FILTER = PrefixAdditionFilter.class.getName();

		final String[] CHUNK_SIZE_OPTION = { "chunkSize", "chunk" };
		final String[] FILE_FILTER_CLASS_OPTION = { "fileFilterClass", "filter" };
		final String[] CHARSET_OPTION = { "charset", "c" };

		final int MAX_JOB_SPLIT_LOCATIONS = 1000000;

		final String[] KEY_PREFIX_OPTION = { "keyPrefix", "prefix" };
		final String BASE_INPUT_PATH = "baseinputpath";
		int chunkSizeInMB = 64;
		Configuration confHadoop = new Configuration();

		Job job = null;// = HadoopUtil.prepareJob(input, output,
						// MultipleTextFileInputFormat.class,
						// SequenceFilesFromDirectoryMapper.class, Text.class,
						// Text.class, SequenceFileOutputFormat.class,
						// confHadoop);

		Configuration jobConfig = job.getConfiguration();
		String keyPrefix = null;
		jobConfig.set(KEY_PREFIX_OPTION[0], keyPrefix);
		FileSystem fs = FileSystem.get(jobConfig);
		FileStatus fsFileStatus = fs.getFileStatus(input);
		String inputDirList = HadoopUtil.buildDirList(fs, fsFileStatus);
		jobConfig.set("baseinputpath", input.toString());

		long chunkSizeInBytes = chunkSizeInMB * 1024 * 1024;

		jobConfig.set("mapreduce.job.max.split.locations", String.valueOf(1000000));

		FileInputFormat.setInputPaths(job, inputDirList);

		FileInputFormat.setMaxInputSplitSize(job, chunkSizeInBytes);
		FileOutputFormat.setCompressOutput(job, true);

		boolean succeeded = job.waitForCompletion(true);
		if (!succeeded) {
			return -1;
		}
		return 0;
	}

	public static void writeResultHadoop(Configuration confHadoop, String file) {
		FileSystem fileSystem = null;
		// confHadoop.set("mapred.textoutputformat.separatorText", ",");
		try {
			fileSystem = FileSystem.get(confHadoop);
		} catch (IOException e1) {
			MainGUI.writeResult("Not able to create a Hadoop configuration", Constants.Log.ERROR);
		}
		SequenceFile.Reader reader = null;

		try {
			reader = new SequenceFile.Reader(fileSystem, new Path(file), confHadoop);
		} catch (IOException e) {
			MainGUI.writeResult("Not able to read Hadoop Job's result", Constants.Log.ERROR);
		}
		IntWritable key2 = new IntWritable();
		ClusterWritable value2 = new ClusterWritable();
		List<Vector> vectors = new ArrayList<Vector>();
		try {

			while (reader.next(key2, value2)) {

				Cluster cluster = value2.getValue();
				int id = cluster.getId();
				Vector center = cluster.getCenter();
				int features = center.getNumNonZeroElements();
				Vector radius = cluster.getRadius();
				long numObservations = cluster.getNumObservations();
				
				String salida = "Cluster :" + id +" Center :" + center + " Radius : " + radius + " Num Observations :"+ numObservations;
				vectors.add(center);
				MainGUI.writeResult(salida, Constants.Log.RESULT);
			}
		} catch (IOException e) {
			MainGUI.writeResult("Not able to read Hadoop Job's result", Constants.Log.ERROR);
		}

	}

	public static void deleteContent(File index) {
		String[] entries = index.list();
		for (String s : entries) {
			File currentFile = new File(index.getPath(), s);
			if (currentFile.isDirectory()) {
				deleteContent(currentFile);
			} else
				currentFile.delete();
		}
	}

	public static void startDeleting(String path) {
		List<String> filesList = new ArrayList<String>();
		List<String> folderList = new ArrayList<String>();
		fetchCompleteList(filesList, folderList, path);
		for (String filePath : filesList) {
			File tempFile = new File(filePath);
			tempFile.delete();
		}
		for (String filePath : folderList) {
			File tempFile = new File(filePath);
			tempFile.delete();
		}
	}

	private static void fetchCompleteList(List<String> filesList, List<String> folderList, String path) {
		File file = new File(path);
		File[] listOfFile = file.listFiles();
		for (File tempFile : listOfFile) {
			if (tempFile.isDirectory()) {
				folderList.add(tempFile.getAbsolutePath());
				fetchCompleteList(filesList, folderList, tempFile.getAbsolutePath());
			} else {
				filesList.add(tempFile.getAbsolutePath());
			}

		}

	}
}
