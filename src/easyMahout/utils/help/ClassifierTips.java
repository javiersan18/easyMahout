package easyMahout.utils.help;

public class ClassifierTips {

	public static final String CLASSIF_CONFIG_DIST = "<html>"
			+ "<h4>Steps to build a classifier</h4>"
			+ "<br><p><u><b>1.</b></u> The system generates classifiers using an algorithm: </p>" +
			"<p> Naive Bayes, Complementary Naive Bayes</p>"
			+ "<br><p><u><b>2.</b></u> With the input data the algorithm creates a model.</p>" +
			"<p>This model can be used for test new data o the data used during the training. You can configure the amount of data that will be used for test. </p>" +
			"<br><p><u><b>3.</b></u> Select the input data and RUN the classifier.</p>"
			+ "<br><p><u><b>4.</b></u> You can use the model created before to test new data.</p>" + "</html>";
	
	public static final String CLASSIF_CONFIG = "<html>"
			+ "<h4>Steps to build a classifier</h4>"
			+ "<br><p><u><b>1.</b></u> The system generates classifiers using an algorithm: </p>" +
			"<p> Naive Bayes, Complementary Naive Bayes</p>"
			+ "<br><p><u><b>2.</b></u> With the input data the algorithm creates a model.</p>" +
			"<p>This model can be used for test new data o the data used during the training. You can configure the amount of data that will be used for test. </p>" +
			"<br><p><u><b>3.</b></u> Select the input data and RUN the classifier.</p>"
			+ "<br><p><u><b>4.</b></u> You can use the model created before to test new data.</p>" + "</html>";
	
	public static final String CLASSIFIER = "docs/tooltips/classifier/classifier.html";

	public static final String CLASSIFIER_TYPE = "<html>"
			+ "<h4>Classifier Types</h4>"
			+ "<br><p><u><b>Based on different algorithms:</b></u> The system generates classifiers using information</p>"
			+ "<p> for analyse it and extract conclusions about one target.</p><p> The algoritms avalaible in easyMahout " +
			" are Naive Bayes and Complementary Naive Bayes.</p>"
			+ "</html>";

	public static final String CLASSIFIER_DATAMODEL = "<html>"
			+ "<h4>Classifier Data Model</h4>"
			+ "<br><p><u><b>Type of data model:</b></u> Usually <u>Extended</u> is the one used</p>"
			+ "<br><p><u><b>Delimiter:</b></u> Which normally is  <b>;</b>  <b>,</b>  <b>:</b> etc.</p>"
			+"</html>";

	public static final String CLASSIFIER_ALGORITHM = "<html>"
			+ "<h4>Classifier Algorithms</h4>"
			+ "<br><p><u><b>Naive Bayes:</b></u> The system generates classifiers using Naive Bayes algorithm</p>"
			+ "<br><p><u><b>Complementary Naive Bayes:</b></u> he system generates classifiers using Complementary Naive Bayes algorithm</p>"
			+"</html>";
	
	public static final String CLASSIFIER_TYPE_DIST = "<html>"
			+ "<h4>Classifier Types Distributed</h4>"
			+ "<br><p><u><b>Based on different algorithms:</b></u> The system generates classifiers using information</p>"
			+ "<p> for analyse it and extract conclusions about one target.</p><p> The algoritms avalaible in easyMahout " +
			" are Naive Bayes and Complementary Naive Bayes.</p>"
			+ "</html>";

	public static final String CLASSIFIER_DATAMODEL_DIST = "<html>"
	+ "<h4>Data Model(Hadoop)</h4>"
	+"<p> Here you have to insert your data folder. " + "<br>"
	+ " in order to run hadoop classifier on that data</p> ";

	public static final String CLASSIFIER_JOB = "docs/tooltips/classifier/job_dist.html";

	public static final String CLASSIFIER_TESTDATA = "<html>"
			+ "<h4>Classifier Test Data</h4>"
			+ "<br><p><u><b>Select the amount of data used to test the model created by the algorithm</b></u></p>" +
			"<p></p>" +
			" You can choose by percent or by size and if it is randomly or not.</p>"
			+ "</html>";

	public static final String CLASSIFIER_TESTMODEL = "<html>"
			+ "<h4>Classifier Test Model</h4>"
			+ "<br><p><u><b>Use a model created before to test new data:</b></u></p> " +
			"<p></p>" +
			" You must to put the input data, the path of the model and the label index.</p>"
			+ "</html>";


}
