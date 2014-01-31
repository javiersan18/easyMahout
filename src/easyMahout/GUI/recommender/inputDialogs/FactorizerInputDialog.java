package easyMahout.GUI.recommender.inputDialogs;

import javax.swing.JFrame;

public abstract class FactorizerInputDialog extends JFrame {	

	private static final long serialVersionUID = 1L;
	

	private static boolean ready = false;
	public FactorizerInputDialog(String title) {		
		super(title);
	}
	
	protected abstract void onClose();
	
	protected abstract void onCloseCancel();

	public static boolean isReady() {
		return ready;
	}

	public static void setReady(boolean ready) {
		FactorizerInputDialog.ready = ready;
	}

}
