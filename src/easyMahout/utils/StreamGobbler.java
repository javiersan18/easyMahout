package easyMahout.utils;

/**
 * StramGobbler.java
 *
 * Para capturar la salida de la ejecucion del comando.
 *
 * @modified clase original de javaworld modificada por jdelgado
 * @version 0.0.0.2 - 13 Marzo 2007
 * @sinde JDK 1.5 - Eclipse 3.2
 */

import java.io.*;

public class StreamGobbler extends Thread {
	// Flujo de entrada
	InputStream is;
	// Tipo de Flujo: generalmente output o error
	String type;
	// Flujo de salida
	OutputStream os;
	// Variable para mostrar por la salida estandard
	boolean debug = false;
	// para guardar la salida generada
	String output = "";

	// ----
	/**
	 * Constructor
	 * 
	 * @param is
	 *            InputStream
	 * @param type
	 *            tipo de flujo (OUTPUT o ERROR)
	 * @param debug
	 *            indica si se debe mostrar o no la salida por la salida
	 *            estandard
	 */
	StreamGobbler(InputStream is, String type, boolean debug) {
		this(is, type, null, debug);
	}

	// ----
	/**
	 * Constructor
	 * 
	 * @param is
	 *            InputStram
	 * @param type
	 *            tipo de flujo (OUTPUT o ERROR)
	 * @param redirect
	 *            OutputStream donde redireccionar la salida
	 * @param debug
	 *            indica si se debe mostrar o no la salida por la salida
	 *            estandard
	 */
	StreamGobbler(InputStream is, String type, OutputStream redirect,
			boolean debug) {
		this.is = is;
		this.type = type;
		this.os = redirect;
		this.debug = debug;
	}

	// ----
	/**
	 * Ejecutar el hilo
	 */
	public void run() {
		try {
			PrintWriter pw = null;
			if (os != null)
				pw = new PrintWriter(os);
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				// si hay fichero lo imprime a fichero
				if (pw != null)
					pw.println(line);
				if (debug)
					System.out.println(type + "> " + line);
				output = output + line + "\r\n";
			}
			if (pw != null)
				pw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	// ------
	/**
	 * Recuperar el flujo de datos generado
	 * 
	 * @return un String con el output
	 */
	public String getOutput() {
		return output;
	}
	// ------
}
// end of class StreamGobbler.java