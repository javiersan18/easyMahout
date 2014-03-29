package easyMahout.GUI.clustering.builder;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.canopy.CanopyDriver;
import org.apache.mahout.clustering.display.DisplayClustering;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.ManhattanDistanceMeasure;
import org.apache.mahout.math.DenseVector;

/**
 * Java desktop graphics class that runs canopy clustering and displays the results.
 * This class generates random data and clusters it.
 */
public class DisplayGraphicCanopy extends DisplayCluster {
 static final Color[] COLORES = { Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.magenta,Color.lightGray };
  DisplayGraphicCanopy() {
    initialize();
    this.setTitle("Canopy Clusters (>" + (int) (0.07 * 100) + "% of population)");
  }

  @Override
  public void paint(Graphics g) {
    plotSampleData((Graphics2D) g);
    plotClusters((Graphics2D) g);
  }

  protected static void plotClusters(Graphics2D g2) {
    int cx = CLUSTERS.size() - 1;
    for (List<Cluster> clusters : CLUSTERS) {
      for (Cluster cluster : clusters) {
        if (isSignificant(cluster)) {
          g2.setStroke(new BasicStroke(1));
          g2.setColor(Color.BLUE);
          double[] t1 = {T1, T1};
          plotEllipse(g2, cluster.getCenter(), new DenseVector(t1));
          double[] t2 = {T2, T2};
          plotEllipse(g2, cluster.getCenter(), new DenseVector(t2));
          g2.setColor(COLORES[Math.min(DisplayGraphicCanopy.COLORES.length - 1, cx)]);
          g2.setStroke(new BasicStroke(cx == 0 ? 3 : 1));
          plotEllipse(g2, cluster.getCenter(), cluster.getRadius().times(3));
        }
      }
      cx--;
    }
  }

  public static void main(ArrayList<Object> parametrosCanopy) throws Exception {
    Path samples = (Path) parametrosCanopy.get(1);
    Path output = new Path("output");
    Configuration conf = (Configuration) parametrosCanopy.get(0);
    DistanceMeasure measure=  (DistanceMeasure) parametrosCanopy.get(3);
   
   // HadoopUtil.delete(conf, samples);
   // HadoopUtil.delete(conf, output);
    RandomUtils.useTestSeed();
   // generateSamples();
   // writeSampleData(samples);
    CanopyDriver.buildClusters(conf, samples, output, measure,(double)parametrosCanopy.get(4), (double)parametrosCanopy.get(5),0, (boolean)parametrosCanopy.get(8));
    loadClustersWritable(output,(int) parametrosCanopy.get(9));

    new DisplayGraphicCanopy();
  }

}

