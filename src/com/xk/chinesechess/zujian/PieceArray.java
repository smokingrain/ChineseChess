package com.xk.chinesechess.zujian;

import java.util.ArrayList;

public class PieceArray {
	private ArrayList<Qizi> list;
	public PieceArray(){
		list = new ArrayList<Qizi>();
	}
	public Qizi getPiece(int index){
		if (index>=0 && index<list.size()) 
			return (list.get(index));
		else return null;
	}
	public void add(Qizi qz){
		list.add(qz);
	}
	public Qizi remove(int index){
		if (index>=0 && index<list.size())
			return list.remove(index);
		else 
			return null;
	}
	public boolean remove(Qizi qz){
		return list.remove(qz);
	}
	public int size(){
		return list.size();
	}
}
