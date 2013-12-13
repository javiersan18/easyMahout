package easyMahout.recommender;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import easyMahout.GUI.MainGUI;
import easyMahout.utils.Constants;

public class RecommenderXMLPreferences {

	private final static Logger log = Logger
			.getLogger(RecommenderXMLPreferences.class);

	public static boolean saveXMLFile() {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(Constants.RecommenderXML.RECOMMENDER);
			doc.appendChild(rootElement);

			// name element
			Element name = doc.createElement(Constants.RecommenderXML.RECOMM_NAME);
			rootElement.appendChild(name);

			Element value = doc.createElement(Constants.RecommenderXML.RECOMM_NAME_VALUE);
			value.appendChild(doc.createTextNode("Ejemplo2"));
			name.appendChild(value);

			// type element
			Element type = doc.createElement(Constants.RecommenderXML.RECOMM_TYPE);
			rootElement.appendChild(type);

			value = doc.createElement(Constants.RecommenderXML.RECOMM_TYPE_VALUE);
			value.appendChild(doc.createTextNode("User Based"));
			type.appendChild(value);

			// datamodel element
			Element datamodel = doc
					.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL);
			rootElement.appendChild(datamodel);

			Element _boolean = doc
					.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_BOOLEAN);
			_boolean.appendChild(doc.createTextNode("false"));
			datamodel.appendChild(_boolean);

			Element model = doc
					.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_MODEL);
			model.appendChild(doc.createTextNode("Generic"));
			datamodel.appendChild(model);

			Element delimiter = doc
					.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_DELIMITER);
			delimiter.appendChild(doc.createTextNode(" "));
			datamodel.appendChild(delimiter);

			Element path = doc
					.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_PATH);
			path.appendChild(doc.createTextNode("C:\\file2.xml"));
			datamodel.appendChild(path);

			// similarity element
			Element similarity = doc
					.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY);
			rootElement.appendChild(similarity);

			Element metric = doc
					.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_METRIC);
			metric.appendChild(doc.createTextNode("Pearson Correlation"));
			similarity.appendChild(metric);

			Element weighted = doc
					.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_WEIGHTED);
			weighted.appendChild(doc.createTextNode("false"));
			similarity.appendChild(weighted);

			// neighborhood element
			Element neighborhood = doc
					.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD);
			rootElement.appendChild(neighborhood);

			Element function = doc
					.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_FUNCTION);
			function.appendChild(doc.createTextNode("Nearest N"));
			neighborhood.appendChild(function);

			Element size = doc
					.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_SIZE);
			size.appendChild(doc.createTextNode("2"));
			neighborhood.appendChild(size);

			Element threshold = doc
					.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_THRESHOLD);
			threshold.appendChild(doc.createTextNode(" "));
			neighborhood.appendChild(threshold);

			Element minimum = doc
					.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_MINIMUM);
			minimum.appendChild(doc.createTextNode(" "));
			neighborhood.appendChild(minimum);

			Element sampling = doc
					.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_SAMPLING);
			sampling.appendChild(doc.createTextNode("1.0"));
			neighborhood.appendChild(sampling);

			// evaluator element
			Element evaluator = doc
					.createElement(Constants.RecommenderXML.RECOMM_EVALUATOR);
			rootElement.appendChild(evaluator);

			// queries element
			Element query = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY);
			rootElement.appendChild(query);

			Element selected = doc
					.createElement(Constants.RecommenderXML.RECOMM_QUERY_SELECTED);
			selected.appendChild(doc.createTextNode("false"));
			query.appendChild(selected);

			Element user = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY_USERID);
			user.appendChild(doc.createTextNode("1"));
			query.appendChild(user);

			Element howmany = doc
					.createElement(Constants.RecommenderXML.RECOMM_QUERY_HOWMANY);
			howmany.appendChild(doc.createTextNode("1"));
			query.appendChild(howmany);

			query = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY);
			rootElement.appendChild(query);

			selected = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY_SELECTED);
			selected.appendChild(doc.createTextNode("tue"));
			query.appendChild(selected);

			user = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY_USERID);
			user.appendChild(doc.createTextNode("2"));
			query.appendChild(user);

			howmany = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY_HOWMANY);
			howmany.appendChild(doc.createTextNode("2"));
			query.appendChild(howmany);

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			File f = new File("D:\\file.xml");
			StreamResult result = new StreamResult(f);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
		return true;
	}

	public static boolean loadXMLFile() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory
				.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Document doc = docBuilder.parse(new File("D:\\file.xml"));
			doc.getDocumentElement().normalize();

			if (doc.getDocumentElement().getNodeName()
					.equals(Constants.RecommenderXML.RECOMMENDER)) {

				Element element;

				NodeList xmlNodes = doc
						.getElementsByTagName(Constants.RecommenderXML.RECOMM_NAME);
				Node xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : "
							+ getTagValue("value", element));
				}
				
				xmlNodes = doc
						.getElementsByTagName(Constants.RecommenderXML.RECOMM_TYPE);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : "
							+ getTagValue("value", element));
				}

				xmlNodes = doc
						.getElementsByTagName(Constants.RecommenderXML.RECOMM_DATAMODEL);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : "
							+ getTagValue("boolean", element));
					System.out.println("Nombre : "
							+ getTagValue("model", element));
					System.out.println("Nombre : "
							+ getTagValue("delimiter", element));
					System.out.println("Nombre : "
							+ getTagValue("path", element));
				}

				xmlNodes = doc
						.getElementsByTagName(Constants.RecommenderXML.RECOMM_SIMILARITY);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : "
							+ getTagValue("metric", element));
					System.out.println("Nombre : "
							+ getTagValue("weighted", element));
				}

				xmlNodes = doc
						.getElementsByTagName(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : "
							+ getTagValue("function", element));
					System.out.println("Nombre : "
							+ getTagValue("size", element));
					System.out.println("Nombre : "
							+ getTagValue("threshold", element));
					System.out.println("Nombre : "
							+ getTagValue("minimum", element));
					System.out.println("Nombre : "
							+ getTagValue("sampling", element));
				}

				xmlNodes = doc
						.getElementsByTagName(Constants.RecommenderXML.RECOMM_EVALUATOR);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
				}
				xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_QUERY);
				int numQueries = xmlNodes.getLength();

				for (int i = 0; i < numQueries; i++) {

					xmlNode = xmlNodes.item(i);

					if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
						element = (Element) xmlNode;
						System.out.println("Nombre : "
								+ getTagValue("selected", element));
						System.out.println("Nombre : "
								+ getTagValue("userID", element));
						System.out.println("Nombre : "
								+ getTagValue("howMany", element));
					}
				}
			} else {
				log.debug("The file is not a RECOMMENDER preferences file.");
				MainGUI.writeResult(
						"The file is not a RECOMMENDER preferences file.",
						Constants.Log.ERROR);
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainGUI.writeResult("The system cannot find the file specified.",
					Constants.Log.ERROR);
		}
		return true;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0)
				.getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

}
