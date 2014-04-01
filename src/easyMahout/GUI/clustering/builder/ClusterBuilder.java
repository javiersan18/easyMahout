package easyMahout.GUI.clustering.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


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
import org.apache.mahout.clustering.canopy.CanopyClusterer;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.dirichlet.DirichletDriver;
import org.apache.mahout.clustering.dirichlet.models.DistributionDescription;
import org.apache.mahout.clustering.display.DisplayCanopy;
import org.apache.mahout.clustering.display.DisplayFuzzyKMeans;
import org.apache.mahout.clustering.display.DisplayKMeans;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
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
import org.apache.mahout.text.SequenceFilesFromDirectory;
import org.apache.mahout.text.SequenceFilesFromDirectoryMapper;
import org.apache.mahout.utils.SplitInput;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.SparseVectorsFromSequenceFiles;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

import MahoutInAction.Clustering.Clustering72;


import easyMahout.GUI.MainGUI;

import easyMahout.GUI.Punto;
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

	private static boolean hadoop=MainGUI.isDistributed();//hadoop =true

	private static boolean esCanopy;

	private final static Logger log = Logger.getLogger(ClusterBuilder.class);

	private static File testData;

	public static final double[][] points= { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 }, { 3, 3 }, { 8, 8 }, { 9, 8 }, { 8, 9 }, { 9, 9 } ,{1000,10}} ;

	private static final String[] KEY_PREFIX_OPTION = null;

	private static final Object[] CHUNK_SIZE_OPTION = null;

	private static ArrayList<Punto> puntos=new ArrayList<Punto>();
	public static Cluster buildCluster() throws ClassNotFoundException, InterruptedException, IOException {

		hayError=false;

		algoritmo = MainClusterPanel.getAlgorithmClusterPanel().getSelectedType();
		String dist=MainClusterPanel.getDistanceClusterPanel().getSelectedType();
		//-------------------------------------------------------------------------------------------				
		//cogemos el tipo de distancia

		switch(dist){
		case	Constants.ClusterDist.EUCLIDEAN:
		{
			MainClusterPanel.getDistanceClusterPanel();
			if (DistanceMeasurePanel.getChckbxWeighted().isSelected())
				d=new WeightedEuclideanDistanceMeasure();
			else d=new EuclideanDistanceMeasure();

			break;
		}
		case	Constants.ClusterDist.SQUAREDEUCLIDEAN:
		{
			d=new SquaredEuclideanDistanceMeasure();
			break;
		}
		case	Constants.ClusterDist.MANHATTAN:
		{
			MainClusterPanel.getDistanceClusterPanel();
			if (DistanceMeasurePanel.getChckbxWeighted().isSelected())
				d=new WeightedManhattanDistanceMeasure();
			else d=new ManhattanDistanceMeasure();
			break;
		}
		case	Constants.ClusterDist.TANIMOTO:
		{
			d=new TanimotoDistanceMeasure();
			break;
		}
		case	Constants.ClusterDist.COSINE:
		{
			d=new CosineDistanceMeasure();
			break;
		}

		default : d=new EuclideanDistanceMeasure();
		}
		//fin tipo distancia
		//algoritmo=CANOPY
		if (algoritmo.equals(Constants.ClusterAlg.CANOPY)){

			//-------------------------------------------------------------------------------------------	
			esCanopy = true;
			//cogemos las valores treshold
			treshold1=MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch(treshold1){
			case "":{ JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold 1");
			hayError=true;
			break;
			}
			default :{
				t1=Double.parseDouble(treshold1);
			}
			}

			treshold2=MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch(treshold2){
			case "":{ JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold 2");
			hayError=true;
			break;
			}
			default :{
				t2=Double.parseDouble(treshold2);
			}
			}
			//fin treshold
			//-------------------------------------------------------------------------------------------	


			//nº iterations
			it=MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch(it){
			case "":{ 
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError=true;
				break;
			}
			default :{
				iteraciones=Integer.parseInt(it);
			}
			}


			//fin nº iterations

			//-------------------------------------------------------------------------------------------	

			//construimos el cluster CANOPY
			CanopyClusterer cluster = null;
			if (!hayError) 
			{
				cluster = new CanopyClusterer(d,t1,t2);
			}


			if (cluster != null) {
				constructCluster(false);
				MainGUI.writeResult("OK building the clusters :Algorithm "+ algoritmo +" Distance  "+ d +" Treshold "+ treshold1 +" Iterations "+it , Constants.Log.INFO);
				//TODO escribir los datos del cluster en un doc	
			}	

			else 					
				MainGUI.writeResult("error building the clusters", Constants.Log.ERROR);
		}
		//fin CANOPY
		//--------------------------------------------------------------------------------------------
		else if (MainClusterPanel.getAlgorithmClusterPanel().getSelectedType().equals(Constants.ClusterAlg.KMEANS)){
			//nº clusters
			if (!esCanopy)	{
				numberClusters=MainClusterPanel.getNumberClusterPanel().getCampoNum().getText();
				switch(numberClusters){
				case "":{ 
					JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
					hayError=true;
					break;
				}
				default :{
					numero=Integer.parseInt(numberClusters);
				}
				}
			}

			//fin nº clusters

			treshold1=MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch(treshold1){
			case "":{ JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
			hayError=true;
			break;
			}
			default :{
				t1=Double.parseDouble(treshold1);
			}
			}
			//nº iterations
			it=MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch(it){
			case "":{ 
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError=true;
				break;
			}
			default :{
				iteraciones=Integer.parseInt(it);
			}
			}


			//fin nº iterations

			Kluster kluster = new Kluster();

			if (kluster != null) {
				Path output = new Path("output");
				conf = new Configuration();
				try {
					fs = FileSystem.get(conf);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				constructCluster(true);

			}	

			else 					
				MainGUI.writeResult("error building the clusters", Constants.Log.ERROR);
		}
		//if user choose Fuzzy K-Means
		else if (MainClusterPanel.getAlgorithmClusterPanel().getSelectedType().equals(Constants.ClusterAlg.FUZZYKMEANS)){
			//nº clusters
			if (!esCanopy)	{
				numberClusters=MainClusterPanel.getNumberClusterPanel().getCampoNum().getText();
				switch(numberClusters){
				case "":{ 
					JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
					hayError=true;
					break;
				}
				default :{
					numero=Integer.parseInt(numberClusters);
				}
				}
			}

			//fin nº clusters

			treshold1=MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch(treshold1){
			case "":{ JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
			hayError=true;
			break;
			}
			default :{
				t1=Double.parseDouble(treshold1);
			}
			}
			//nº iterations
			it=MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch(it){
			case "":{ 
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError=true;
				break;
			}
			default :{
				iteraciones=Integer.parseInt(it);
			}
			}


			//fin nº iterations

			Kluster kluster = new Kluster();

			if (kluster != null) {
				Path output = new Path("output");
				conf = new Configuration();
				try {
					fs = FileSystem.get(conf);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				constructCluster(false);

			}	

			else 					
				MainGUI.writeResult("error building the clusters", Constants.Log.ERROR);
		}
		// fin Fuzzy K-Means
		else if (hadoop){

			treshold1=MainClusterPanel.getTresholdClusterPanel().getCampoNum().getText();
			switch(treshold1){
			case "":{ 
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Treshold ");
				hayError=true;
				break;
			}
			default :{
				t1=Double.parseDouble(treshold1);
			}
			}
			//nº iterations
			it=MainClusterPanel.getMaxIterationsPanel().getCampoNum().getText();
			switch(it){
			case "":{ 
				JOptionPane.showMessageDialog(null, "You haven't introduced a value for Max Iterations!");
				hayError=true;
				break;
			}
			default :{
				iteraciones=Integer.parseInt(it);
			}
			}

			constructCluster(false);
		}

		else if (MainClusterPanel.getAlgorithmClusterPanel().getSelectedType().equals(Constants.ClusterAlg.DIRICHLET)){
			Double treshold1=0.1;
			Double treshold2=0.2;
			CanopyClusterer cluster = new CanopyClusterer(new EuclideanDistanceMeasure(),treshold1,treshold2);

			if (cluster != null) {MainGUI.writeResult("OK building the clusters USER_Defined", Constants.Log.INFO);}	

			else 					
				MainGUI.writeResult("error building the clusters", Constants.Log.ERROR);
		}

		return null;
	}


	public static void writeResult(){
		SequenceFile.Reader reader2=null;
		if (hadoop){
			try {
				reader2 = new SequenceFile.Reader(fs, new Path("output"+ System.getProperty("file.separator") + Cluster.CLUSTERED_POINTS_DIR +  System.getProperty("file.separator")+"part-m-0"), conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			try {
				reader2 = new SequenceFile.Reader(fs, new Path("output"+ System.getProperty("file.separator") + Cluster.CLUSTERED_POINTS_DIR + System.getProperty("file.separator")+"part-m-0"), conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		IntWritable key2 = new IntWritable();
		WeightedVectorWritable value2 = new WeightedVectorWritable();
		try {
			while (reader2.next(key2, value2)) {
				System.out.println(value2.toString() + " belongs to cluster " + key2.toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			reader2.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void constructCluster(boolean kmeans){
		ArrayList<Object> parametrosFuzzy= new ArrayList<Object>();
		ArrayList<Object> parametrosCanopy= new ArrayList<Object>();
		ArrayList<Object> parametrosKMEANS= new ArrayList<Object>();
		List<Vector> vectors;
		
		if (isSequential()){
			vectors =  CreateSequenceFile.getVectors();
		
		
		testData = new File("testdata");
		if (!testData.exists()) {
			testData.mkdir();
		}
		System.out.println("hola");
		testData = new File("testdata"+ System.getProperty("file.separator")+"points");

		if (!testData.exists()) {
			testData.mkdir();
			Configuration conf = new Configuration();
			FileSystem fs = null;
			try {
				fs = FileSystem.get(conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				writePointsToFile(vectors, "testdata"+ System.getProperty("file.separator")+"points"+ System.getProperty("file.separator")+"file1", fs, conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//hadoop?
			Path path=new Path("help");
			if (!hadoop){
				path = new Path("testdata"+ System.getProperty("file.separator")+"clusters"+ System.getProperty("file.separator")+"part-00000");
			}
			else {
				path = new Path("testdata"+File.separatorChar+"clusters"+File.separatorChar+"part-m-0");
			}
			SequenceFile.Writer writer = null;
			try {
				writer = new SequenceFile.Writer(fs, conf, path, Text.class, Kluster.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//¿hadoop?
			if (!hadoop){
				for (int i = 0; i < numero; i++) {
					Vector vec = vectors.get(i);
					Kluster cluster = new Kluster(vec, i, d);
					try {
						writer.append(new Text(cluster.getIdentifier()), cluster);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else {
				for (int i = 0; i < numero; i++) {
					Vector vec = vectors.get(i);
					Kluster cluster = new Kluster(vec, i, d);
					try {
						writer.append(new Text(cluster.getIdentifier()), cluster);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SequenceFile.Reader reader = null;
			try {
				reader = new SequenceFile.Reader(fs, path, conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Text key = new Text();
			Kluster value = new Kluster();
			try {
				while (reader.next(key, value)) {
					System.out.println(value.toString());
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Path output = new Path("output");
			try {
				HadoopUtil.delete(conf, output);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				if (kmeans /*|| hadoop*/){
					Path pointsPath=new Path(DataModelClusterPanel.getOutputPath());
					DisplayGraphicKMeans.runSequentialKMeansClusterer(conf, pointsPath, output, d, numero, iteraciones, t1);
					
					/*parametrosKMEANS.add(conf);
					parametrosKMEANS.add(pointsPath);
					parametrosKMEANS.add(new Path("testdata/clusters"));
					parametrosKMEANS.add(output);
					parametrosKMEANS.add(d);
					parametrosKMEANS.add(t1);
					parametrosKMEANS.add(iteraciones);
					parametrosKMEANS.add(true);
					parametrosKMEANS.add(t1);
					parametrosKMEANS.add(!hadoop);
					parametrosKMEANS.add(numero);
					try {
						DisplayGraphicKMeans.main(parametrosKMEANS);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						MainGUI.writeResult("Impossible drawing", Constants.Log.ERROR);
					}*/
					
				}
				
				else if (esCanopy){
					Path pointsPath=new Path(DataModelClusterPanel.getOutputPath());
					
					CanopyDriver.run(conf,pointsPath , output, d, t1, t2, true, t1, !hadoop);
					
					//-------------CanopyDriver.run(conf, new Path("testdata/points"), output, d, t1, t2, true, t1, !hadoop);

					/*parametrosCanopy.add(conf);
					parametrosCanopy.add(pointsPath);
					parametrosCanopy.add(output);
					parametrosCanopy.add(d);
					parametrosCanopy.add(t1);
					parametrosCanopy.add(t2);
					parametrosCanopy.add(true);
					parametrosCanopy.add(t1);
					parametrosCanopy.add(!hadoop);
					parametrosCanopy.add(iteraciones);

					//----------
				try {
					DisplayGraphicCanopy.main(parametrosCanopy);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					MainGUI.writeResult("Impossible drawing", Constants.Log.ERROR);
				}*/
				
				}
				
				else  {
					
					Path pointsPath=new Path(DataModelClusterPanel.getOutputPath());
					boolean emitMostLikely=AlgorithmClusterPanel.getEmitMostLikely().isSelected();
					String s=AlgorithmClusterPanel.getFuzzyFactor().getText();
					float fuzzyFactor=Float.parseFloat(s);
					
					DisplayGraphicFuzzy.runSequentialFuzzyKClusterer(conf, pointsPath, output, d, iteraciones, fuzzyFactor, t1);

					//--------
				
					/*parametrosFuzzy.add(conf);
					parametrosFuzzy.add(pointsPath);
					parametrosFuzzy.add(new Path("testdata/clusters"));
					parametrosFuzzy.add(output);
					parametrosFuzzy.add(d);
					parametrosFuzzy.add(t1);
					parametrosFuzzy.add(iteraciones);
					parametrosFuzzy.add(fuzzyFactor);
					parametrosFuzzy.add(true);
					parametrosFuzzy.add(emitMostLikely);
					parametrosFuzzy.add(t1);
					parametrosFuzzy.add(!hadoop);
					parametrosFuzzy.add(numero);

					try {
						DisplayGraphicFuzzy.main(parametrosFuzzy);
					} catch (Exception e) {
						MainGUI.writeResult("Impossible drawing", Constants.Log.ERROR);
					}*/
					
					//--------
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


			// write result
			SequenceFile.Reader reader2 = null;
			if (hadoop){
				try {
					reader2 = new SequenceFile.Reader(fs, new Path("output" +File.separatorChar + Cluster.CLUSTERED_POINTS_DIR +File.separatorChar +"part-m-00000"), conf);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				try {

					reader2 = new SequenceFile.Reader(fs, new Path("output" +File.separatorChar + Cluster.CLUSTERED_POINTS_DIR +File.separatorChar +"part-m-0"), conf);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			IntWritable key2 = new IntWritable();
			WeightedVectorWritable value2 = new WeightedVectorWritable();
			try {
				
				while (reader2.next(key2, value2)) {
					String s=value2.toString() + " belongs to cluster " + key2.toString();
					System.out.println(s);//canopy
					MainGUI.writeResult(s, Constants.Log.RESULT);
					/*IntWritable x= key2;
					WeightedVectorWritable data=value2;
					float acum=0;
					for (int i=0; i<data.getVector().size();i++){
						acum+=data.getVector().get(i);
						}
					float y=acum/data.getVector().size();
					Punto punto=new Punto(x,y);
					puntos.add(punto);*/
					
					
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		}
		else{
			vectors =null;
			try {
				getHadoopJob();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
		//log testdata/points/file1
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
	public static void getHadoopJob(){
		
		int i = 0;
		String[] args1 = new String[6];
		args1[i++] = "--input";
		args1[i++] = DataModelClusterPanel.getInputPath();

		args1[i++] = "--output";
		args1[i++] = DataModelClusterPanel.getOutputPath();


		args1[i++] = "--method";
		args1[i++] = "sequential";

		try {

//			ToolRunner.run(new SequenceFilesFromDirectory(), args1);

			i = 0;
			String[] args2 = new String[9];
			args2[i++] = "--input";
			args2[i++] = DataModelClusterPanel.getOutputPath();

			args2[i++] = "--output";
			String vec=DataModelClusterPanel.getOutputPath() + System.getProperty("file.separator")+"vectors";
			args2[i++] = vec;

			args2[i++] = "--overwrite";

			args2[i++] = "--logNormalize";
			args2[i++] = "--namedVector";
			args2[i++] = "--weight";
			args2[i++] = "tfidf";

			//ToolRunner.run(new SparseVectorsFromSequenceFiles(), args2);

			i = 0;
			String[] args3 = new String[12];
			args3[i++] = "--input";
			args3[i++] = vec;

			args3[i++] = "--trainingOutput";
			args3[i++] = vec + System.getProperty("file.separator")+"trainingOutput";

			args3[i++] = "--testOutput";
			args3[i++] = vec + System.getProperty("file.separator")+" testOutput";	

			args3[i++] = "--randomSelectionSize";
			args3[i++] = "20";


			args3[i++] = "--overwrite";
			args3[i++] = "--sequenceFiles";

			args3[i++] = "--method";
			args3[i++] = "sequential";

			//ToolRunner.run(new Configuration(), new SplitInput(), args3);
			Configuration confHadoop=new Configuration();
			Job job = new Job(confHadoop, "KMeansParallel");
			
			
			FileInputFormat.addInputPath(job, new Path(DataModelClusterPanel.getInputPath()));
			FileOutputFormat.setOutputPath(job, new Path(DataModelClusterPanel.getOutputPath()+System.getProperty("file.separator")+"prueba2"));
			job.waitForCompletion(true);
			//KMeansDriver.run(confHadoop, new Path(vec), new Path(args2[1]), new Path(vec+ System.getProperty("file.separator")+"salida"), d, t1, iteraciones, true, t1, false);
			MainGUI.writeResult("Hadoop Job OK", Constants.Log.INFO);
		} catch (Exception e) {
			e.printStackTrace();
			MainGUI.writeResult("Not able to run Hadoop Job", Constants.Log.ERROR);
		}
	}
	private static int runMapReduce(Path input, Path output)
		    throws IOException, ClassNotFoundException, InterruptedException
		  {
		    int chunkSizeInMB = 64;
		  
		   
		   // Job job = HadoopUtil.prepareJob(input, output, MultipleTextFileInputFormat.class, SequenceFilesFromDirectoryMapper.class, Text.class, Text.class, Text.class, Text.class, Text.class, SequenceFileOutputFormat.class, conf);
		    		//(input, output, MultipleTextFileInputFormat.class, SequenceFilesFromDirectoryMapper.class, Text.class, Text.class, SequenceFileOutputFormat.class, "SequenceFilesFromDirectory");
		   Job job=new Job();

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


	public static ArrayList<Punto> getPuntos() {
		return puntos;
	}


	public static void setPuntos(ArrayList<Punto> puntos) {
		ClusterBuilder.puntos = puntos;
	}
		
		/*Usage:                                                                          
 [--input <input> --output <output> --overwrite --method <method> --chunkSize   
<chunkSize> --fileFilterClass <fileFilterClass> --keyPrefix <keyPrefix>         
--charset <charset> --method <method> --overwrite --help --tempDir <tempDir>    
--startPhase <startPhase> --endPhase <endPhase>]                                
Job-Specific Options:                                                           
  --input (-i) input                             Path to job input directory.   
  --output (-o) output                           The directory pathname for     
                                                 output.                        
  --overwrite (-ow)                              If present, overwrite the      
                                                 output directory before        
                                                 running job                    
  --method (-xm) method                          The execution method to use:   
                                                 sequential or mapreduce.       
                                                 Default is mapreduce           
  --chunkSize (-chunk) chunkSize                 The chunkSize in MegaBytes.    
                                                 Defaults to 64                 
  --fileFilterClass (-filter) fileFilterClass    The name of the class to use   
                                                 for file parsing. Default:     
                                                 org.apache.mahout.text.PrefixAd
                                                 ditionFilter                   
  --keyPrefix (-prefix) keyPrefix                The prefix to be prepended to  
                                                 the key                        
  --charset (-c) charset                         The name of the character      
                                                 encoding of the input files.   
                                                 Default to UTF-8               
  --method (-xm) method                          The execution method to use:   
                                                 sequential or mapreduce.       
                                                 Default is mapreduce           
  --overwrite (-ow)                              If present, overwrite the      
                                                 output directory before        
                                                 running job                    
  --help (-h)                                    Print out help                 
  --tempDir tempDir                              Intermediate output directory  
  --startPhase startPhase                        First phase to run             
  --endPhase endPhase                            Last phase to run              
Specify HDFS directories while running on hadoop; else specify local file       
system directories */
	
}


