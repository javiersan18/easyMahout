package easyMahout.GUI;

import org.apache.hadoop.io.IntWritable;

public  class Punto {

	private  IntWritable x;
	private  float y;
	
	public Punto(IntWritable x, float y){
		this.x=x;
		this.y=y;
	}
	public  IntWritable getX() {
		return x;
	}
	public  void setX(IntWritable x) {
		x = x;
	}
	public  float getY() {
		return y;
	}
	public void setY(float y) {
		y = y;
	}
	
}
