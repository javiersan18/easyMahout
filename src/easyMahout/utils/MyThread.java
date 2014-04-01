package easyMahout.utils;

import easyMahout.GUI.MainGUI;

public class MyThread extends Thread{
	
	public void run(){
		MainGUI.writeResult("Please wait until we display the results.", Constants.Log.INFO);
		while (true){
			try {
				sleep(1000);
				MainGUI.writeResult(".", Constants.Log.INFO);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
