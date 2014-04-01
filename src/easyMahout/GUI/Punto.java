package easyMahout.GUI;

//Import packages
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Punto{ //Main class
	//Declare GUI components
	static JFrame frmMain;
	static Container pane;
	static JButton btnDo;
	static JProgressBar barDo;

	public static void main (String[] args){ //Main void
		//Set Look and Feel
		try {UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}
		catch (Exception e) {}

		//Create all components
		frmMain = new JFrame("Sample progress bar application");
		frmMain.setSize(300, 100); //Window size 300x100 pixels
		pane = frmMain.getContentPane();
		pane.setLayout(null); //Use the null layout
		frmMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit when X is clicked
		btnDo = new JButton("Go!");
		barDo = new JProgressBar(0, 100); //Min value: 0 Max value: 100	

		//Add components to pane
		pane.add(btnDo);
		pane.add(barDo);

		//Position controls (X, Y, width, height)
		barDo.setBounds(10, 10, 280, 20);
		btnDo.setBounds(100, 35, 100, 25);

		//Make frame visible
		frmMain.setResizable(false); //No resize
		frmMain.setVisible(true);

		//Add action listeners
		btnDo.addActionListener(new btnDoAction()); //Add the button's action
	}

	//The action
	public static class btnDoAction implements ActionListener{
		public void actionPerformed (ActionEvent e){
			new Thread(new thread1()).start(); //Start the thread
		}
	}

	//The thread
	public static class thread1 implements Runnable{
		public void run(){
			for (int i=0; i<=100; i++){ //Progressively increment variable i
				barDo.setValue(i); //Set value
				barDo.repaint(); //Refresh graphics
				try{Thread.sleep(50);} //Sleep 50 milliseconds
				catch (InterruptedException err){}
			}
		}
	}
}