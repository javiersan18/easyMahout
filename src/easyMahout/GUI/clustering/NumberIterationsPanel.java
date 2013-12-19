package easyMahout.GUI.clustering;


	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Dimension;
	import java.awt.Graphics;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;

	import javax.swing.DefaultComboBoxModel;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JComboBox;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;
	import javax.swing.JTextArea;
	import javax.swing.JTextField;
	import javax.swing.border.LineBorder;

	import easyMahout.utils.Constants;
	

	import javax.swing.border.TitledBorder;

	public class NumberIterationsPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private JLabel numero;
		
		private JTextField campoNum;
		
		private JButton boton;
		
		private long numeroIteraciones;

		public NumberIterationsPanel() {
			// super();
			setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Number of iterations", TitledBorder.CENTER,
					TitledBorder.TOP, null, null));
			setForeground(Color.BLACK);
			setLayout(null);
			setBounds(228, 11, 480, 408);
			
			numero = new JLabel("Number of iterations:");
			numero.setBounds(38, 105, 189, 14);
			add(numero);

			campoNum = new JTextField();
			campoNum.setBounds(38, 131, 401, 20);
			add(campoNum);
			campoNum.setColumns(10);
			
			boton = new JButton("OK");
			boton.setBounds(200, 190, 70, 30);
			add(boton);
			
//			JPanel panel = new IconHelpPanel(25,25);
//			panel.setBounds(280, 193, 25, 25);
//			panel.setToolTipText("Number of iterations you wish the algortihm to have"); 
//			add(panel);

			boton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String num = campoNum.getText();
					if (!isInteger(num))
						JOptionPane.showMessageDialog(null, "That is not a number!");
					else {
						numeroIteraciones= Long.parseLong(num);
					}
				}
			});

		}

		public static boolean isInteger(String s) {
		    try { 
		    	Long.parseLong(s);
		        //Integer.parseInt(s); 
		    } catch(NumberFormatException e) { 
		        return false; 
		    }
		    // if exception isn't thrown, then it is an integer
		    return true;
		}
		 
	}




