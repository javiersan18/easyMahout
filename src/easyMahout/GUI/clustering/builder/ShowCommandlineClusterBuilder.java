package easyMahout.GUI.clustering.builder;

import easyMahout.GUI.clustering.ConvergenceTresholdPanel;
import easyMahout.GUI.clustering.DataModelClusterPanel;
import easyMahout.GUI.clustering.DistanceMeasurePanel;
import easyMahout.GUI.clustering.MaxIterationsPanel;

public class ShowCommandlineClusterBuilder {
	public static final StringBuilder buildCommandline() {
		
		String[] argsSequence = JobClusterBuilder.buildSequenceFromDirectoryJob();
		String[] argsSplit = JobClusterBuilder.buildSplitJob();
		String[] argsSparse = JobClusterBuilder.buildSparseVectorsJob();
		
		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append("#!/bin/bash")
				.append("\n\n")
				.append("MAHOUT=\"./bin/mahout\"")
				.append("\n\n")
				.append("$MAHOUT seq2sparse")
				.append("\n\t")
				.append(argsSequence[0] + " " + argsSequence[1])
				.append("\n\t")
				.append(argsSequence[2] + " " + argsSequence[3])
				.append("\n\t")
				.append(argsSequence[4] + " " + argsSequence[5])
				.append("\n\n")
				.append("$MAHOUT seq2sparse")
				.append("\n\t")
				.append(argsSparse[0] + " " + argsSparse[1])
				.append("\n\t")
				.append(argsSparse[2] + " " + argsSparse[3])
				.append("\n\t")
				.append(argsSparse[4])
				.append("\n\t")
				.append(argsSparse[5])
				.append("\n\t")
				.append(argsSparse[6])
				.append("\n\t")
				.append(argsSparse[7] + " " + argsSparse[8])
				.append("\n\n")
				.append("$MAHOUT split")
				.append("\n\t")
				.append(argsSplit[0] + " " + argsSplit[1])
				.append("\n\t")
				.append(argsSplit[2] + " " + argsSplit[3])
				.append("\n\t")
				.append(argsSplit[4])
				.append("\n\t")
				.append(argsSplit[5])
				.append("\n\t")
				.append(argsSplit[6] + " " + argsSplit[7])
				.append("\n\t")
				.append(argsSplit[8] + " " + argsSplit[9])
				.append("\n\n")
				.append("$MAHOUT kmeans")
				.append("\n\t")
				.append("--input " + argsSparse[3] + "/tfidf-vectors")
				.append("\n\t")
				.append("--output " + argsSparse[3] + "/output")
				.append("\n\t")
				.append("--clusters "+ DataModelClusterPanel.getOutputPath() + "/clusters" + "clusters-0-final")
				.append("\n\t")
				.append("--convergenceDelta" + ConvergenceTresholdPanel.getConvergenceTreshold())
				.append("\n\t")
				.append("--maxIter" + MaxIterationsPanel.getNumeroIteraciones())
				.append("\n\t")
				.append("--clustering")
				.append("\n\t")
				.append("--method mapreduce")
				.append("\n\t")
				.append("--distanceMeasure " + DistanceMeasurePanel.getSelectedType())
				.append("\n\n");
						
		return textBuilder;		
	}
}
