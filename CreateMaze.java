/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maze;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

abstract class AbstractCreateMaze {                     //Creates maze using DFS approach
	protected boolean isOutofBorder(int x, int y, int colNumber, int rowNumber) {
		if ((x == 0 && y == 1) || (x == colNumber + 1 && y == rowNumber))
			return false;
		else
			return (x > colNumber || y > rowNumber || x < 1 || y < 1) ? true : false;
	}

	abstract void createMaze(Lattice[][] mazeLattice, int colNumber, int rowNumber);
}

class DepthFirstSearchCreateMaze extends AbstractCreateMaze {           //DFS class

	protected Point ArroundPoint(Lattice[][] mazeLattice, Point p, Stack<Point> s, Random rand, int colNumber,
			int rowNumber) {
		final int[] arroundPoint = { -2, 0, 2, 0, -2 };                
                //final int[] arroundPoint = { -1, 0, 1, 0, -1 };                
		int r = rand.nextInt(4);
		for (int i = 0; i < 4; ++i) {
			int j = r % 4;
			int x = p.x + arroundPoint[j];
			int y = p.y + arroundPoint[j + 1];
			++r;
			if (!isOutofBorder(x, y, colNumber, rowNumber) && !mazeLattice[y][x].isPassable()) {
				mazeLattice[y][x].setPassable(true);
				mazeLattice[p.y + arroundPoint[j + 1] / 2][p.x + arroundPoint[j] / 2].setPassable(true);
				return new Point(x, y);
			}
		}
		return null;               
	}

	@Override
	public void createMaze(Lattice[][] mazeLattice, int colNumber, int rowNumber) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		Point currentPoint = new Point(2 * rand.nextInt(colNumber / 2) + 1, 2 * rand.nextInt(rowNumber / 2) + 1);
		mazeLattice[currentPoint.y][currentPoint.x].setPassable(true);
		Stack<Point> pathStack = new Stack<Point>();
		pathStack.push(currentPoint);
		currentPoint = ArroundPoint(mazeLattice, currentPoint, pathStack, rand, colNumber, rowNumber);
		while (true) {
			Point p = ArroundPoint(mazeLattice, currentPoint, pathStack, rand, colNumber, rowNumber);
			if (p != null) {
				pathStack.push(currentPoint);
				currentPoint = p;
			} else if (!pathStack.isEmpty())
				currentPoint = pathStack.pop();
			else
				break;
		}
	}
}
