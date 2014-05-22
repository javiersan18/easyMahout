package easyMahout.GUI.classification.builder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.classification.AlgorithmClassifierPanel;
import easyMahout.GUI.classification.DataModelClassifierPanel;
import easyMahout.utils.Constants;

public class JobClassifierBuilder {
	
	private static final Logger log = Logger.getLogger(JobClassifierBuilder.class);
	
	private static boolean hadoop = MainGUI.isDistributed();
	
	private static final int ARGS_SEQDIRECTORY = 7;

	private static final int ARGS_SEQ2SPARSE = 17;
	
	private static final int ARGS_SPLIT = 17;
	
	private static final int ARGS_TRAIN_NB = 10;
	
	private static final int ARGS_TEST_NB = 10;

	private static final int ARGS_TRAIN_SGD = 10;
	
	private static final int ARGS_TEST_SGD = 10;
	
	private static String algorithm = AlgorithmClassifierPanel.getSelectedType();
	
	private static String inputPath, outputPath, modelPath, modelSGDPath;
	
	private static String auxPath1, auxPath2, trainPath, testPath, labelIndexPath;
	
	public static final String[][] buildClassifierJob() {
		
		inputPath = DataModelClassifierPanel.getInputPath();
		outputPath = DataModelClassifierPanel.getOutputPath();
		
		if (StringUtils.isBlank(inputPath)) {
			MainGUI.writeResult("Input folder is empty, please choose one before run the classifier.", Constants.Log.ERROR);
			return null;
		} 
		if (StringUtils.isBlank(outputPath)) {
			MainGUI.writeResult("Output folder is empty, please choose one before run the classifier.", Constants.Log.ERROR);
			return null;
		}	
		
		boolean naiveBayes, complementaryNaiveBayes, sgd;
			
		naiveBayes = algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES);
		complementaryNaiveBayes = algorithm.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES);
		sgd = algorithm.equals(Constants.ClassificatorAlg.SGD);
			
		if (naiveBayes || complementaryNaiveBayes){
			return buildNaiveBayesJob(complementaryNaiveBayes);
		}
		
		if (sgd){
			return buildSGDJob();
		}
		
		return null;
	}
		
	public static final String[][] buildNaiveBayesJob(boolean complementary){
		
		String[][] args = null;	
		args = new String[5][];
		
		//Args seqdirectory
		
		String[] argsSeqFile = args[0] = new String[ARGS_SEQDIRECTORY];
		
		auxPath1 = "classifier/NaiveBayes/intermediateResults/seqDirectory";
		
		int i = 0;
		argsSeqFile[i] = "--input";
		argsSeqFile[++i] = inputPath;
		argsSeqFile[++i] = "--output";
		argsSeqFile[++i] = auxPath1;
		argsSeqFile[++i] = "-ow";
		argsSeqFile[++i] = "--method";
		if(hadoop){
			argsSeqFile[++i] = "mapreduce";
		} else {
			argsSeqFile[++i] = "sequential";
		}
		//charset, tempDir, startPhase, endPhase, keyPrefix, fileFilterClass, chunkSize
		
		//Args Seq2Sparse
		
		String[] argsSeq2Sparse = args[1] = new String[ARGS_SEQ2SPARSE];
		
		auxPath2 = "classifier/NaiveBayes/intermediateResults/seq2Sparse";
		
		i = 0;
		argsSeq2Sparse[i] = "--input";
		argsSeq2Sparse[++i] = auxPath1;
		argsSeq2Sparse[++i] = "--output";
		argsSeq2Sparse[++i] = auxPath2;
		argsSeq2Sparse[++i] = "-ow";
		argsSeq2Sparse[++i] = "--logNormalize";		//Optional
		argsSeq2Sparse[++i] = "--namedVector";   //Optional
		argsSeq2Sparse[++i] = "--weight";
		argsSeq2Sparse[++i] = "tfidf";				//TF o TFIDT
		// numReducers, minDF, maxDFPercent, MAXDFSigma, norm, minLLR, maxNGramSize...
		
		//Args Split
		
		String[] argsSplit = args[2] = new String[ARGS_SPLIT];
		
		trainPath = "classifier/NaiveBayes/train";
		testPath =  "classifier/NaiveBayes/test";
		
		i = 0;
		argsSplit[i] = "--input";
		argsSplit[++i] = auxPath2;
		argsSplit[++i] = "--trainingOutput";
		argsSplit[++i] = trainPath;
		argsSplit[++i] = "--testOutput";
		argsSplit[++i] = testPath;
		argsSplit[++i] = "-ow";
		//También se puede apartir de un porcentaje de la entrada(50 = mitad hacia arriba test) o porcentaje o tam. fijo de cada categoría
		/*if(testPercent){
			argsSplit[++i] = "--randomSelectionPct"; 	//porcentaje
			argsSplit[++i] = "20";
		} else {*/
			argsSplit[++i] = "--randomSelectionSize"; 	//tamaño de items fijo 
			argsSplit[++i] = "100";
		//}
		argsSplit[++i] = "--sequenceFiles";			//Optional: If the input data are seqFiles	
		argsSplit[i++] = "--method";
		if(hadoop){
			argsSplit[++i] = "mapreduce";
		} else {
			argsSplit[++i] = "sequential";
		}
		
		//Args train
		
		String[] argsTrain = args[3] = new String[ARGS_TRAIN_NB];
		
		labelIndexPath = "classifier/NaiveBayes/labelIndex";
		modelPath = "classifier/NaiveBayes/model";
		
		i = 0;
		argsTrain[i] = "--input";
		argsTrain[++i] = trainPath;
		argsTrain[++i] = "--output";
		argsTrain[++i] = modelPath;
		argsTrain[++i] = "-ow";
		argsTrain[++i] = "--extractLabels";			//O extraer o introducirlas --labels label1,label2,...
		argsTrain[++i] = "--labelIndex";
		argsTrain[++i] = labelIndexPath;
		if(complementary){
			argsTrain[++i] = "--trainComplementary";				
		}
		//alphaI, startPhase, endPhase...
		
		//Args test
		
		String[] argsTest = args[4] = new String[ARGS_TEST_NB];
		
		i = 0;
		argsTest[i] = "--input";
		argsTest[++i] = testPath;
		argsTest[++i] = "--model";
		argsTest[++i] = modelPath;
		argsTest[++i] = "--output";
		argsTest[++i] = outputPath;
		argsTest[++i] = "-ow";
		argsTest[++i] = "--labelIndex";
		argsTest[++i] = labelIndexPath;
		if(complementary){
			argsTest[++i] = "--testComplementary";				
		}
		if(!hadoop){
			argsTest[++i] = "--runSequential";
		}
		//tempDir, startPhase, endPhase...
		
		return args;		
	}
	
	public static final String[][] buildSGDJob(){
		
		String[][] args = null;	
		args = new String[5][];
		
		//Args train
		
		String[] argsTrainSGD = args[0] = new String[ARGS_TRAIN_SGD];
		
		modelSGDPath = "classifier/SGD/model";
		
		//Recoger datos interfaz
		
		int i = 0;
		argsTrainSGD[i] = "--input";
		argsTrainSGD[++i] = inputPath;
		argsTrainSGD[++i] = "--output";
		argsTrainSGD[++i] = modelSGDPath;
		argsTrainSGD[++i] = "--target";
		//argsTrainSGD[++i] = target;
		argsTrainSGD[++i] = "--categories";
		//argsTrainSGD[++i] = numCategories;
		argsTrainSGD[++i] = "--predictors";
		//argsTrainSGD[++i] = "pred1 pred2";  //Volcar en un string separado por espacios
		argsTrainSGD[++i] = "--types";
		//argsTrainSGD[++i] = "type1 type2..."; //Volcar en un string separado por espacios
		argsTrainSGD[++i] = "--passes";
		//argsTrainSGD[++i] = passes;
		argsTrainSGD[++i] = "--lambda";
		//argsTrainSGD[++i] = lambda;
		argsTrainSGD[++i] = "--rate";
		//argsTrainSGD[++i] = rate;
		argsTrainSGD[++i] = "--lambda";
		//argsTrainSGD[++i] = lambda;
		argsTrainSGD[++i] = "--noBias"; 	//Optional??
		argsTrainSGD[++i] = "--features";
		//argsTrainSGD[++i] = numFeatures;

		//argsTrainSGD[++i] = "-ow";	//Se puede??
		
		return args;		
	}

}
