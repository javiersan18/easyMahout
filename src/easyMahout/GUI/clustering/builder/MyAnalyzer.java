package easyMahout.GUI.clustering.builder;

import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Tokenizer;

import easyMahout.GUI.clustering.DataModelClusterPanel;

public class MyAnalyzer extends Analyzer {

	@Override
	protected TokenStreamComponents createComponents(String arg0, Reader arg1) {
		// TODO Auto-generated method stub
	
		return new TokenStreamComponents((Tokenizer) DataModelClusterPanel.getDataModel());
	}

}
