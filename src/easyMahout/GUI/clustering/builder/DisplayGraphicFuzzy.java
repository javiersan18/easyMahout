package easyMahout.GUI.clustering.builder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.ClusterClassifier;
//import org.apache.mahout.clustering.display.DisplayClustering;
import org.apache.mahout.clustering.fuzzykmeans.FuzzyKMeansDriver;
import org.apache.mahout.clustering.fuzzykmeans.SoftCluster;
import org.apache.mahout.clustering.iterator.ClusterIterator;
import org.apache.mahout.clustering.iterator.FuzzyKMeansClusteringPolicy;
import org.apache.mahout.clustering.kmeans.RandomSeedGenerator;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.math.Vector;

import com.google.common.collect.Lists;

public class DisplayGraphicFuzzy extends DisplayCluster {
  
  DisplayGraphicFuzzy() {
    initialize();
    this.setTitle("Fuzzy k-Means Clusters (>" + (int) (0.05 * 100) + "% of population)");
  }
  
  // Override the paint() method
  @Override
  public void paint(Graphics g) {
    plotSampleData((Graphics2D) g);
    plotClusters((Graphics2D) g);
  }
  
  public static void main(ArrayList<Object> parametrosFuzzy) throws Exception {
    DistanceMeasure measure = (DistanceMeasure) parametrosFuzzy.get(4);
    
    Path samples = (Path) parametrosFuzzy.get(1);
    Path output = new Path("output");
    Configuration conf = (Configuration) parametrosFuzzy.get(0);
    //HadoopUtil.delete(conf, output);
    //HadoopUtil.delete(conf, samples);
    RandomUtils.useTestSeed();
   // DisplayCluster.generateSamples();
   // writeSampleData(samples);
    boolean runClusterer = (boolean) parametrosFuzzy.get(8);
    int maxIterations = (int) parametrosFuzzy.get(6);
    double t = (double) parametrosFuzzy.get(5);
    float threshold = (float) t;
    float m = (float) parametrosFuzzy.get(7) ;
    if (runClusterer) {
//      runSequentialFuzzyKClusterer(conf, samples, output, measure, maxIterations, m, threshold);
    } else {
      int numClusters = (int) parametrosFuzzy.get(12);
      runSequentialFuzzyKClassifier(conf, samples, output, measure, numClusters, maxIterations, m, threshold);
    }
    new DisplayGraphicFuzzy();
  }
  
  public static void runSequentialFuzzyKClassifier(Configuration conf, Path samples, Path output,
      DistanceMeasure measure, int numClusters, int maxIterations, float m, double threshold) throws IOException {
    Collection<Vector> points = Lists.newArrayList();
    for (int i = 0; i < numClusters; i++) {
      points.add(SAMPLE_DATA.get(i));
    }
    List<Cluster> initialClusters = Lists.newArrayList();
    int id = 0;
    for (Vector point : points) {
      initialClusters.add(new SoftCluster(point, id++, measure));
    }
    ClusterClassifier prior = new ClusterClassifier(initialClusters, new FuzzyKMeansClusteringPolicy(m, threshold));
    Path priorPath = new Path(output, "classifier-0");
    prior.writeToSeqFiles(priorPath);
    
    ClusterIterator.iterateSeq(conf, samples, priorPath, output, maxIterations);
    loadClustersWritable(output,maxIterations);
  }
  
  public static void runSequentialFuzzyKClusterer(Configuration conf, Path samples, Path output,
      DistanceMeasure measure, int maxIterations, float m, double threshold) throws IOException,
      ClassNotFoundException, InterruptedException {
    Path clustersIn = new Path(output, "random-seeds");
   // RandomSeedGenerator.buildRandom(conf, samples, clustersIn, maxIterations, measure);
   FuzzyKMeansDriver.run(conf, samples, new Path("testdata/clusters"), output, measure, m, maxIterations, m, true,  true, threshold, true);
   // FuzzyKMeansDriver.run(samples, clustersIn, output, measure, threshold, maxIterations, m, true, true, threshold,true);
    
    loadClustersWritable(output,maxIterations);
  }
}

