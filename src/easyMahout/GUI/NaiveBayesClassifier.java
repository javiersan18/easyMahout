package easyMahout.GUI;





import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.util.ToolRunner;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.training.TrainNaiveBayesJob;
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import easyMahout.utils.Environment;



public class NaiveBayesClassifier {
	
	private final static Logger log = LoggerFactory.getLogger(NaiveBayesClassifier.class);

	public static void main(String[] args) {
		
		Environment env;
		try {
			env = new Environment();
			log.info(env.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//Environment.logEnv("env", (org.slf4j.Logger) log);
		
		Configuration configuration = new Configuration();

		String inputModelPath = "";

		// model is a matrix (wordId, labelId) => probability score
		// NaiveBayesModel model = NaiveBayesModel.materialize(new
		// Path(inputModelPath), configuration);

		// StandardNaiveBayesClassifier classifier = new
		// StandardNaiveBayesClassifier(model);

		
		int i = 0;
		String[] args1 = new String[6];
		args1[i++] = "--input";
		args1[i++] = "C:/Mahout/test1/data";

		args1[i++] = "--output";
		args1[i++] = "C:/Mahout/test1/data-seq";

		
		args1[i++] = "--method";
		args1[i++] = "sequential";

		try {
			
			ToolRunner.run(new SequenceFilesFromDirectory(), args1);
			
			i = 0;
			String[] args2 = new String[9];
			args2[i++] = "--input";
			args2[i++] = "C:/Mahout/test1/data-seq";

			args2[i++] = "--output";
			args2[i++] = "C:/Mahout/test1/data-vectors";
			
			args2[i++] = "--overwrite";

			args2[i++] = "--logNormalize";
			args2[i++] = "--namedVector";
			args2[i++] = "--weight";
			args2[i++] = "tfidf";
			
			ToolRunner.run(new SparseVectorsFromSequenceFiles(), args2);
			
			i = 0;
			String[] args3 = new String[12];
			args3[i++] = "--input";
			args3[i++] = "C:/Mahout/test1/data-vectors/tfidf-vectors";
			
			args3[i++] = "--trainingOutput";
			args3[i++] = "C:/Mahout/test1/data-train-vectors";

			args3[i++] = "--testOutput";
			args3[i++] = "C:/Mahout/test1/data-test-vectors";	
			
			args3[i++] = "--randomSelectionSize";
			args3[i++] = "20";
			
			
			args3[i++] = "--overwrite";
			args3[i++] = "--sequenceFiles";
			
			args3[i++] = "--method";
			args3[i++] = "sequential";			
			
			ToolRunner.run(new Configuration(), new SplitInput(), args3);
			
			i = 0;
			String[] args4 = new String[8];
			args4[i++] = "--input";
			args4[i++] = "C:/Mahout/test1/data-train-vectors";
			
			args4[i++] = "--output";
			args4[i++] = "C:/Mahout/test1/model"; 
			
			args4[i++] = "-el";
			
			args4[i++] = "-li";
			args4[i++] = "C:/Mahout/test1/labelindex";
	
			args4[i++] = "--overwrite";
			
			ToolRunner.run(new Configuration(), new TrainNaiveBayesJob(), args4);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
