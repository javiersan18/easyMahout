package easyMahout.utils.xml;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.PreferencesPanel;
import easyMahout.GUI.recommender.DataModelRecommenderPanel;
import easyMahout.GUI.recommender.EvaluatorRecommenderPanel;
import easyMahout.GUI.recommender.MainRecommenderPanel;
import easyMahout.GUI.recommender.NeighborhoodRecommenderPanel;
import easyMahout.GUI.recommender.QueriesRecommenderPanel;
import easyMahout.GUI.recommender.SimilarityRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.Constants.Log;
import easyMahout.utils.Constants.Neighborhood;
import easyMahout.utils.Constants.RecommenderXML;

public class XMLPreferences {

	private final static Logger log = Logger.getLogger(XMLPreferences.class);

	public static boolean saveXMLFile(String filePath) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(Constants.PreferencesXML.PREFERENCES);
			doc.appendChild(rootElement);

			// java home element
			Element java = doc.createElement(Constants.PreferencesXML.JAVA_HOME);
			java.appendChild(doc.createTextNode(PreferencesPanel.getJavaHome()));
			rootElement.appendChild(java);

			// mahout home element
			Element mahout = doc.createElement(Constants.PreferencesXML.MAHOUT_HOME);
			mahout.appendChild(doc.createTextNode(PreferencesPanel.getMahoutHome()));
			rootElement.appendChild(mahout);

			// hadoop home element
			Element hadoop = doc.createElement(Constants.PreferencesXML.HADOOP_HOME);
			hadoop.appendChild(doc.createTextNode(PreferencesPanel.getHadoopHome()));
			rootElement.appendChild(hadoop);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			if (!StringUtils.contains(filePath, ".xml")) {
				filePath += ".xml";
			}
			File f = new File(filePath);
			StreamResult result = new StreamResult(f);

			transformer.transform(source, result);

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		return true;
	}

	public static boolean loadXMLFile(String filePath) {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Document doc;
		try {
			Configuration conf = new Configuration();
			FileSystem fs = FileSystem.get(conf);
			Path path = new Path(filePath);
			if (fs.exists(path)) {
				doc = docBuilder.parse(new File(filePath));
				doc.getDocumentElement().normalize();

				NodeList xmlNodes = doc.getDocumentElement().getChildNodes();
				String xmlNodeJava = xmlNodes.item(0).getChildNodes().item(0).getNodeValue().toString();
				String xmlNodeMahout = xmlNodes.item(1).getChildNodes().item(0).getNodeValue().toString();
				String xmlNodeHadoop = xmlNodes.item(2).getChildNodes().item(0).getNodeValue().toString();
				System.out.println("SET JAVA_HOME=" + xmlNodeJava);
				System.out.println("SET MAHOUT_HOME=" + xmlNodeMahout);
				System.out.println("SET HADOOP_HOME=" + xmlNodeHadoop);
				PreferencesPanel.setJavaHome(xmlNodeJava);
				PreferencesPanel.setMahoutHome(xmlNodeMahout);
				PreferencesPanel.setHadoopHome(xmlNodeHadoop);
			} else {
				PreferencesPanel.setJavaHome(Constants.EnviromentVariables.JAVA_HOME);
				PreferencesPanel.setMahoutHome(Constants.EnviromentVariables.MAHOUT_HOME);
				PreferencesPanel.setHadoopHome(Constants.EnviromentVariables.HADOOP_HOME);
			}

		} catch (SAXException | IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue().toString();
	}

	public static String getTagName(String file) {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Document doc = docBuilder.parse(new File(file));
			doc.getDocumentElement().normalize();

			if (doc.getDocumentElement().getNodeName().equals(Constants.RecommenderXML.RECOMMENDER)) {

				Element element;

				NodeList xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_NAME);
				Node xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					return getTagValue("value", element);
				}
			}
		} catch (SAXException e1) {
			// TODO puede que mas errores acerca del parsing
			e1.printStackTrace();
			MainGUI.writeResult("The system cannot read the specified file. Possibly it is not a XML preferences file or it has some error.",
					Constants.Log.ERROR);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			MainGUI.writeResult("The system cannot find the specified file.", Constants.Log.ERROR);
		}
		return null;
	}

}
