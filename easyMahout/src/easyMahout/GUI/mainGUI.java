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

public class mainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame formEasymahout;

	private recommenderJPanel recommenderTab;

	private classificationJPanel classificationTab;

	private static JTextPane logTextPane;

	private static StringBuilder textBuilder;

	private final static Logger log = Logger.getLogger(mainGUI.class);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainGUI window = new mainGUI();
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
	public mainGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		//TODO: WindowUtilities.setNativeLookAndFeel();

		BasicConfigurator.configure();
		PropertyConfigurator.configure("src/easyMahout/log4j.properties");

		formEasymahout = new JFrame();
		formEasymahout.setTitle("easyMahout 0.1");

		formEasymahout.setMinimumSize(new Dimension(750, 650));
		formEasymahout.setBounds(100, 100, 95, 279);

		formEasymahout.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		formEasymahout.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 734, 464);
		formEasymahout.getContentPane().add(tabbedPane);

		recommenderTab = new recommenderJPanel();
		tabbedPane.addTab("Recommendation", null, recommenderTab, null);

		classificationTab = new classificationJPanel();
		tabbedPane.addTab("Classification", null, classificationTab.getPanel(), null);

		createMenuBar();

		// Result log textField
		logTextPane = new JTextPane();
		logTextPane.setBounds(0, 462, 742, 131);
		formEasymahout.getContentPane().add(logTextPane);
		logTextPane.setEditable(false);
		logTextPane.setContentType("text/html");
		textBuilder = new StringBuilder();
	}

	protected static void writeResult(String text, String type) {
		//TODO: poner la hora a los logs, scroll o popup para ver los resultados comodamente.
		if (type.toLowerCase().equals("error")) {
			textBuilder.append("<font color=red>").append(text).append("</font><br>");
		} else if (type.toLowerCase().equals("warning")) {
			textBuilder.append("<font color=yellow>").append(text).append("</font><br>");
		} else if (type.toLowerCase().equals("result")) {
			textBuilder.append("<font color=black>").append(text).append("</font><br>");
		} else if (type.toLowerCase().equals("info")) {
			textBuilder.append("<font color=green>").append(text).append("</font><br>");
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
