package easyMahout.GUI;


import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.clustering.Cluster;
import org.apache.mahout.math.AbstractVector;
import org.apache.mahout.math.DenseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.VectorWritable;

import com.google.common.collect.Lists;
public class Display extends Canvas{ 
	private static final int SIZE = 7;
	protected static final int DS = 72; // default scale = 72 pixels per inch
	private static final List<List<Cluster>> CLUSTERS =  Lists.newArrayList();
	static final Color[] COLORES = { Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.magenta,Color.lightGray };

	public Display(){ 
		setSize(200, 200); 
		setBackground(Color.white);
		} 
	public static void main( String [] args){
		//GraphicsProgram class is now a type of canvas 
		//since it extends the Canvas class 
		//lets instantiate it 
		Display GP = new Display();
		int res = Toolkit.getDefaultToolkit().getScreenResolution();
		//create a new frame to which we will add a canvas 
		Frame aFrame = new Frame();
		aFrame.setSize(res*SIZE, res*SIZE); 
		//add the canvas
		aFrame.add(GP); 
		aFrame.setVisible(true); 
		
		} 
	public void paint(Graphics g){
		/*g.setColor(Color.blue); 
		g.drawLine(30, 30, 80, 80);
		g.drawRect(20, 150, 100, 100);
		g.fillRect(20, 150, 100, 100);
		g.fillOval(150, 20, 100, 100); */
		
		 // plot the axes
	    g.setColor(Color.BLACK);
	    Vector dv = new DenseVector(2).assign(SIZE / 2.0);
	    plotRectangle(g, new DenseVector(2).assign(2), dv);
	    plotRectangle(g, new DenseVector(2).assign(-2), dv);
	    
	    // plot the sample data
	    g.setColor(Color.DARK_GRAY);
	    dv.assign(0.03);
	   
	   /* for (VectorWritable v : SAMPLE_DATA) {
	      plotRectangle(g2, v.get(), dv);
	    }*/
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
