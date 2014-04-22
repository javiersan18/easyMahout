package easyMahout.GUI.classification.builder;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Locale;

import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.sgd.AdaptiveLogisticRegression;
import org.apache.mahout.classifier.sgd.CrossFoldLearner;
import org.apache.mahout.classifier.sgd.CsvRecordFactory;
import org.apache.mahout.classifier.sgd.ElasticBandPrior;
import org.apache.mahout.classifier.sgd.L1;
import org.apache.mahout.classifier.sgd.L2;
import org.apache.mahout.classifier.sgd.PriorFunction;
import org.apache.mahout.classifier.sgd.TPrior;
import org.apache.mahout.classifier.sgd.UniformPrior;
import org.apache.mahout.classifier.sgd.AdaptiveLogisticRegression.Wrapper;
import org.apache.mahout.ep.State;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;

import com.google.common.base.Charsets;

import easyMahout.GUI.classification.MainClassifierPanel;

public class ModelBuilder {
	
	private static AdaptiveLogisticRegression model;
	
	private int targetCategories;
	private int numFeatures;
	
	private int passes;
	
	private String prior = "L1";
	private double priorOption = Double.NaN;
	
	private int interval = 800;
	private static int averageWindow = 500;
	private int threads = 4;
	
	private static boolean showperf;  //Pedir por interfaz ¿¿Que es??
	private static int skipperfnum = 99;

	private String inputFile;
	private String outputFile;

	private PrintWriter output;
	
	//NAIVE BAYES
	
	public static NaiveBayesModel createNaiveBayesModel(){
		NaiveBayesModel model = new NaiveBayesModel(null, null, null, null, averageWindow);
		model.validate();

		return model;
	}
	
	//SGD
	
	public void createLogisticModel(){
		if (model == null) {
		      model = new AdaptiveLogisticRegression(targetCategories, numFeatures, 
		    		  									createPrior(prior, priorOption));
		      model.setInterval(interval);
		      model.setAveragingWindow(averageWindow);
		      model.setThreadCount(threads);
		      //model.setAucEvaluator(createAUC(auc));
	    }		
	}	
	
	public void trainLogisticModel() throws IOException{
		if (model != null){
			//model.train(actual, instance);
			
			CsvRecordFactory csv = getCsvRecordFactory();
			State<Wrapper, CrossFoldLearner> best;
		    CrossFoldLearner learner = null;
		    
		    output = new PrintWriter(new OutputStreamWriter(System.out, Charsets.UTF_8), true);

		    int k = 0;
		    for (int pass = 0; pass < passes; pass++) {
		        BufferedReader in = open(inputFile);

		        // read variable names
		        csv.firstLine(in.readLine());

		        String line = in.readLine();
		        while (line != null) {
		          // for each new line, get target and predictors
		          Vector input = new RandomAccessSparseVector(numFeatures);
		          int targetValue = csv.processLine(line, input);

		          // update model
		          model.train(targetValue, input);
		          k++;

		          if (showperf && (k % (skipperfnum + 1) == 0)) {

		            best = model.getBest();
		            if (best != null) {
		              learner = best.getPayload().getLearner();
		            }
		            if (learner != null) {
		              double averageCorrect = learner.percentCorrect();
		              double averageLL = learner.logLikelihood();
		              output.printf("%d\t%.3f\t%.2f%n",
		                            k, averageLL, averageCorrect * 100);
		            } else {
		              output.printf(Locale.ENGLISH,
		                            "%10d %2d %s%n", k, targetValue,
		                            "AdaptiveLogisticRegression has not found a good model ......");
		            }
		          }
		          line = in.readLine();
		        }
		        in.close();
		      }
		    
		      best = model.getBest();
		      if (best != null) {
		        learner = best.getPayload().getLearner();
		      }
		      if (learner == null) {
		        output.println("AdaptiveLogisticRegression has failed to train a model.");
		        return;
		      }


		      OutputStream modelOutput = new FileOutputStream(outputFile);
		      try {
		        saveTo(modelOutput);
		      } finally {
		        modelOutput.close();
		      }
		      
		      /* Falta un cacho de código de TrainAdaptiveLogistic*/
		}	
	}
	
	private CsvRecordFactory getCsvRecordFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	private BufferedReader open(String inputFile2) {
		// TODO Auto-generated method stub
		return null;
	}

	private void saveTo(OutputStream modelOutput) {
		// TODO Auto-generated method stub
		
	}

	public void getParameters(){
		inputFile = null;   //Ruta donde recoge el csv con los datos el DataModelPanel
		
		outputFile = null; //Ruta donde guarda el modelo.
		
		targetCategories = MainClassifierPanel.getDataDefinitionsClassifierPanel().getTargetCategories();
		
		numFeatures = MainClassifierPanel.getTrainingDataClassifierPanel().getNumFeatures();
		
		passes = MainClassifierPanel.getTrainingDataClassifierPanel().getPasses();	
		
	}
	
	private static PriorFunction createPrior(String cmd, double priorOption) {
	    if (cmd == null) {
	      return null;
	    }
	    if ("L1".equals(cmd.toUpperCase(Locale.ENGLISH).trim())) {
	      return new L1();
	    }
	    if ("L2".equals(cmd.toUpperCase(Locale.ENGLISH).trim())) {
	      return new L2();
	    }
	    if ("UP".equals(cmd.toUpperCase(Locale.ENGLISH).trim())) {
	      return new UniformPrior();
	    }
	    if ("TP".equals(cmd.toUpperCase(Locale.ENGLISH).trim())) {
	      return new TPrior(priorOption);
	    }
	    if ("EBP".equals(cmd.toUpperCase(Locale.ENGLISH).trim())) {
	      return new ElasticBandPrior(priorOption);
	    }

	    return null;
	  }

	public static AdaptiveLogisticRegression getModel() {
		return model;
	}

	public static void setModel(AdaptiveLogisticRegression model) {
		ModelBuilder.model = model;
	}

}
