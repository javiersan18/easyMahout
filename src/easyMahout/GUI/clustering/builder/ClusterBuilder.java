package easyMahout.GUI.clustering.builder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.log4j.Logger;
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
import org.apache.mahout.clustering.display.DisplayCanopy;
import org.apache.mahout.clustering.display.DisplayFuzzyKMeans;
import org.apache.mahout.clustering.display.DisplayKMeans;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.TanimotoDistanceMeasure;
import org.apache.mahout.common.distance.WeightedEuclideanDistanceMeasure;
import org.apache.mahout.common.distance.WeightedManhattanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

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
	
	private static boolean hadoop=MainGUI.isDistributed();//hadoop =true
	
	private static boolean esCanopy;
	
	private final static Logger log = Logger.getLogger(ClusterBuilder.class);
	
	
	public static final double[][] points= { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 }, { 3, 3 }, { 8, 8 }, { 9, 8 }, { 8, 9 }, { 9, 9 } ,{1000,10}} ;
	
	public static void transformData(){
		//This method will take the data model from the input dialog and transform it to a vector of vectors using the Vectorizer class.
		DataModel dataModel=DataModelClusterPanel.getDataModel();
		try {
			int features=dataModel.getNumItems();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
		
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

				//Data model
				//fin Data model
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
				
				else if (MainClusterPanel.getAlgorithmClusterPanel().getSelectedType().equals(Constants.ClusterAlg.USER_DEFINED)){
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
		try {
			reader2 = new SequenceFile.Reader(fs, new Path("output/" + Cluster.CLUSTERED_POINTS_DIR + "/part-m-0"), conf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
		List<Vector> vectors = getPoints(points);
		File testData = new File("testdata");
		if (!testData.exists()) {
			testData.mkdir();
		}
		System.out.println("hola");
		testData = new File("testdata/points");
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
				writePointsToFile(vectors, "testdata/points/file1", fs, conf);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Path path = new Path("testdata/clusters/part-00000");
			
			SequenceFile.Writer writer = null;
			try {
				writer = new SequenceFile.Writer(fs, conf, path, Text.class, Kluster.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < 2; i++) {
				Vector vec = vectors.get(i);
				Kluster cluster = new Kluster(vec, i, d);
				try {
					writer.append(new Text(cluster.getIdentifier()), cluster);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
			
				/*run(Configuration conf, Path input, Path clustersIn, Path output, DistanceMeasure measure,
			      double convergenceDelta, int maxIterations, boolean runClustering, double clusterClassificationThreshold,
			      boolean runSequential)*/
				if (kmeans || hadoop){
					KMeansDriver.run(conf, new Path("testdata/points"), new Path("testdata/clusters"), output, d, t1, iteraciones, true, t1, !hadoop);
					
					parametrosKMEANS.add(conf);
					parametrosKMEANS.add(new Path("testdata/points"));
					parametrosKMEANS.add(new Path("testdata/clusters"));
					parametrosKMEANS.add(output);
					parametrosKMEANS.add(d);
					parametrosKMEANS.add(t1);
					parametrosKMEANS.add(iteraciones);
					parametrosKMEANS.add(true);
					parametrosKMEANS.add(t1);
					parametrosKMEANS.add(!hadoop);
					parametrosKMEANS.add(numero);
				}
				else if (esCanopy){
					CanopyDriver.run(conf, new Path("testdata/points"), output, d, t1, t2, true, t1, !hadoop);
					
					//-------------CanopyDriver.run(conf, new Path("testdata/points"), output, d, t1, t2, true, t1, !hadoop);
					
					parametrosCanopy.add(conf);
					parametrosCanopy.add(new Path("testdata/points"));
					parametrosCanopy.add(output);
					parametrosCanopy.add(d);
					parametrosCanopy.add(t1);
					parametrosCanopy.add(t2);
					parametrosCanopy.add(true);
					parametrosCanopy.add(t1);
					parametrosCanopy.add(!hadoop);
					//----------
				}
				else {
					//FuzzyKMeansDriver.run(conf, input, clustersIn, output, measure, convergenceDelta, maxIterations, m, runClustering, emitMostLikely, threshold, runSequential/notHadoop)
					
					boolean emitMostLikely=AlgorithmClusterPanel.getEmitMostLikely().isSelected();
					String s=AlgorithmClusterPanel.getFuzzyFactor().getText();
					float fuzzyFactor=Float.parseFloat(s);
					FuzzyKMeansDriver.run(conf, new Path("testdata/points"), new Path("testdata/clusters"), output, d, t1, iteraciones, fuzzyFactor, true, emitMostLikely, t1, !hadoop);
					
					//--------
					/*FuzzyKMeansDriver.run(conf, new Path("testdata/points"), new Path("testdata/clusters"), output, d,
					 *  t1, iteraciones, fuzzyFactor, true, emitMostLikely, t1, !hadoop);*/
					
					parametrosFuzzy.add(conf);
					parametrosFuzzy.add(new Path("testdata/points"));
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
					parametrosKMEANS.add(numero);
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
	
			/*try {
				if (algoritmo.equals(Constants.ClusterAlg.KMEANS))	DisplayGraphicKMeans.main(parametrosKMEANS);
				else if (algoritmo.equals(Constants.ClusterAlg.CANOPY)) DisplayGraphicCanopy.main(parametrosCanopy);
				else if (algoritmo.equals(Constants.ClusterAlg.FUZZYKMEANS)) DisplayGraphicFuzzy.main(parametrosFuzzy);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			// write result
			SequenceFile.Reader reader2 = null;
			try {
				reader2 = new SequenceFile.Reader(fs, new Path("output/" + Cluster.CLUSTERED_POINTS_DIR + "/part-m-0"), conf);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void writePointsToFile(List<Vector> points, String fileName, FileSystem fs, Configuration conf) throws IOException {
		Path path = new Path(fileName);
		SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, LongWritable.class, VectorWritable.class);
		long recNum = 0;
		VectorWritable vec = new VectorWritable();
		for (Vector point : points) {
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

	public static ArrayList<Vector> getPoints(double[][] raw) {
		ArrayList<Vector> points = new ArrayList<Vector>();
		for (int i = 0; i < raw.length; i++) {
			double[] fr = raw[i];
			Vector vec = new RandomAccessSparseVector(fr.length);
			vec.assign(fr);
			points.add(vec);
		}
		return points;
	}
	
}
		
	
