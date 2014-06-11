package easyMahout.GUI.classification.builder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra.system_add_column_family_args;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.utils.SplitInputJob;
//import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;
import org.apache.mahout.classifier.AbstractVectorClassifier;
import org.apache.mahout.classifier.ClassifierResult;
import org.apache.mahout.classifier.ResultAnalyzer;
import org.apache.mahout.classifier.naivebayes.*;
import org.apache.mahout.classifier.naivebayes.test.TestNaiveBayesDriver;
import org.apache.mahout.classifier.naivebayes.training.TrainNaiveBayesJob;
import org.apache.mahout.classifier.sgd.*;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.PathFilters;
import org.apache.mahout.common.iterator.sequencefile.PathType;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileDirIterable;
import org.omg.CORBA.CharSeqHolder;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.PreferencesPanel;
import easyMahout.GUI.classification.MainClassifierPanel;
import easyMahout.GUI.clustering.builder.CreateSequenceFile;
import easyMahout.GUI.recommender.builder.ShellScriptBuilder;
import easyMahout.GUI.recommender.builder.ShowCommandlineBuilder;
import easyMahout.utils.Constants;

public class ClassifierBuilder {

	private final static Logger log = Logger.getLogger(ClassifierBuilder.class);

	private static String algorithm;
	private static int numCharacteristics;
	private static FileSystem fs;
	private static Configuration conf;
	private static boolean hadoop = MainGUI.isDistributed();
	private static boolean hayError;

	private static File testData;

	private static String[][] argsClassifier;

	public static AbstractVectorClassifier buildClassifier() throws Exception {

		hayError = false;

		algorithm = MainClassifierPanel.getAlgorithmClassifierPanel()
				.getSelectedType();

		boolean naiveBayes, complementaryNaiveBayes, sgd;

		naiveBayes = algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES);
		complementaryNaiveBayes = algorithm
				.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES);
		sgd = algorithm.equals(Constants.ClassificatorAlg.SGD);

		argsClassifier = JobClassifierBuilder.buildClassifierJob();

		if (naiveBayes || complementaryNaiveBayes) {
			buildBayesClassifier();
		}

		if (sgd) {
			buildSGDClassifier();
		}

		MainGUI.writeResult("OK building the classifier: Algorithm "
				+ algorithm, Constants.Log.INFO);

		return null;
	}

	private static void buildBayesClassifier() throws Exception {

		String[][] args = JobClassifierBuilder.buildNaiveBayesJob(false);
		String scriptTemp = ShellClassifierScriptBuilder
				.buildClassifierScript(args);

		String javaHome = "JAVA_HOME=" + PreferencesPanel.getJavaHome();
		String hadoopHome = "HADOOP_HOME=" + System.getenv("PWD") + "/"
				+ PreferencesPanel.getHadoopHome();
		Process proc = Runtime.getRuntime().exec("/bin/sh " + scriptTemp,
				new String[] { javaHome, hadoopHome });
		int exitValue = proc.waitFor();

		if (exitValue == 0) {

			String absPath = writeResultFile(args, proc);

			MainGUI.writeResult("TXT Result file created: " + absPath,
					Constants.Log.INFO);

		} else {
			MainGUI.writeResult("Error running the classifier Job.",
					Constants.Log.ERROR);
		}

		Path path = new Path(scriptTemp);
		fs = FileSystem.get(conf);
		if (fs.exists(path)) {
			fs.delete(path, true);
		}

	}

	private static String writeResultFile(String[][] args, Process proc)
			throws IOException {
		BufferedReader is = new BufferedReader(new InputStreamReader(
				proc.getErrorStream()));

		StringBuilder textBuilder1 = new StringBuilder();
		StringBuilder textBuilder2 = new StringBuilder();
		String line;
		boolean matrix = false;
		boolean second = false;

		while ((line = is.readLine()) != null) {
			if (line.contains("Standard NB Results:")) {
				matrix = true;
				if (!second) {
					MainGUI.writeResult("Confusion matrix of Train subset:",
							Constants.Log.RESULT);
					MainGUI.writeResultClassifier("");
				} else {
					MainGUI.writeResult("Confusion matrix of Test subset:",
							Constants.Log.RESULT);
					MainGUI.writeResultClassifier("");
				}
			}

			if (matrix && !second) {
				textBuilder1.append(line).append("\n");
				MainGUI.writeResultClassifier(line);
			} else if (matrix && second) {
				textBuilder2.append(line).append("\n");
				MainGUI.writeResultClassifier(line);
			}

			if (line.contains("(standard deviation)")) {
				matrix = false;
				second = true;
			}
		}

		String absPath = args[5][5] + "/result.txt";

		FileWriter fileW = null;
		PrintWriter pw = null;
		try {
			fileW = new FileWriter(absPath);
			pw = new PrintWriter(fileW);
			pw.print("Confusion matrix of Train subset:\n\n");
			pw.print(textBuilder1.toString());
			pw.print("\n\n\nConfusion matrix of Test subset: \n\n");
			pw.print(textBuilder2.toString());

		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (null != fileW)
					fileW.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return absPath;
	}

	private static void buildSGDClassifier() {

	}

	public static void constructClassifier() {
		List<Vector> vectors = CreateSequenceFile.getVectors();

		// Pasar a sparseVectors --> SparseVectorsFromSequenceFiles.run()

		testData = new File("testdata");
		if (!testData.exists()) {
			testData.mkdir();
		}

		Configuration conf = new Configuration();
		FileSystem fs = null;
		try {
			fs = FileSystem.get(conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
