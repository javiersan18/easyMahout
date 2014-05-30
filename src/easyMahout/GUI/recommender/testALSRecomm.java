package easyMahout.GUI.recommender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.bigtop.itest.shell.Shell;
import org.apache.cassandra.thrift.Cassandra.system_add_column_family_args;

import easyMahout.utils.Environment;

public class testALSRecomm {

	public static final String TEMP_DIR = "/tmp/mahout" + new Date().getTime();
	public static final String WORK_DIR = TEMP_DIR;

	public static String MAHOUT_HOME = System.getenv("MAHOUT_HOME");
	public static String MAHOUT = MAHOUT_HOME != null ? MAHOUT_HOME
			+ "/bin/mahout" : "mahout";

	private static Shell sh = new Shell("/bin/bash");
	static final int ITERATIONS = 2;

	public static void main(String[] args) {
		// Environment env;
		// try {
		// env = new Environment();
		// env.setProperty("MAHOUT_HOME", "/home/javi/mahout-distribution-0.8");
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//

		//
		// //sh.exec("export A=/home/javi/mahout-distribution-0.8");
		// //System.out.println(sh.getRet());
		// //System.out.println(sh.getOut());
		sh.exec("pwd");
		System.out.println(sh.getRet());
		System.out.println(sh.getOut());

		try {
			Process proc = Runtime.getRuntime().exec("sh factorizer.sh", new String[]{"JAVA_HOME=/usr/lib/java/jdk1.8.0_05","HADOOP_HOME=/home/javi/hadoop-1.1.2"});
			int exitValue = proc.waitFor();

			BufferedReader is;
			String line;

			is = new BufferedReader(
					new InputStreamReader(proc.getInputStream()));

			while ((line = is.readLine()) != null) {
				System.out.println(line);
			}

			//System.out.println(proc.getOutputStream());
			//System.out.println(exitValue);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// iterations for factorizer, original value was "10",
		// on a small 4 node cluster, 2 iterations
		// should complete in about 5 minutes or so.

		// sh.exec("splitDataset --input ${WORK_DIR}/movielens/ratings.csv --output ${WORK_DIR}/dataset "
		// +
		// "--trainingPercentage 0.9 --probePercentage 0.1 --tempDir ${WORK_DIR}/dataset/tmp");
		//
		// // Default iterations was 10, but for simple smokes that most might
		// run,
		// // 2 iterations will confirm enough to move on.
		//
		// // run distributed ALS-WR to factorize the rating matrix based on the
		// // training set
		//
		// sh.exec("parallelALS --input ${WORK_DIR}/dataset/trainingSet/ --output ${WORK_DIR}/als/out "
		// +
		// "--tempDir ${WORK_DIR}/als/tmp --numFeatures 20 --numIterations ${ITERATIONS} --lambda 0.065");
		//
		// // remove this
		// sh.exec("hadoop fs -ls ${WORK_DIR}/als/out >> /tmp/mahoutdebug");
		// // compute predictions against the probe set, measure the error
		// sh.exec("evaluateFactorization --output ${WORK_DIR}/als/rmse --input ${WORK_DIR}/dataset/probeSet/ "
		// +
		// "--userFeatures ${WORK_DIR}/als/out/U/ --itemFeatures ${WORK_DIR}/als/out/M/ --tempDir ${WORK_DIR}/als/tmp");
		//
		// // compute recommendations
		// sh.exec("recommendfactorized --input ${WORK_DIR}/als/out/userRatings/ --output ${WORK_DIR}/recommendations "
		// +
		// "--userFeatures ${WORK_DIR}/als/out/U/ --itemFeatures ${WORK_DIR}/als/out/M/ "
		// + "--numRecommendations 6 --maxRating 5");
		//
		// // print the error
		// sh.exec("hadoop fs -cat ${WORK_DIR}/als/rmse/rmse.txt");
		//
		// // check that recommendations has been calculated
		// sh.exec("hadoop fs -test -e ${WORK_DIR}/recommendations/part-m-00000");
		// //
		// assertEquals("${WORK_DIR}/recommendations/part-m-00000 does not exist",
		// // 0, sh.getRet());

	}
}
