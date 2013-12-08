package easyMahout.utils;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import easyMahout.GUI.MainGUI;

public class recommenderFileConfig {
	
	private final static Logger log = Logger.getLogger(recommenderFileConfig.class);
	
	public boolean loadXMLPreferencesFile(){
		
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Document doc = docBuilder.parse (new File("prueba.xml"));
			doc.getDocumentElement().normalize ();
			
			if(doc.getDocumentElement().getNodeName().equals(Constants.XML.RECOMMENDER)){
				NodeList listaPersonas = doc.getElementsByTagName("type");
				
				int totalPersonas = listaPersonas.getLength();
				Node persona = listaPersonas.item(0);

				
				System.out.println("Número total de personas : " + persona);	
			}
			else {
				log.debug("The file is not a RECOMMENDER preferences file.");
				MainGUI.writeResult("The file is not a RECOMMENDER preferences file.", Constants.Log.ERROR);				
			}
			
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

		
	}

}
