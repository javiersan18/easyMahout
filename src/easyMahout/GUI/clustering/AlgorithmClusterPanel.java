package easyMahout.GUI.clustering;

	import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

	import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClusterTips;
import easyMahout.utils.help.RecommenderTips;
import easyMahout.utils.listeners.ItemChangeListener;

	//import easyMahout.utils.IconHelpPanel;


import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

	public class AlgorithmClusterPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		private JComboBox comboBoxAlg;
		private HelpTooltip helpTooltip;
		private JLabel numero2;
		private JTextField campoNum2;
		private static JCheckBox emitMostLikely;
		private JLabel fuzzyFactor;
		private static JTextField campoFuzzyFactor;
		private final static Logger log = Logger.getLogger(MaxIterationsPanel.class);
		
		private final static String ayuda = "[2..9999]";

		public AlgorithmClusterPanel() {
			// super();
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Algorithm of the cluster", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			setForeground(Color.BLACK);
			setLayout(null);
			setBounds(228, 11, 480, 408);

			comboBoxAlg = new JComboBox();
			comboBoxAlg.setModel(new DefaultComboBoxModel(new String[] { Constants.ClusterAlg.USER_DEFINED, Constants.ClusterAlg.CANOPY, Constants.ClusterAlg.KMEANS,
					Constants.ClusterAlg.FUZZYKMEANS, Constants.ClusterAlg.DIRICHLET }));
			comboBoxAlg.setBounds(38, 36, 141, 20);
			
			add(comboBoxAlg);
			
			emitMostLikely = new JCheckBox("Emit Most Likely");
			emitMostLikely.setBounds(256, 35, 157, 23);
			add(emitMostLikely);
			emitMostLikely.addItemListener(new ItemChangeListener());
			emitMostLikely.setEnabled(false);
			
			fuzzyFactor = new JLabel("Fuzzyfication Factor");
			fuzzyFactor.setBounds(286, 65, 157, 23);
			add(fuzzyFactor);
			
			fuzzyFactor.setEnabled(false);
			
			campoFuzzyFactor = new JTextField();
			campoFuzzyFactor.setBounds(256, 95, 157, 23);
			
			campoFuzzyFactor.setToolTipText(ayuda);
			add(campoFuzzyFactor);
			
			campoFuzzyFactor.setInputVerifier(new InputVerifier() {
				public boolean verify(JComponent input) {
					JTextField tf = (JTextField) input;
					String text = tf.getText();
					try {
						Long i = Long.parseLong(text);
						if (i > 1 && i <= 9999) {
							campoFuzzyFactor.setBackground(Color.WHITE);
							return true;
						} else {
							log.error(text + " is out of range");
							MainGUI.writeResult("Size has to be an integer number in range [2..9999]", Constants.Log.ERROR);
							campoFuzzyFactor.setBackground(new Color(240, 128, 128));
							return false;
						}
					} catch (NumberFormatException e) {
						log.error(text + " is not a number, focus not lost");
						MainGUI.writeResult("Size has to be an integer number in range [2..9999]", Constants.Log.ERROR);
						campoFuzzyFactor.setBackground(new Color(240, 128, 128));
						return false;
					}
				}
			});
			
			
			campoFuzzyFactor.setEnabled(false);
			
			final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
			btnHelp.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
				}
			});
			btnHelp.setPreferredSize(new Dimension(65, 40));
			btnHelp.setBounds(10, 358, 40, 40);
			add(btnHelp);

			// Help Tip
			helpTooltip = new HelpTooltip(btnHelp, ClusterTips.CLUSTER_ALGORITHM);
			add(helpTooltip);


			comboBoxAlg.addActionListener(new ActionListener() {
				@SuppressWarnings("deprecation")
				public void actionPerformed(ActionEvent e) {
					String alg = (String) ((JComboBox) e.getSource()).getSelectedItem();
					
					//If user choose Canopy algorithm...
					
					if (alg.equals(Constants.ClusterAlg.CANOPY)) {
					
						MainClusterPanel.setCanopy(true);
						MainClusterPanel.getTresholdClusterPanel().hazVisible(true);
						emitMostLikely.setEnabled(false);
						fuzzyFactor.setEnabled(false);
						campoFuzzyFactor.setEnabled(false);	
					} 
					//If user choose KMeans algorithm...
					else if (alg.equals(Constants.ClusterAlg.KMEANS)) {
						MainClusterPanel.setCanopy(false);
						emitMostLikely.setEnabled(false);
						fuzzyFactor.setEnabled(false);
						campoFuzzyFactor.setEnabled(false);
						MainClusterPanel.getTresholdClusterPanel().hazVisible(false);
					
					}
					else if (alg.equals(Constants.ClusterAlg.FUZZYKMEANS)) {
						MainClusterPanel.setCanopy(false);
						emitMostLikely.setEnabled(true);
						fuzzyFactor.setEnabled(true);
						campoFuzzyFactor.setEnabled(true);
					}
					else if (alg.equals(Constants.ClusterAlg.DIRICHLET)) {
						MainClusterPanel.setCanopy(false);
						emitMostLikely.setEnabled(false);
						fuzzyFactor.setEnabled(false);
						campoFuzzyFactor.setEnabled(false);
					}
					else if (alg.equals(Constants.ClusterAlg.USER_DEFINED)) {
						MainClusterPanel.setCanopy(false);
						emitMostLikely.setEnabled(false);
						fuzzyFactor.setEnabled(false);
						campoFuzzyFactor.setEnabled(false);
					}
					
				}

				
			});

		}


		public String getSelectedType() {
			return (String) comboBoxAlg.getSelectedItem();
		}
		public HelpTooltip getHelpTooltip() {
			return helpTooltip;
		}
		
		public JComboBox getComboBoxAlg() {
			
			return this.comboBoxAlg;
		}
		
		public static JCheckBox getEmitMostLikely() {
			return emitMostLikely;
		}
		
		public static JTextField getFuzzyFactor(){
			return campoFuzzyFactor;
		}
	}

