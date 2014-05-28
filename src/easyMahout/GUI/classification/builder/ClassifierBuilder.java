package easyMahout.GUI.classification.builder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.mahout.math.Vector;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;
import org.apache.mahout.classifier.AbstractVectorClassifier;
import org.apache.mahout.classifier.naivebayes.*;
import org.apache.mahout.classifier.naivebayes.test.TestNaiveBayesDriver;
import org.apache.mahout.classifier.naivebayes.training.TrainNaiveBayesJob;
import org.apache.mahout.classifier.sgd.*;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.classification.MainClassifierPanel;
import easyMahout.GUI.clustering.builder.CreateSequenceFile;
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
	
	public static AbstractVectorClassifier buildClassifier() throws Exception{
		
		hayError = false;
		
		algorithm = MainClassifierPanel.getAlgorithmClassifierPanel().getSelectedType();
		
		boolean naiveBayes, complementaryNaiveBayes, sgd;
		
		naiveBayes = algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES);
		complementaryNaiveBayes = algorithm.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES);
		sgd = algorithm.equals(Constants.ClassificatorAlg.SGD);
		
		argsClassifier = JobClassifierBuilder.buildClassifierJob();
			
		if (naiveBayes || complementaryNaiveBayes){
			buildBayesClassifier();
		}
		
		if (sgd){
			buildSGDClassifier();			
		}
		
		MainGUI.writeResult("OK building the classifier: Algorithm "+ algorithm, Constants.Log.INFO);
		
		return null;
		
		//NAIVEBAYES
		/*if(algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES)){
			
			//Recoger variables necesarias para construccion
			
			StandardNaiveBayesClassifier classifier = null;
			if (!hayError){
				NaiveBayesModel model = ModelBuilder.createNaiveBayesModel();
				classifier = new StandardNaiveBayesClassifier(model);
			}
			if (classifier != null) {
				constructClassifier();
				MainGUI.writeResult("OK building the classifier: Algorithm "+ algorithm, Constants.Log.INFO);
			} else { 					
				log.error("Error building the classifier");
				MainGUI.writeResult("Error building the classifier", Constants.Log.ERROR);
			}
			
			return classifier;
		}
		//COMPLEMENTARY NAIVE BAYES
		else if(algorithm.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES)){
			
			//Recoger variables necesarias para construccion
			
			ComplementaryNaiveBayesClassifier classifier = null;
			if (!hayError){
				NaiveBayesModel model = ModelBuilder.createNaiveBayesModel();
				classifier = new ComplementaryNaiveBayesClassifier(model); //Pasarle el modelo
			}
			if (classifier != null) {
				constructClassifier();
				MainGUI.writeResult("OK building the classifier: Algorithm "+ algorithm, Constants.Log.INFO);
			} else { 					
				log.error("Error building the classifier");
				MainGUI.writeResult("Error building the classifier", Constants.Log.ERROR);
			}
			
			return classifier;
		}
		//SGD
		else if(algorithm.equals(Constants.ClassificatorAlg.SGD)){
			
			//Recoger variables necesarias para construccion
			
			OnlineLogisticRegression classifier = null;
			if (!hayError){
				classifier = new OnlineLogisticRegression();
			}
			if (classifier != null) {
				constructClassifier();
				MainGUI.writeResult("OK building the classifier: Algorithm "+ algorithm, Constants.Log.INFO);
			} else { 					
				log.error("Error building the classifier");
				MainGUI.writeResult("Error building the classifier", Constants.Log.ERROR);
			}
			
			return classifier;
		}
		
  		return null;*/		
	}
	
	private static void buildBayesClassifier() throws Exception {
		
		//Sequence files from directory
		
		MainGUI.writeResult("SeqDirectory",Constants.Log.INFO);
		
		ToolRunner.run(new SequenceFilesFromDirectory(), argsClassifier[0]);
		
		//Sequence files to Sparse Vectors		
		
		MainGUI.writeResult("SparseVectors",Constants.Log.INFO);
		
		ToolRunner.run(new SparseVectorsFromSequenceFiles(), argsClassifier[1]);
		
		//Split input
		
		MainGUI.writeResult("SplitInput",Constants.Log.INFO);
		
		ToolRunner.run(new Configuration(), new SplitInput(), argsClassifier[2]);
		
		//Train Bayes
		
		MainGUI.writeResult("TrainNaiveBayes",Constants.Log.INFO);
		
		ToolRunner.run(new Configuration(), new TrainNaiveBayesJob(), argsClassifier[3]);
		
		//Test Bayes
		
		MainGUI.writeResult("TestNaiveBayes",Constants.Log.INFO);
		
		ToolRunner.run(new Configuration(), new TestNaiveBayesDriver(), argsClassifier[4]);
		
	}
	
	private static void buildSGDClassifier() {
		
	}


	public static void constructClassifier(){
		List<Vector> vectors =  CreateSequenceFile.getVectors();
		
		//Pasar a sparseVectors --> SparseVectorsFromSequenceFiles.run()
		
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
