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
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import easyMahout.GUI.MainGUI;
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

public class RecommenderXMLPreferences {

	private final static Logger log = Logger.getLogger(RecommenderXMLPreferences.class);

	public static boolean saveXMLFile(String filePath) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(Constants.RecommenderXML.RECOMMENDER);
			doc.appendChild(rootElement);

			// name element
			Element name = doc.createElement(Constants.RecommenderXML.RECOMM_NAME);
			rootElement.appendChild(name);

			Element value = doc.createElement(Constants.RecommenderXML.RECOMM_NAME_VALUE);
			value.appendChild(doc.createTextNode(MainRecommenderPanel.getFileName()));
			name.appendChild(value);

			// distributed element
			Element distributed = doc.createElement(Constants.RecommenderXML.RECOMM_DISTRIBUTED);
			rootElement.appendChild(distributed);

			value = doc.createElement(Constants.RecommenderXML.RECOMM_DISTRIBUTED_VALUE);
			value.appendChild(doc.createTextNode(Boolean.toString(MainGUI.isDistributed())));
			distributed.appendChild(value);

			// type element
			Element type = doc.createElement(Constants.RecommenderXML.RECOMM_TYPE);
			rootElement.appendChild(type);

			value = doc.createElement(Constants.RecommenderXML.RECOMM_TYPE_VALUE);
			value.appendChild(doc.createTextNode(TypeRecommenderPanel.getSelectedType()));
			type.appendChild(value);

			// datamodel element
			Element datamodel = doc.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL);
			rootElement.appendChild(datamodel);

			Element _boolean = doc.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_BOOLEAN);
			_boolean.appendChild(doc.createTextNode(DataModelRecommenderPanel.getBooleanPrefs()));
			datamodel.appendChild(_boolean);

			Element model = doc.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_MODEL);
			model.appendChild(doc.createTextNode(DataModelRecommenderPanel.getSelectedDataModel()));
			datamodel.appendChild(model);

			Element delimiter = doc.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_DELIMITER);
			delimiter.appendChild(doc.createTextNode(DataModelRecommenderPanel.getDelimiter()));
			datamodel.appendChild(delimiter);

			Element inputPath = doc.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_INPUTPATH);
			inputPath.appendChild(doc.createTextNode(DataModelRecommenderPanel.getInputPath()));
			datamodel.appendChild(inputPath);

			Element outputPath = doc.createElement(Constants.RecommenderXML.RECOMM_DATAMODEL_OUTPUTPATH);			
			outputPath.appendChild(doc.createTextNode(DataModelRecommenderPanel.getOutputPath()));
			datamodel.appendChild(outputPath);

			// similarity element
			Element similarity = doc.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY);
			rootElement.appendChild(similarity);

			Element metric = doc.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_METRIC);
			metric.appendChild(doc.createTextNode(SimilarityRecommenderPanel.getSelectedMetric()));
			similarity.appendChild(metric);

			Element weighted = doc.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_WEIGHTED);
			weighted.appendChild(doc.createTextNode(SimilarityRecommenderPanel.getWeighted()));
			similarity.appendChild(weighted);

			Element maxsim = doc.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_MAXSIM);
			maxsim.appendChild(doc.createTextNode(SimilarityRecommenderPanel.getMaxSimilarities()));
			similarity.appendChild(maxsim);

			Element maxprefs = doc.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_MAXPREFS);
			maxprefs.appendChild(doc.createTextNode(SimilarityRecommenderPanel.getMaxPreferences()));
			similarity.appendChild(maxprefs);

			Element minprefs = doc.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_MINPREFS);
			minprefs.appendChild(doc.createTextNode(SimilarityRecommenderPanel.getMinPreferences()));
			similarity.appendChild(minprefs);

			Element threshold = doc.createElement(Constants.RecommenderXML.RECOMM_SIMILARITY_THRESHOLD);
			threshold.appendChild(doc.createTextNode(SimilarityRecommenderPanel.getThreshold()));
			similarity.appendChild(threshold);

			// neighborhood element
			Element neighborhood = doc.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD);
			rootElement.appendChild(neighborhood);

			Element function = doc.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_FUNCTION);
			function.appendChild(doc.createTextNode(NeighborhoodRecommenderPanel.getSelectedFuction()));
			neighborhood.appendChild(function);

			Element size = doc.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_SIZE);
			size.appendChild(doc.createTextNode(NeighborhoodRecommenderPanel.getNeighborhoodSize()));
			neighborhood.appendChild(size);

			threshold = doc.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_THRESHOLD);
			threshold.appendChild(doc.createTextNode(NeighborhoodRecommenderPanel.getThreshold()));
			neighborhood.appendChild(threshold);

			Element minimum = doc.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_MINIMUM);
			minimum.appendChild(doc.createTextNode(NeighborhoodRecommenderPanel.getMinSimilarities()));
			neighborhood.appendChild(minimum);

			Element sampling = doc.createElement(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_SAMPLING);
			sampling.appendChild(doc.createTextNode(NeighborhoodRecommenderPanel.getSampling()));
			neighborhood.appendChild(sampling);

			// evaluator element
			Element evaluator = doc.createElement(Constants.RecommenderXML.RECOMM_EVALUATOR);
			rootElement.appendChild(evaluator);
			
			type = doc.createElement(Constants.RecommenderXML.RECOMM_EVALUATOR_TYPE);
			type.appendChild(doc.createTextNode(EvaluatorRecommenderPanel.getSelectedType()));
			evaluator.appendChild(type);
			
			Element training = doc.createElement(Constants.RecommenderXML.RECOMM_EVALUATOR_TRAINING);
			training.appendChild(doc.createTextNode(EvaluatorRecommenderPanel.getTraining()));
			evaluator.appendChild(training);
			
			Element evaluation = doc.createElement(Constants.RecommenderXML.RECOMM_EVALUATOR_EVALUATION);
			evaluation.appendChild(doc.createTextNode(EvaluatorRecommenderPanel.getEvaluation()));
			evaluator.appendChild(evaluation);
			
			Element top = doc.createElement(Constants.RecommenderXML.RECOMM_EVALUATOR_AT);
			top.appendChild(doc.createTextNode(EvaluatorRecommenderPanel.getTopN()));
			evaluator.appendChild(top);	

			// queries element
			Vector queries = QueriesRecommenderPanel.getQueries();
			Iterator it = queries.iterator();
			Vector row;

			while (it.hasNext()) {

				row = (Vector) it.next();

				boolean selectedCell = (boolean) row.elementAt(0);
				Long userID = (Long) row.elementAt(1);
				Integer howMany = (Integer) row.elementAt(2);

				if (userID != null && howMany != null) {
					Element query = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY);
					rootElement.appendChild(query);
					Element selected = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY_SELECTED);

					selected.appendChild(doc.createTextNode(Boolean.toString(selectedCell)));
					query.appendChild(selected);

					Element user = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY_USERID);
					user.appendChild(doc.createTextNode(Long.toString((Long) row.elementAt(1))));
					query.appendChild(user);

					Element howmany = doc.createElement(Constants.RecommenderXML.RECOMM_QUERY_HOWMANY);
					howmany.appendChild(doc.createTextNode(Integer.toString((int) row.elementAt(2))));
					query.appendChild(howmany);
				}

			}

			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			if (!StringUtils.contains(filePath, ".xml")) {
				filePath += ".xml";
			}
			File f = new File(filePath);
			StreamResult result = new StreamResult(f);

			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);

			transformer.transform(source, result);

			// TODO si el path viene del jfilechooser la ruta es entera C://...,
			// dejar la ruta solo saves/recommeder/file.xml...
			MainGUI.setMainTitle(filePath);
			MainRecommenderPanel.setConfigurationModified(false);

			System.out.println("File saved!");

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
		try {
			Document doc = docBuilder.parse(new File(filePath));
			doc.getDocumentElement().normalize();

			if (doc.getDocumentElement().getNodeName().equals(Constants.RecommenderXML.RECOMMENDER)) {

				Element element;
				boolean distributed = false;

				NodeList xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_NAME);
				Node xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					log.debug("Nombre : " + getTagValue(Constants.RecommenderXML.RECOMM_NAME_VALUE, element));
					// TODO crear nodo con nombre
				}

				xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_DISTRIBUTED);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					distributed = Boolean.valueOf(getTagValue(Constants.RecommenderXML.RECOMM_DISTRIBUTED_VALUE, element));
					MainGUI.setDistributedRecommPanel(distributed);
					log.debug("Distributed : " + getTagValue(Constants.RecommenderXML.RECOMM_DISTRIBUTED_VALUE, element));
				}

				xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_TYPE);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					TypeRecommenderPanel.setSelectedType(getTagValue(Constants.RecommenderXML.RECOMM_TYPE_VALUE, element));
					log.debug("Type : " + getTagValue("value", element));
				}

				xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_DATAMODEL);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;

					DataModelRecommenderPanel.setBooleanPrefs(Boolean.valueOf(getTagValue(Constants.RecommenderXML.RECOMM_DATAMODEL_BOOLEAN,
							element)));
					log.debug("Boolean : " + getTagValue("boolean", element));

					DataModelRecommenderPanel.setSelectedModel(getTagValue(Constants.RecommenderXML.RECOMM_DATAMODEL_MODEL, element));
					log.debug("Datamodel : " + getTagValue("model", element));

					DataModelRecommenderPanel.setDelimiter(getTagValue(Constants.RecommenderXML.RECOMM_DATAMODEL_DELIMITER, element));
					log.debug("Delimiter : " + getTagValue("delimiter", element));

					DataModelRecommenderPanel.setInputPath(getTagValue(Constants.RecommenderXML.RECOMM_DATAMODEL_INPUTPATH, element));
					log.debug("inputpath : " + getTagValue("inputPath", element));

					DataModelRecommenderPanel.setOutputPath(getTagValue(Constants.RecommenderXML.RECOMM_DATAMODEL_OUTPUTPATH, element));
					log.debug("outputpath : " + getTagValue("outputPath", element));
				}

				xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_SIMILARITY);
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;

					SimilarityRecommenderPanel.setSelectedMetric(getTagValue(Constants.RecommenderXML.RECOMM_SIMILARITY_METRIC, element));
					log.debug("Metric : " + getTagValue("metric", element));

					if (distributed) {

						SimilarityRecommenderPanel.setMaxSimilarities(getTagValue(Constants.RecommenderXML.RECOMM_SIMILARITY_MAXSIM,
								element));
						log.debug("Metric : " + getTagValue("maxsim", element));

						SimilarityRecommenderPanel.setMaxPreferences(getTagValue(Constants.RecommenderXML.RECOMM_SIMILARITY_MAXPREFS,
								element));
						log.debug("Weighted : " + getTagValue("maxprefs", element));

						SimilarityRecommenderPanel.setMinPreferences(getTagValue(Constants.RecommenderXML.RECOMM_SIMILARITY_MINPREFS,
								element));
						log.debug("Metric : " + getTagValue("minprefs", element));

						SimilarityRecommenderPanel.setThreshold(getTagValue(Constants.RecommenderXML.RECOMM_SIMILARITY_THRESHOLD, element));
						log.debug("Weighted : " + getTagValue("threshold", element));
					} else {
						SimilarityRecommenderPanel.setWeighted(Boolean.valueOf(getTagValue(Constants.RecommenderXML.RECOMM_SIMILARITY_WEIGHTED,
								element)));
						log.debug("Weighted : " + getTagValue("weighted", element));
					}
				}

				if (!distributed) {

					xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD);
					xmlNode = xmlNodes.item(0);

					if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
						element = (Element) xmlNode;

						String function = getTagValue(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_FUNCTION, element);
						NeighborhoodRecommenderPanel.setSelectedFuction(function);
						log.debug("Function : " + getTagValue("function", element));

						if (function.equals(Constants.Neighborhood.NEAREST)) {
							NeighborhoodRecommenderPanel.setSize(getTagValue(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_SIZE, element));
							log.debug("Size : " + getTagValue("size", element));
						} else {
							NeighborhoodRecommenderPanel.setThreshold(getTagValue(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_THRESHOLD,
									element));
							log.debug("Threshold : " + getTagValue("threshold", element));
						}

						NeighborhoodRecommenderPanel.setMinSimilarities(getTagValue(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_MINIMUM,
								element));
						log.debug("Minimun : " + getTagValue("minimum", element));
						NeighborhoodRecommenderPanel.setSampling(getTagValue(Constants.RecommenderXML.RECOMM_NEIGHBORHOOD_SAMPLING, element));
						log.debug("Sampling rate : " + getTagValue("sampling", element));
					}

					xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_EVALUATOR);
					xmlNode = xmlNodes.item(0);

					if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
						element = (Element) xmlNode;
						
						String type = getTagValue(Constants.RecommenderXML.RECOMM_EVALUATOR_TYPE, element);
						EvaluatorRecommenderPanel.setSelectedType(type);
						log.debug("Evaluator type : " + getTagValue("type", element));
						
						String evaluation = getTagValue(Constants.RecommenderXML.RECOMM_EVALUATOR_EVALUATION, element);
						EvaluatorRecommenderPanel.setEvaluation(evaluation);
						log.debug("Evaluator percentage : " + getTagValue("evaluation", element));
						
						String training = getTagValue(Constants.RecommenderXML.RECOMM_EVALUATOR_TRAINING, element);
						EvaluatorRecommenderPanel.setTraining(training);
						log.debug("Training percentage : " + getTagValue("training", element));
						
						String topN = getTagValue(Constants.RecommenderXML.RECOMM_EVALUATOR_AT, element);
						EvaluatorRecommenderPanel.setTopN(topN);
						log.debug("TopN : " + getTagValue("topN", element));						
					}

					xmlNodes = doc.getElementsByTagName(Constants.RecommenderXML.RECOMM_QUERY);
					int numQueries = xmlNodes.getLength();

					boolean selected = false;
					Long userID;
					Integer howMany;

					QueriesRecommenderPanel.emptyTable();

					for (int i = 0; i < numQueries; i++) {

						xmlNode = xmlNodes.item(i);

						if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
							element = (Element) xmlNode;
							selected = Boolean.valueOf(getTagValue(Constants.RecommenderXML.RECOMM_QUERY_HOWMANY, element));
							userID = Long.valueOf(getTagValue(Constants.RecommenderXML.RECOMM_QUERY_HOWMANY, element));
							howMany = Integer.valueOf(getTagValue(Constants.RecommenderXML.RECOMM_QUERY_HOWMANY, element));
							QueriesRecommenderPanel.addRow(selected, userID, howMany);
							log.debug("Selected : " + getTagValue("selected", element));
							log.debug("UserID : " + getTagValue("userID", element));
							log.debug("HoyMany : " + getTagValue("howMany", element));
						}
					}

				}

				MainGUI.setMainTitle(filePath);
				MainRecommenderPanel.setConfigurationModified(false);

			} else {
				log.debug("The file is not a RECOMMENDER preferences file.");
				MainGUI.writeResult("The file is not a RECOMMENDER preferences file.", Constants.Log.ERROR);
			}

		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainGUI.writeResult("The system cannot find the file specified.", Constants.Log.ERROR);
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
