package com.xk.chinesechess.zujian;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Qizi extends Image {
	private int value=0;
	private String key;
	public Qizi(TextureRegion bgt,int value) {
		super(bgt);
		this.value=value;
		this.setSize(54, 54);
	}


	public int getValue() {
		return value;
	}

	public void select(){
	}
	


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public void setV(int i) {
		this.value=i;
		
	}

	public float getCoordinateX() {
		return (this.getX()-45)/72;
	}

	public float getCoordinateY() {
		return (this.getY()-340)/72;
	}

	public int getCoordinate(){
		return (int)getCoordinateX()*10+(int)getCoordinateY();
	}
}
