package easyMahout.GUI;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

public class classificationJPanel extends JPanel {
	
	private JPanel panelClassification;
	
	private final static Logger log = Logger.getLogger(classificationJPanel.class);
	
	public classificationJPanel() {
		panelClassification = new JPanel();
		panelClassification.setLayout(null);
	}

	public JPanel getPanel() {
		return panelClassification;
	}

	public void setPanel(JPanel panelClassification) {
		this.panelClassification = panelClassification;
	}
	
	

}
