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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

import easyMahout.GUI.MainGUI;
import easyMahout.GUI.recommender.NeighborhoodRecommenderPanel;
import easyMahout.GUI.recommender.TypeRecommenderPanel;
import easyMahout.utils.Constants;
import easyMahout.utils.HelpTooltip;
import easyMahout.utils.help.ClusterTips;
		

import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

		public class ConvergenceTresholdPanel extends JPanel {

			private static final long serialVersionUID = 1L;
			
			private JLabel numero;
			
			private JTextField campoNum;
			
			private JLabel numero2;
			
			private JTextField campoNum2;
			
			private JButton boton;
			
			private double convergenceTreshold;
			
			private double convergenceTreshold2;
			
			private final static Logger log = Logger.getLogger(ConvergenceTresholdPanel.class);
			
			private HelpTooltip helpTooltip;
			
			private final static String ayuda="[0..1]";

			public ConvergenceTresholdPanel() {
				// super();
				setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Convergence Treshold", TitledBorder.CENTER,
						TitledBorder.TOP, null, null));
				setForeground(Color.BLACK);
				setLayout(null);
				setBounds(228, 11, 480, 408);
				
				numero = new JLabel("Convergence Treshold:");
				numero.setBounds(38, 55, 189, 14);
				add(numero);

				campoNum = new JTextField();
				campoNum.setBounds(38, 80, 157, 23);
				campoNum.setToolTipText(ayuda);
				add(campoNum);
				campoNum.setColumns(10);
				
				campoNum.setInputVerifier(new InputVerifier() {
					public boolean verify(JComponent input) {
						JTextField tf = (JTextField) input;
						String text = tf.getText();
						try {
							Double i = Double.parseDouble(text);
							if (i >= 0 && i <= 1) {
								campoNum.setBackground(Color.WHITE);
								return true;
							} else {
								log.error(text + " is out of range");
								MainGUI.writeResult("Size has to be a number in range [0..1]", Constants.Log.ERROR);
								campoNum.setBackground(new Color(240, 128, 128));
								return false;
							}
						} catch (NumberFormatException e) {
							log.error(text + " is not a number, focus not lost");
							MainGUI.writeResult("Size has to be a number in range [0..1]", Constants.Log.ERROR);
							campoNum.setBackground(new Color(240, 128, 128));
							return false;
						}
					}
				});
				
				//add a new treshold section
				numero2 = new JLabel("Convergence Treshold 2:");
				numero2.setBounds(38, 155, 189, 14);
				

				campoNum2 = new JTextField();
				campoNum2.setBounds(38, 181, 157, 23);
				
				campoNum2.setColumns(10);
				campoNum2.setToolTipText(ayuda);
				numero2.setVisible(false);
				campoNum2.setVisible(false);
				add(numero2);
				add(campoNum2);
				
				campoNum2.setInputVerifier(new InputVerifier() {
					public boolean verify(JComponent input) {
						JTextField tf = (JTextField) input;
						String text = tf.getText();
						try {
							Double i = Double.parseDouble(text);
							if (i >= 0 && i <= 1) {
								campoNum2.setBackground(Color.WHITE);
								return true;
							} else {
								log.error(text + " is out of range");
								MainGUI.writeResult("Size has to be a real number in range [0..1]", Constants.Log.ERROR);
								campoNum2.setBackground(new Color(240, 128, 128));
								return false;
							}
						} catch (NumberFormatException e) {
							log.error(text + " is not a number, focus not lost");
							MainGUI.writeResult("Size has to be a real number in range [0..1]", Constants.Log.ERROR);
							campoNum2.setBackground(new Color(240, 128, 128));
							return false;
						}
					}
				});
				
				if (MainClusterPanel.isCanopy()){
				
				numero2.setVisible(true);
				campoNum2.setVisible(true);
				}
				else {
				numero2.setVisible(false);
				campoNum2.setVisible(false);
				}
							
				final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
				btnHelp.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				btnHelp.setPreferredSize(new Dimension(65, 40));
				btnHelp.setBounds(10, 358, 40, 40);
				add(btnHelp);

				// Help Tip
				helpTooltip = new HelpTooltip(btnHelp, ClusterTips.CLUSTER_NITERATIONS);
				add(helpTooltip);

			}

			public static boolean isInteger(String s) {
			    try { 
			    	Double.parseDouble(s);
			        
			    } catch(NumberFormatException e) { 
			        return false; 
			    }
			    // if exception isn't thrown, then it is an integer
			    return true;
			}
			
			public HelpTooltip getHelpTooltip() {
				return helpTooltip;
			}

			public double getConvergenceTreshold() {
				return convergenceTreshold;
			}

			public void setConvergenceTreshold(double convergenceTreshold) {
				this.convergenceTreshold = convergenceTreshold;
			}
			
			public JTextField getCampoNum(){
				return campoNum;
			}
			public void setCampoNum(String s){
				campoNum.setText(s);
			}
			
			public void hazVisible(boolean b){
				numero2.setVisible(b);
				campoNum2.setVisible(b);
			}
			 
		}
