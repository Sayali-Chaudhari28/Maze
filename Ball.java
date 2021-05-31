/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maze;

import java.awt.Color;

class Ball {                                    //Creates ball 
	private int x;
	private int y;
	private Color color;
	public Ball(int x,int y) {
		setX(x);
		setY(y);
		setColor(Color.GREEN);
	}

	public int getX() {                     //Returns x coordinate
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {                 //Returns y coordinate
		return y;
	}

	public int setY(int y) {
		this.y = y;
		return y;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
