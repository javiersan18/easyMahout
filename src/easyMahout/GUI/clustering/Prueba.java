package easyMahout.GUI.clustering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.clustering.AbstractCluster;
import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.clustering.classify.WeightedVectorWritable;
import org.apache.mahout.clustering.iterator.DistanceMeasureCluster;
import org.apache.mahout.clustering.kmeans.KMeansDriver;
import org.apache.mahout.clustering.kmeans.Kluster;
import org.apache.mahout.common.HadoopUtil;
import org.apache.mahout.common.distance.DistanceMeasure;
import org.apache.mahout.common.distance.EuclideanDistanceMeasure;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class Prueba {
	public static final double[][] points = { { 1, 1 }, { 2, 1 }, { 1, 2 }, { 2, 2 }, { 3, 3 }, { 8, 8 }, { 9, 8 }, { 8, 9 }, { 9, 9 } };

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

	public static void main(String args[]) throws Exception {
		int k = 2;
		List<Vector> vectors = getPoints(points);
		File testData = new File("testdata");
		if (!testData.exists()) {
			testData.mkdir();
		}
		System.out.println("hola");
		testData = new File("testdata/points");
		if (!testData.exists()) {
			testData.mkdir();
		}
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			writePointsToFile(vectors, "testdata/points/file1", fs, conf);
			//writePointsToFile(vectors, new Path("testdata/points/file1"), conf);
			Path path = new Path("testdata/clusters/part-00000");
			DistanceMeasure dm = new EuclideanDistanceMeasure();
			SequenceFile.Writer writer = new SequenceFile.Writer(fs, conf, path, Text.class, Kluster.class);
			for (int i = 0; i < k; i++) {
				Vector vec = vectors.get(i);
				Kluster cluster = new Kluster(vec, i, dm);
				writer.append(new Text(cluster.getIdentifier()), cluster);
			}
			writer.close();
			
			SequenceFile.Reader reader = new SequenceFile.Reader(fs, path, conf);
			Text key = new Text();
			Kluster value = new Kluster();
			while (reader.next(key, value)) {
				System.out.println(value.toString());
			}
			reader.close();

			Path output = new Path("output");
			HadoopUtil.delete(conf, output);

			KMeansDriver.run(conf, new Path("testdata/points"), new Path("testdata/clusters"), output, dm, 0.001, 10, true, 0.0, true);

			SequenceFile.Reader reader2 = new SequenceFile.Reader(fs, new Path("output/" + Cluster.CLUSTERED_POINTS_DIR + "/part-m-0"), conf);
			IntWritable key2 = new IntWritable();
			WeightedVectorWritable value2 = new WeightedVectorWritable();
			while (reader2.next(key2, value2)) {
				System.out.println(value2.toString() + " belongs to cluster " + key2.toString());
			}
			reader.close();
		}
	}

