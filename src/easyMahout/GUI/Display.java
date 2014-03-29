package easyMahout.GUI;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.math.AbstractVector;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import com.google.common.collect.Lists;
import com.sun.corba.se.impl.orbutil.graph.Graph;

import easyMahout.GUI.clustering.builder.ClusterBuilder;
public class Display extends Canvas{ 
	private static final int SIZE = 7;
	protected static final int DS = 72; // default scale = 72 pixels per inch
	private static final List<List<Cluster>> CLUSTERS =  Lists.newArrayList();
	static final Color[] COLORES = { Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.magenta,Color.lightGray };

	public Display(){ 
		setSize(800, 800); 
		setBackground(Color.white);
		} 
	public static void main( ArrayList<Punto> puntos){
		Display GP = new Display();
		int res = Toolkit.getDefaultToolkit().getScreenResolution();
		Frame aFrame = new Frame();
		
		aFrame.setSize(res*SIZE, res*SIZE); 
		//add the canvas
		aFrame.add(GP); 
		
		aFrame.setVisible(true); 
		
		} 
	
	public  void paint(Graphics g){
		ArrayList<Punto> puntos = ClusterBuilder.getPuntos();
		Graphics2D g2=(Graphics2D) g;
		int cx=30;
		for(int i=0;i<puntos.size();i++){
			
			Punto p=puntos.get(i);
			int x=(Integer.parseInt(p.getX().toString()));
			int y=((int)p.getY())/1000;
			g.setColor(COLORES[Math.min(COLORES.length - 1, cx)]);
	        ((Graphics2D) g).setStroke(new BasicStroke(cx == 0 ? 3 : 1));
			
			g.drawRect(x,y,10,10);
			cx--;
		}
		g.setColor(Color.blue); 
		
		
		
		
		} 
	
	 protected static void plotRectangle(Graphics g, Vector v, Vector dv) {
		    double[] flip = {1, -1};
		    Vector v2 = v.times(new DenseVector(flip));
		    v2 = v2.minus(dv.divide(2));
		    int h = SIZE / 2;
		    double x = v2.get(0) + h;
		    double y = v2.get(1) + h;
		    ((Graphics2D) g).draw(new Rectangle2D.Double(x * DS, y * DS, dv.get(0) * DS, dv.get(1) * DS));
		  }
	 protected static void plotClusters(Graphics2D g2) {
		    int cx = CLUSTERS.size() - 1;
		    for (List<Cluster> clusters : CLUSTERS) {
		      g2.setStroke(new BasicStroke(cx == 0 ? 3 : 1));
		      g2.setColor(COLORES[Math.min(COLORES.length - 1, cx--)]);
		      for (Cluster cluster : clusters) {
		        plotEllipse(g2, cluster.getCenter(), cluster.getRadius().times(3));
		      }
		    }
		  }
	 protected static void plotEllipse(Graphics2D g2, Vector v, Vector dv) {
		    double[] flip = {1, -1};
		    Vector v2 = v.times(new DenseVector(flip));
		    v2 = v2.minus(dv.divide(2));
		    int h = SIZE / 2;
		    double x = v2.get(0) + h;
		    double y = v2.get(1) + h;
		    g2.draw(new Ellipse2D.Double(x * DS, y * DS, dv.get(0) * DS, dv.get(1) * DS));
		  }
}
