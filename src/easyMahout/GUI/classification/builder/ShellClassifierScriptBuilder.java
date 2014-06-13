package easyMahout.GUI.classification.builder;

import java.io.FileWriter;
import java.io.PrintWriter;

import easyMahout.GUI.PreferencesPanel;

public class ShellClassifierScriptBuilder {

	public static String buildClassifierScript(String[][] args) {

		StringBuilder textBuilder = new StringBuilder();
		String mahoutHome = PreferencesPanel.getMahoutHome();

		textBuilder.append("#!/bin/bash").append("\n\n")
				.append("MAHOUT=./bin/mahout").append("\n\n")
				.append("cd "+ System.getenv("PWD") + "/" + mahoutHome)
				.append("\n\n")
				.append("$MAHOUT seqdirectory \\").append("\n\t")
				.append(args[0][0] + " " + args[0][1] + " \\").append("\n\t")
				.append(args[0][2] + " " + args[0][3] + " \\").append("\n\t")
				.append(args[0][4] + " \\").append("\n\t")
				.append(args[0][5] + " " + args[0][6]).append("\n\n")
				
				.append("$MAHOUT seq2sparse \\").append("\n\t")
				.append(args[1][0] + " " + args[1][1] + " \\").append("\n\t")
				.append(args[1][2] + " " + args[1][3] + " \\").append("\n\t")
				.append(args[1][4] + " \\").append("\n\t")
				.append(args[1][5] + " \\").append("\n\t")
				.append(args[1][6] + " \\").append("\n\t")
				.append(args[1][7] + " " + args[1][8]).append("\n\n")
			
				.append("$MAHOUT split \\").append("\n\t")
				.append(args[2][0] + " " + args[2][1] + " \\").append("\n\t")
				.append(args[2][2] + " " + args[2][3] + " \\").append("\n\t")
				.append(args[2][4] + " " + args[2][5] + " \\").append("\n\t")
				.append(args[2][6] + " \\").append("\n\t")
				.append(args[2][7] + " \\").append("\n\t")
				.append(args[2][8] + " " + args[2][9] + " \\").append("\n\t")
				.append(args[2][10] + " " + args[2][11]).append("\n\n")
				
				.append("$MAHOUT trainnb \\").append("\n\t")
				.append(args[3][0] + " " + args[3][1] + " \\").append("\n\t")
				.append(args[3][6] + " " + args[3][7] + " \\").append("\n\t")
				.append(args[3][4] + " " + args[3][5] + " \\").append("\n\t")
				.append(args[3][2] + " " + args[3][3]).append("\n\n")
		
				.append("$MAHOUT testnb \\").append("\n\t")
				.append(args[4][0] + " " + args[4][1] + " \\").append("\n\t")
				.append(args[4][2] + " " + args[4][3] + " \\").append("\n\t")
				.append(args[4][4] + " " + args[4][5] + " \\").append("\n\t")
				.append(args[4][6] + " \\").append("\n\t")
				.append(args[4][7] + " " + args[4][8]).append("\n\n")
				
				.append("$MAHOUT testnb \\").append("\n\t")
				.append(args[5][0] + " " + args[5][1] + " \\").append("\n\t")
				.append(args[5][2] + " " + args[5][3] + " \\").append("\n\t")
				.append(args[5][4] + " " + args[5][5] + " \\").append("\n\t")
				.append(args[5][6] + " \\").append("\n\t")
				.append(args[5][7] + " " + args[5][8]).append("\n\n");

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

	public static String buildTestClassifierScript(String[][] args) {
		
		StringBuilder textBuilder = new StringBuilder();
		String mahoutHome = PreferencesPanel.getMahoutHome();

		textBuilder.append("#!/bin/bash").append("\n\n")
				.append("MAHOUT=./bin/mahout").append("\n\n")
				.append("cd "+ System.getenv("PWD") + "/" + mahoutHome)
				.append("\n\n")
				
				.append("$MAHOUT testnb \\").append("\n\t")
				.append(args[0][0] + " " + args[0][1] + " \\").append("\n\t")
				.append(args[0][2] + " " + args[0][3] + " \\").append("\n\t")
				.append(args[0][4] + " " + args[0][5] + " \\").append("\n\t")
				.append(args[0][6] + " \\").append("\n\t")
				.append(args[0][7] + " " + args[0][8]).append("\n\n");
				
		String absPath = "tempTestMahout.sh";

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
