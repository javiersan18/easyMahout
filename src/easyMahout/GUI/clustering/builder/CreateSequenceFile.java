package easyMahout.GUI.clustering.builder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.BufferedReader;

import java.io.FileReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

public class CreateSequenceFile {

	public static void convert(String input, String output, String delimiter) {
		Configuration conf = new Configuration();
		FileSystem fs;
		try {
			fs = FileSystem.get(conf);
			BufferedReader reader;
			try {
				reader = new BufferedReader(new FileReader(input));
				SequenceFile.Writer writer;
				try {
					writer = new SequenceFile.Writer(fs, conf, new Path(output), LongWritable.class, VectorWritable.class);
					String line;
					long counter = 0;
					try {
						while ((line = reader.readLine()) != null) {
							String[] c;
							c = line.split(delimiter);
							double[] d = new double[c.length];
							for (int i = 0; i < c.length; i++) {
								try {
									d[i] = Double.parseDouble(c[i]);
								} catch (Exception ex) {
									d[i] = 0;
								}
							}
							Vector vec = new RandomAccessSparseVector(c.length);
							vec.assign(d);
							VectorWritable writable = new VectorWritable();
							writable.set(vec);
							try {
								writer.append(new LongWritable(counter++), writable);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						writer.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} catch (FileNotFoundException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}
}
