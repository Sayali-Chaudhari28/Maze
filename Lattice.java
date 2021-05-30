/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maze;

import java.awt.Point;

class Lattice {
	private boolean Passable;
	private Point Father;
	public Lattice() {
		setPassable(false);
		setFather(null);
	}

	public boolean isPassable() {
		return Passable;
	}

	public void setPassable(boolean isPassable) {
		this.Passable = isPassable;
	}

	/**
	 * @return the father
	 */
	public Point getFather() {
		return Father;
	}

	/**
	 * @param father the father to set
	 */
	public void setFather(Point father) {
		Father = father;
	}
}