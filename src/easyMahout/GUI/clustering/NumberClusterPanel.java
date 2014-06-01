package easyMahout.GUI.clustering;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClusterTips;

import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

public class NumberClusterPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel number;
	
	private JTextField fieldNum;
	
	private int numeroClusters;

	private HelpTooltip helpTooltip;
	
	@SuppressWarnings("unused")
	private final static Logger log = Logger.getLogger(NumberClusterPanel.class);
	
	private final static String HELP = "[1..9999]";
	
	public NumberClusterPanel() {
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Number of clusters", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		setBounds(228, 11, 480, 408);
		
		number = new JLabel("Number of clusters:");
		number.setBounds(38,36, 189, 14);
		add(number);

		fieldNum = new JTextField();
		fieldNum.setBounds(38, 61, 93, 23);
		fieldNum.setToolTipText(HELP);
		add(fieldNum);
		fieldNum.setColumns(10);
		fieldNum.setText("5");  
		fieldNum.setInputVerifier(new InputVerifier() {
			public boolean verify(JComponent input) {
				JTextField tf = (JTextField) input;
				String text = tf.getText();
				try {
					Integer i = Integer.parseInt(text);
					if (i >= 1 && i <= 9999) {
						fieldNum.setBackground(Color.WHITE);
						return true;
					} else {
						MainGUI.writeResult("Size has to be an integer number in range [1..9999]", Constants.Log.ERROR);
						fieldNum.setBackground(new Color(240, 128, 128));
						return false;
					}
				} catch (NumberFormatException e) {
					MainGUI.writeResult("Size has to be an integer number in range [1..9999]", Constants.Log.ERROR);
					fieldNum.setBackground(new Color(240, 128, 128));
					return false;
				}
			}
		});
		
		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHelp.setPreferredSize(new Dimension(65, 40));
		btnHelp.setBounds(10, 358, 40, 40);
		add(btnHelp);

		// Help Tip
		helpTooltip = new HelpTooltip(btnHelp, ClusterTips.CLUSTER_NCLUSTERS);
		add(helpTooltip);

	}

	public static boolean isInteger(String s) {
	    try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    }
	    // if exception isn't thrown, then it is an integer
	    return true;
	}
	
	public HelpTooltip getHelpTooltip() {
		return helpTooltip;
	}

	public int getNumeroClusters() {
		return numeroClusters;
	}

	public void setNumeroClusters(int numeroClusters) {
		this.numeroClusters = numeroClusters;
	}
	
	public JTextField getCampoNum(){
		return fieldNum;
	}
	 
}

