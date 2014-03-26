package easyMahout.GUI.clustering.builder;

import java.io.IOException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.mahout.math.VectorWritable;

public class ReadSequenceFile {
	public static void readSequenceFile(String input,String output)
	{

		Path path = new Path(input);

		FileWriter writer;
		try {
			writer = new FileWriter(output);
			PrintWriter pw = new PrintWriter(writer);
			String newline = System.getProperty("line.separator");
			//creating header
			pw.print("key,value" + newline);
			//creating Sequence Writer
			Configuration conf = new Configuration();
			FileSystem fs;
			try {
				fs = FileSystem.get(conf);
				SequenceFile.Reader reader;
				try {
					reader = new SequenceFile.Reader(fs,path,conf);
					LongWritable key = new LongWritable();
					VectorWritable value = new VectorWritable();
					try {
						while (reader.next(key, value)) {
							System.out.println( "reading key:" + key.toString() +
									" with value " + value.toString());
							pw.print(key.toString() + "," + value.toString() +
									newline);
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
					pw.close();
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
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

