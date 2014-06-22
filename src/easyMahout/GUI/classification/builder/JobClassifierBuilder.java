package easyMahout.GUI.classification.builder;

import org.apache.log4j.Logger;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.classification.AlgorithmClassifierPanel;
import easyMahout.GUI.classification.DataModelClassifierPanel;
import easyMahout.GUI.classification.TestDataClassifierPanel;
import easyMahout.GUI.classification.TestModelClassifierPanel;
import easyMahout.utils.Constants;

public class JobClassifierBuilder {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(JobClassifierBuilder.class);

	private static boolean hadoop = MainGUI.isDistributed();

	private static final int ARGS_SEQDIRECTORY = 7;

	private static final int ARGS_SEQ2SPARSE = 9;

	private static final int ARGS_SPLIT = 12;

	private static final int ARGS_TRAIN_NB = 8; 
	
	private static final int ARGS_TRAIN_CNB = 9; 

	private static final int ARGS_TEST_NB = 9;// +1 si sequential
	
	private static final int ARGS_TEST_CNB = 10;

	private static final int ARGS_TRAIN_SGD = 10;

	private static String algorithm = AlgorithmClassifierPanel.getSelectedType();

	private static String inputPath, outputPath, modelPath, modelSGDPath;

	private static String auxPath1, auxPath2, trainPath, testPath, labelIndexPath;

	private static String randomPct;

	private static boolean random;

	private static String randomSize;

	private static boolean percent;

	private static boolean location;

	private static String locationPct;

	private static String testPct;

	private static String testSize;

	public static final String[][] buildClassifierJob() {

		inputPath = DataModelClassifierPanel.getInputPath();
		outputPath = DataModelClassifierPanel.getOutputPath();

		boolean naiveBayes, complementaryNaiveBayes;//, sgd;

		naiveBayes = algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES);
		complementaryNaiveBayes = algorithm.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES);
		//sgd = algorithm.equals(Constants.ClassificatorAlg.SGD);

		if (naiveBayes || complementaryNaiveBayes) {
			return buildNaiveBayesJob(complementaryNaiveBayes);
		}

		/*if (sgd) {
			return buildSGDJob();
		}*/

		return null;
	}

	public static final String[][] buildNaiveBayesJob(boolean complementary) {

		String[][] args = null;
		args = new String[6][];

		// Args seqdirectory

		String[] argsSeqFile = args[0] = new String[ARGS_SEQDIRECTORY];

		auxPath1 = outputPath + "/intermediateResults/seqDirectory";

		int i = 0;
		argsSeqFile[i] = "--input";
		argsSeqFile[++i] = inputPath;
		argsSeqFile[++i] = "--output";
		argsSeqFile[++i] = auxPath1;
		argsSeqFile[++i] = "-ow";
		argsSeqFile[++i] = "--method";
		if (hadoop) {
			argsSeqFile[++i] = "mapreduce";
		} else {
			argsSeqFile[++i] = "sequential";
		}
		// charset, tempDir, startPhase, endPhase, keyPrefix, fileFilterClass,
		// chunkSize

		// Args Seq2Sparse

		String[] argsSeq2Sparse = args[1] = new String[ARGS_SEQ2SPARSE];

		auxPath2 = outputPath + "/intermediateResults/seq2Sparse";

		i = 0;
		argsSeq2Sparse[i] = "--input";
		argsSeq2Sparse[++i] = auxPath1;
		argsSeq2Sparse[++i] = "--output";
		argsSeq2Sparse[++i] = auxPath2;
		argsSeq2Sparse[++i] = "-ow";
		argsSeq2Sparse[++i] = "--logNormalize"; // Optional
		argsSeq2Sparse[++i] = "--namedVector"; // Optional
		argsSeq2Sparse[++i] = "--weight";
		argsSeq2Sparse[++i] = "tfidf"; // TF o TFIDT
		// numReducers, minDF, maxDFPercent, MAXDFSigma, norm, minLLR,
		// maxNGramSize...

		// Args Split

		String[] argsSplit;

		if (!hadoop) {
			argsSplit = args[2] = new String[ARGS_SPLIT];
		} else {
			argsSplit = args[2] = new String[ARGS_SPLIT];
		}

		trainPath = outputPath + "/train";
		testPath = outputPath + "/test";
		// mapRedOutPath = "/mapReduce";
		
		
		random = TestDataClassifierPanel.getRandom();
		percent = TestDataClassifierPanel.isPct();
		location = TestDataClassifierPanel.getLocationSplit();			

		i = 0;
		argsSplit[i] = "--input";
		argsSplit[++i] = auxPath2 + "/tfidf-vectors";
		argsSplit[++i] = "--trainingOutput";
		argsSplit[++i] = trainPath;
		argsSplit[++i] = "--testOutput";
		argsSplit[++i] = testPath;
		argsSplit[++i] = "-ow";
		argsSplit[++i] = "--sequenceFiles"; // Optional: If the input data are
											// seqFiles
		argsSplit[++i] = "--method";
		argsSplit[++i] = "sequential";
		if(random){
			if(percent){
				randomPct = TestDataClassifierPanel.getPct();
				argsSplit[++i] = "--randomSelectionPct";				
				argsSplit[++i] = randomPct;				
			} else {
				randomSize = TestDataClassifierPanel.getSizeSelection();
				argsSplit[++i] = "--randomSelectionSize";
				argsSplit[++i] = randomSize;
			}
		} else {
			if(location){
				locationPct = TestDataClassifierPanel.getPct();
				argsSplit[++i] = "--splitLocation";				
				argsSplit[++i] = locationPct;	
			} else {
				if(percent){
					testPct = TestDataClassifierPanel.getPct();
					argsSplit[++i] = "--testSplitPct";				
					argsSplit[++i] = testPct;	
				} else {
					testSize = TestDataClassifierPanel.getSizeSelection();
					argsSplit[++i] = "--testSplitSize"; 
					argsSplit[++i] = testSize;
				}
			}
		}
		
//		--testSplitSize (-ss) testSplitSize: The number of documents held back as test data for each category              
//		--testSplitPct (-sp) testSplitPct: The % of documents held back as test data for each category                   
//		--splitLocation (-sl) splitLocation: Location for start of test data expressed as a percentage of the input file size (0=start,50=middle, 100=end         
//		--randomSelectionSize (-rs) randomSelectionSize: The number of items to be randomly selected as test data                       
//		--randomSelectionPct (-rp) randomSelectionPct: Percentage of items to be randomly selected as test data when using mapreduce mode                       


		// Tambien se puede apartir de un porcentaje de la entrada(50 = mitad
		// hacia arriba test) o porcentaje o tam. fijo de cada categor�a

		// Args train
		
		String[] argsTrain;

		if(!complementary){ 
			argsTrain = args[3] = new String[ARGS_TRAIN_NB];
		} else{		
			argsTrain = args[3] = new String[ARGS_TRAIN_CNB];
		}

		labelIndexPath = outputPath + "/labelIndex";
		modelPath = outputPath + "/model";

		i = 0;
		argsTrain[i] = "--input";
		argsTrain[++i] = trainPath;
		argsTrain[++i] = "--output";
		argsTrain[++i] = modelPath;
		argsTrain[++i] = "-ow";
		argsTrain[++i] = "--extractLabels"; // O extraer o introducirlas
											// --labels label1,label2,...
		argsTrain[++i] = "--labelIndex";
		argsTrain[++i] = labelIndexPath;
		if (complementary) {
			argsTrain[++i] = "--trainComplementary";
		}
		// alphaI, startPhase, endPhase...

		// Args test
		
		String[] argsTestnb1;

		if(!complementary){ 
			argsTestnb1 = args[4] = new String[ARGS_TEST_NB];
		} else{		
			argsTestnb1 = args[4] = new String[ARGS_TEST_CNB];
		}

		i = 0;
		argsTestnb1[i] = "--input";
		argsTestnb1[++i] = testPath;
		argsTestnb1[++i] = "--model";
		argsTestnb1[++i] = modelPath;
		argsTestnb1[++i] = "--output";
		argsTestnb1[++i] = outputPath + "/result";
		argsTestnb1[++i] = "-ow";
		argsTestnb1[++i] = "--labelIndex";
		argsTestnb1[++i] = labelIndexPath;
		if (complementary) {
			argsTestnb1[++i] = "--testComplementary";
		}
		if (!hadoop) {
			//argsTest[++i] = "--runSequential";
		}
		// tempDir, startPhase, endPhase...
		
		
		String[] argsTestnb2;
		
		if(!complementary){ 
			argsTestnb2 = args[5] = new String[ARGS_TEST_NB];
		} else{		
			argsTestnb2 = args[5] = new String[ARGS_TEST_CNB];
		}

		i = 0;
		argsTestnb2[i] = "--input";
		argsTestnb2[++i] = trainPath;
		argsTestnb2[++i] = "--model";
		argsTestnb2[++i] = modelPath;
		argsTestnb2[++i] = "--output";
		argsTestnb2[++i] = outputPath + "/result";
		argsTestnb2[++i] = "-ow";
		argsTestnb2[++i] = "--labelIndex";
		argsTestnb2[++i] = labelIndexPath;
		if (complementary) {
			argsTestnb2[++i] = "--testComplementary";
		}
		
		

		return args;
	}
	
	public static final String[][] testClassifierJob() {

		inputPath = TestModelClassifierPanel.getInputPath();
		modelPath = TestModelClassifierPanel.getModelPath();
		outputPath = TestModelClassifierPanel.getOutputPath();
		labelIndexPath = TestModelClassifierPanel.getLabelIndexPath(); 

		boolean naiveBayes, complementaryNaiveBayes;//, sgd;

		naiveBayes = algorithm.equals(Constants.ClassificatorAlg.NAIVEBAYES);
		complementaryNaiveBayes = algorithm.equals(Constants.ClassificatorAlg.COMPNAIVEBAYES);
		//sgd = algorithm.equals(Constants.ClassificatorAlg.SGD);

		if (naiveBayes || complementaryNaiveBayes) {
				return testNaiveBayesJob(complementaryNaiveBayes);
		}

		/*if (sgd) {
			return buildSGDJob();
		}*/

		return null;
	}
	
	public static final String[][] testNaiveBayesJob(boolean complementary) {
		
		String[][] args = null;
		args = new String[1][];
		
		String[] argsTest;
		
		if(!complementary){ 
			argsTest = args[0] = new String[ARGS_TEST_NB];
		} else{		
			argsTest = args[0] = new String[ARGS_TEST_CNB];
		}

		
		int i;

		i = 0;
		argsTest[i] = "--input";
		argsTest[++i] = trainPath;
		argsTest[++i] = "--model";
		argsTest[++i] = modelPath;
		argsTest[++i] = "--output";
		argsTest[++i] = outputPath + "/result";
		argsTest[++i] = "-ow";
		argsTest[++i] = "--labelIndex";
		argsTest[++i] = labelIndexPath;
		if (complementary) {
			argsTest[++i] = "--testComplementary";
		}
		
		return args;
	}

	public static final String[][] buildSGDJob() {

		String[][] args = null;
		args = new String[5][];

		// Args train

		String[] argsTrainSGD = args[0] = new String[ARGS_TRAIN_SGD];

		modelSGDPath = "classifier/SGD/model";

		// Recoger datos interfaz

		int i = 0;
		argsTrainSGD[i] = "--input";
		argsTrainSGD[++i] = inputPath;
		argsTrainSGD[++i] = "--output";
		argsTrainSGD[++i] = modelSGDPath;
		argsTrainSGD[++i] = "--target";
		// argsTrainSGD[++i] = target;
		argsTrainSGD[++i] = "--categories";
		// argsTrainSGD[++i] = numCategories;
		argsTrainSGD[++i] = "--predictors";
		// argsTrainSGD[++i] = "pred1 pred2"; //Volcar en un string separado por
		// espacios
		argsTrainSGD[++i] = "--types";
		// argsTrainSGD[++i] = "type1 type2..."; //Volcar en un string separado
		// por espacios
		argsTrainSGD[++i] = "--passes";
		// argsTrainSGD[++i] = passes;
		argsTrainSGD[++i] = "--lambda";
		// argsTrainSGD[++i] = lambda;
		argsTrainSGD[++i] = "--rate";
		// argsTrainSGD[++i] = rate;
		argsTrainSGD[++i] = "--lambda";
		// argsTrainSGD[++i] = lambda;
		argsTrainSGD[++i] = "--noBias"; // Optional??
		argsTrainSGD[++i] = "--features";
		// argsTrainSGD[++i] = numFeatures;

		// argsTrainSGD[++i] = "-ow"; //Se puede??

		return args;
	}

}
