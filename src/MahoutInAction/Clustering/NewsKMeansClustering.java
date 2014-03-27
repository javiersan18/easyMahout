package MahoutInAction.Clustering;

import java.io.File;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.lucene.analysis.Analyzer;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.distance.CosineDistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.vectorizer.DictionaryVectorizer;
import org.apache.mahout.vectorizer.DocumentProcessor;
import org.apache.mahout.vectorizer.tfidf.TFIDFConverter;

public class NewsKMeansClustering {
	
	public static void main(String args[]) throws Exception {
		/*//generate sample dummy vectors
		generateSamples(sampleData, 400, 1, 1, 3);
		generateSamples(sampleData, 300, 1, 0, 0.5);
		generateSamples(sampleData, 300, 0, 2, 0.1);
		//create the first cluster vectors
		List<Vector> randomPoints = RandomSeedGenerator.chooseRandomPoints(points, k);
		List<Cluster> clusters = new ArrayList<Cluster>();
		// associate the cluster with the random point
		int clusterId = 0;
		for (Vector v : randomPoints) {
			clusters.add(new Cluster(v, clusterId++));
			}
			// execute the kmeans cluster
			List<List<Cluster>> finalClusters = KMeansClusterer.clusterPoints(points, clusters, new EuclideanDistanceMeasure(), 3,0.01);
			// display final cluster center
			for(Cluster cluster : finalClusters.get(finalClusters.size() - 1))
			{
			System.out.println("Cluster id: " + cluster.getId() + " center:"+ cluster.getCenter().asFormatString());
			}
			}*/
}}