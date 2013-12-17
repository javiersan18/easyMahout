package easyMahout.GUI.recommender;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import easyMahout.utils.Constants;
//import easyMahout.utils.IconHelpPanel;



import javax.swing.border.TitledBorder;
import javax.swing.JButton;

import net.java.balloontip.BalloonTip;
import net.java.balloontip.examples.complete.Utils;
import net.java.balloontip.utils.FadingUtils;

public class TypeRecommenderPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox comboBoxType;

	public TypeRecommenderPanel() {
		setToolTipText("");
		// super();
		setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Type of recommender", TitledBorder.CENTER,
				TitledBorder.TOP, null, null));
		setForeground(Color.BLACK);
		setLayout(null);
		// setLayout(new GridBagLayout());
		int gridY = 0;
		setBounds(228, 11, 480, 408);

		comboBoxType = new JComboBox();
		comboBoxType.setModel(new DefaultComboBoxModel(new String[] { Constants.RecommType.USERBASED, Constants.RecommType.ITEMBASED }));
		comboBoxType.setBounds(38, 36, 141, 20);
		add(comboBoxType);
		
//		// Balloon tip 2
//				JLabel label2 = new JLabel("A balloon tip with tabs");
//				add(label2, new GridBagConstraints(1,gridY,1,1,1.0,1.0, GridBagConstraints.SOUTH, GridBagConstraints.NONE, new Insets(0,0,80,0), 0, 0));
//				++gridY;
//				
//				JTabbedPane contents2 = new JTabbedPane();
//				JPanel tab1 = new JPanel();
//				tab1.add(new JCheckBox());
//				tab1.add(new JLabel("Tabs? But of course!"));
//				JPanel tab2 = new JPanel();
//				tab2.add(new JLabel("Because we can!"));
//				contents2.addTab("FirstTab", tab1);
//				contents2.addTab("SecondTab", tab2);
//				new BalloonTip(label2, contents2,
//						Utils.createBalloonTipStyle(),
//						Utils.createBalloonTipPositioner(), 
//						null);

		final JButton btnHelp = new JButton(new ImageIcon(TypeRecommenderPanel.class.getResource("/easyMahout/GUI/images/helpIcon64.png")));
		btnHelp.setBounds(189, 27, 40, 40);
		add(btnHelp);

		// Fading balloon tip
//		final BalloonTip fadingBalloonTip = new BalloonTip(btnHelp, contents2, Utils.createBalloonTipStyle(),
//				Utils.createBalloonTipPositioner(), null);
//		fadingBalloonTip.setOpacity(0.0f);
//
//		btnHelp.addActionListener(new ActionListener() {
//			private boolean isShown = false;
//
//			private ActionListener onStop = new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					isShown = !isShown;
//					btnHelp.setEnabled(true);
//				}
//			};
//
//			public void actionPerformed(ActionEvent e) {
//				btnHelp.setEnabled(false);
//				if (isShown) {
//					FadingUtils.fadeOutBalloon(fadingBalloonTip, onStop, 500, 24);
//				} else {
//					FadingUtils.fadeInBalloon(fadingBalloonTip, onStop, 500, 24);
//				}
//			}
//
//		});

		comboBoxType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String type = (String) ((JComboBox) e.getSource()).getSelectedItem();
				RecommenderJPanel.getSimilarityPanel().setModelSimilarity(type);
				if (type.equals(Constants.RecommType.ITEMBASED)) {
					RecommenderJPanel.setEnableNeighborhood(false);
				} else if (type.equals(Constants.RecommType.USERBASED)) {
					RecommenderJPanel.setEnableNeighborhood(true);
				}
			}
		});

	}

	public String getSelectedType() {
		return (String) comboBoxType.getSelectedItem();
	}
}
