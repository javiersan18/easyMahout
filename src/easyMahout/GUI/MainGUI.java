package easyMahout.GUI;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import easyMahout.GUI.classification.ClassificationJPanel;
import easyMahout.GUI.recommender.RecommenderJPanel;
import easyMahout.utils.Constants;

import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame formEasymahout;

	private RecommenderJPanel recommenderTab;

	private ClassificationJPanel classificationTab;

	private JScrollPane logScrollPane;

	private static JTextPane logTextPane;

	private static StringBuilder textBuilder;

	private final static Logger log = Logger.getLogger(MainGUI.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			// Set System(Windows, Mac, linux) Look and fell
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGUI window = new MainGUI();
					window.formEasymahout.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/easyMahout/log4j.properties");

		formEasymahout = new JFrame();
		formEasymahout.setTitle("easyMahout 0.2");

		formEasymahout.setMinimumSize(new Dimension(750, 650));
		formEasymahout.setBounds(100, 100, 750, 700);

		formEasymahout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		formEasymahout.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(2, 0, 732, 463);
		formEasymahout.getContentPane().add(tabbedPane);

		recommenderTab = new RecommenderJPanel();
		tabbedPane.addTab("Recommendation", null, recommenderTab, null);

		classificationTab = new ClassificationJPanel();
		tabbedPane.addTab("Classification", null, classificationTab, null);

		createMenuBar();
		textBuilder = new StringBuilder();

		logScrollPane = new JScrollPane();
		logScrollPane.setBounds(2, 463, 730, 176);
		formEasymahout.getContentPane().add(logScrollPane);

		// Result log textField
		logTextPane = new JTextPane();
		logScrollPane.setViewportView(logTextPane);
		logTextPane.setBackground(Color.WHITE);
		logTextPane.setBounds(42, 501, 735, 131);
		logTextPane.setEditable(false);
		logTextPane.setContentType("text/html");

		loadXMLFile();
		
	}

	private boolean loadXMLFile() {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = null;
		try {
			docBuilder = docBuilderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Document doc = docBuilder.parse(new File("prueba.xml"));
			doc.getDocumentElement().normalize();

			if (doc.getDocumentElement().getNodeName().equals(Constants.XML.RECOMMENDER)) {

				Element element;

				NodeList xmlNodes = doc.getElementsByTagName("type");
				Node xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : " + getTagValue("value", element));
				}

				xmlNodes = doc.getElementsByTagName("datamodel");
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : " + getTagValue("boolean", element));
					System.out.println("Nombre : " + getTagValue("model", element));
					System.out.println("Nombre : " + getTagValue("delimiter", element));
					System.out.println("Nombre : " + getTagValue("path", element));
				}

				xmlNodes = doc.getElementsByTagName("similarity");
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : " + getTagValue("metric", element));
					System.out.println("Nombre : " + getTagValue("weighted", element));
				}

				xmlNodes = doc.getElementsByTagName("neighborhood");
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
					System.out.println("Nombre : " + getTagValue("function", element));
					System.out.println("Nombre : " + getTagValue("size", element));
					System.out.println("Nombre : " + getTagValue("threshold", element));
					System.out.println("Nombre : " + getTagValue("minimum", element));
					System.out.println("Nombre : " + getTagValue("sampling", element));
				}

				xmlNodes = doc.getElementsByTagName("evaluator");
				xmlNode = xmlNodes.item(0);

				if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
					element = (Element) xmlNode;
				}
				xmlNodes = doc.getElementsByTagName("query");
				int numQueries = xmlNodes.getLength();

				for (int i = 0; i < numQueries; i++) {

					xmlNode = xmlNodes.item(i);

					if (xmlNode.getNodeType() == Node.ELEMENT_NODE) {
						element = (Element) xmlNode;
						System.out.println("Nombre : " + getTagValue("selected", element));
						System.out.println("Nombre : " + getTagValue("userID", element));
						System.out.println("Nombre : " + getTagValue("howMany", element));
					}
				}
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
		}
		return true;
	}

	private static String getTagValue(String sTag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(sTag).item(0).getChildNodes();
		Node nValue = (Node) nlList.item(0);

		return nValue.getNodeValue();
	}

	public static void writeResult(String text, String type) {
		// TODO: poner la hora a los logs, scroll o popup para ver los
		// resultados comodamente.
		if (type.toLowerCase().equals(Constants.Log.ERROR)) {
			textBuilder.append("<font color=red>ERROR: ").append(text).append("</font><br>");
		} else if (type.toLowerCase().equals(Constants.Log.WARNING)) {
			textBuilder.append("<font color=yellow>WARNING: ").append(text).append("</font><br>");
		} else if (type.toLowerCase().equals(Constants.Log.RESULT)) {
			textBuilder.append("<font color=black>RESULT: ").append(text).append("</font><br>");
		} else if (type.toLowerCase().equals(Constants.Log.INFO)) {
			textBuilder.append("<font color=green>INFO: ").append(text).append("</font><br>");
		}

		logTextPane.setText(textBuilder.toString());

	}

	private void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		formEasymahout.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
	}
}
