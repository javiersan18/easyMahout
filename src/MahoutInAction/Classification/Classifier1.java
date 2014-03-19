//package MahoutInAction.Classification;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.StringReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.TreeMap;
//
//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.util.Version;
//import org.apache.mahout.classifier.sgd.L1;
//import org.apache.mahout.classifier.sgd.OnlineLogisticRegression;
//import org.apache.mahout.math.DenseVector;
//import org.apache.mahout.math.RandomAccessSparseVector;
//import org.apache.mahout.math.Vector;
//import org.apache.mahout.vectorizer.encoders.ConstantValueEncoder;
//import org.apache.mahout.vectorizer.encoders.Dictionary;
//
//
//import org.apache.mahout.vectorizer.encoders.FeatureVectorEncoder;
//import org.apache.mahout.vectorizer.encoders.StaticWordValueEncoder;
//
//import com.google.common.collect.ConcurrentHashMultiset;
//import com.google.common.collect.Iterables;
//import com.google.common.collect.Multiset;
//
//public class Classifier1 {
//
//	/* SETTING UP VECTOR ENCODERS
//	 * 
//	 *Encoder: To encode the textual content in the subject and body of a posting.
//	 * 
//	 *Bias: provides a constant offset that the model can use to encode the average
//	 *frequency of each class.
//	 *
//	 *Lines: To encode the number of lines in a message.
//	 */
//	
//	Map<String, Set<Integer>> traceDictionary =	new TreeMap<String, Set<Integer>>();
//	FeatureVectorEncoder encoder = new StaticWordValueEncoder("body");
//	
//	encoder.setProbes(2);
//	encoder.setTraceDictionary(traceDictionary);
//	FeatureVectorEncoder bias = new ConstantValueEncoder("Intercept");
//	bias.setTraceDictionary(traceDictionary);
//	FeatureVectorEncoder lines = new ConstantValueEncoder("Lines");
//	lines.setTraceDictionary(traceDictionary);
//	Dictionary newsGroups = new Dictionary();
//	@SuppressWarnings("deprecation")
//	Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
//	
//	/*CONFIGURING THE LEARNING ALGORITHM
//	 * 
//	 *The alpha, decayExponent, and stepOffset methods specify the rate and way 
//	 *that the learning rate decreases. The lambda method specifies the amount
//	 *of regularization, and the learningRate method specifies the initial 
//	 *learning rate.
//	 */
//	
//	OnlineLogisticRegression learningAlgorithm =new OnlineLogisticRegression(20, FEATURES, new L1()).alpha(1).stepOffset(1000).decayExponent(0.9).lambda(3.0e-5).learningRate(20);
//	
//	/*ACCESSING DATA FILES
//	 * 
//	 *
//	 *
//	 */
//	
//	List<File> files = new ArrayList<File>();
//	for (File newsgroup : base.listFiles()) {
//		newsGroups.intern(newsgroup.getName());
//		files.addAll(Arrays.asList(newsgroup.listFiles()));
//	}
//	Collections.shuffle(files);
//	System.out.printf("%d training files\n", files.size());
//	
//	/*PREPARING TO TOKENIZE THE DATA
//	 * 
//	 * */
//	
//	double averageLL = 0.0;
//	double averageCorrect = 0.0;
//	double averageLineCount = 0.0;
//	int k = 0;
//	double step = 0.0;
//	int[] bumps = new int[]{1, 2, 5};
//	double lineCount;
//	
//	/*READING AND TOKENIZING THE DATA
//	 * 
//	 * */
//	
//	for (File file : files) {
//		BufferedReader reader = new BufferedReader(new FileReader(file));
//		String ng = file.getParentFile().getName();
//		int actual = newsGroups.intern(ng);
//		Multiset<String> words = ConcurrentHashMultiset.create();
//		String line = reader.readLine();
//		while (line != null && line.length() > 0) {
//			if (line.startsWith("Lines:")) {
//				String count = Iterables.get(onColon.split(line), 1);
//				try {
//					lineCount = Integer.parseInt(count);
//					averageLineCount += (lineCount - averageLineCount)/
//														Math.min(k + 1, 1000);
//				} catch (NumberFormatException e) {
//					lineCount = averageLineCount;
//				}
//			}
//			boolean countHeader = (line.startsWith("From:") || 
//									line.startsWith("Subject:")||
//									line.startsWith("Keywords:")|| 
//									line.startsWith("Summary:"));
//			do {
//				StringReader in = new StringReader(line);
//				if (countHeader) {
//					countWords(analyzer, words, in);
//				}
//				line = reader.readLine();
//			} while (line.startsWith(" "));
//		}
//		countWords(analyzer, words, reader);
//		reader.close();
//	}
//	
//	/*VECTORIZING THE DATA 
//	 * 
//	 * */
//	
//	Vector v = new RandomAccessSparseVector(FEATURES);
//	bias.addToVector(null, 1, v);
//	lines.addToVector(null, lineCount / 30, v);
//	logLines.addToVector(null, Math.log(lineCount + 1), v);
//	for (String word : words.elementSet()) {
//		encoder.addToVector(word, Math.log(1 + words.count(word)), v);
//	}
//	
//	/*MEASURING PROGRESS SO FAR
//	 * 
//	 * */
//	
//	double mu = Math.min(k + 1, 200);
//	double ll = learningAlgorithm.logLikelihood(actual, v);
//	averageLL = averageLL + (ll - averageLL) / mu;
//	Vector p = new DenseVector(20);
//	learningAlgorithm.classifyFull(p, v);
//	int estimated = p.maxValueIndex();
//	int correct = (estimated == actual? 1 : 0);
//	averageCorrect = averageCorrect + (correct - averageCorrect) / mu;
//	
//	/* TRAINING THE SGD MODEL WITH THE ENCODED DATA
//	 * 
//	 */
//	
//	learningAlgorithm.train(actual, v);
//	k++;
//	int bump = bumps[(int) Math.floor(step) % bumps.length];
//	int scale = (int) Math.pow(10, Math.floor(step / bumps.length));
//	if (k % (bump * scale) == 0) {
//		step += 0.25;
//		System.out.printf("%10d %10.3f %10.3f %10.2f %s %s\n",
//				k, ll, averageLL, averageCorrect * 100, ng,
//				newsGroups.values().get(estimated));
//	}
//	learningAlgorithm.close();
//			
//	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
