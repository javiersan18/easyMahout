package easyMahout.GUI.recommender.builder;

import java.io.FileWriter;
import java.io.PrintWriter;

import easyMahout.GUI.PreferencesPanel;

public class ShellScriptBuilder {

	public static String buildRecommenderScript(String[] args) {

		StringBuilder textBuilder = new StringBuilder();
		String mahoutHome = PreferencesPanel.getMahoutHome();

		textBuilder.append("#!/bin/bash").append("\n\n")
				.append("MAHOUT=./bin/mahout").append("\n\n")
				.append("cd " + mahoutHome).append("\n\n")
				.append("$MAHOUT recommendfactorized \\").append("\n\t")
				.append(args[0] + " " + args[1] + "\\").append("\n\t")
				.append(args[2] + " " + args[3] + "\\").append("\n\t")
				.append(args[4] + " " + args[5] + "\\").append("\n\t")
				.append(args[6] + " " + args[7] + "\\").append("\n\t")
				.append(args[8] + " " + args[9] + "\\").append("\n\t")
				.append(args[10] + " " + args[11] + "\\").append("\n\t")
				.append(args[12] + " " + args[13]).append("\n\n");

		String absPath = "tempMahout.sh";

		FileWriter fileW = null;
		PrintWriter pw = null;
		try {
			fileW = new FileWriter(absPath);
			pw = new PrintWriter(fileW);
			pw.print(textBuilder.toString());
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if (null != fileW)
					fileW.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		return absPath;
	}

}
