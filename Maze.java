/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Maze;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Stack;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

class Maze extends JPanel {
	private Point entrance = null;
	private Point exit = null;
	private int rowNumber;
	private int colNumber;
	private int LatticeWidth;
	private Ball ball;
	private Lattice[][] mazeLattice;
	private boolean startTiming = false;
	private JPanel panel = new JPanel();
	private JTextField timeText = new Timers(), stepNumberText = new JTextField("0");
	private boolean computerDo = false;
	private Thread thread = null;
	//private Thread audioThread = null;
	private int stepNmber;
	private static final char DepthFirstSearchSolveMaze = 0;
	private static final char BreadthFirstSearchSolveMaze = 1;
	private char solveMaze = DepthFirstSearchSolveMaze;
	private static final char DepthFirstSearchCreateMaze = 0;
	//private static final char RandomizedPrimCreateMaze = 1;
	//private static final char RecursiveDivisionCreateMaze = 2;
	private char createMaze = DepthFirstSearchCreateMaze;
	private boolean promptSolveMaze = false;

	public Maze(int row, int col) {
		this.setRowNumber(row);
		this.setColNumber(col);
		this.LatticeWidth = 15;
		mazeLattice = new Lattice[getRowNumber() + 2][getColNumber() + 2];
		setLayout(new BorderLayout(0, 0));
		getTimeText().setForeground(Color.BLUE);
		getTimeText().setFont(new Font("Vardana", Font.PLAIN, 14));
		getTimeText().setHorizontalAlignment(JTextField.CENTER);
		stepNumberText.setEnabled(false);
		getStepNumberText().setForeground(Color.BLUE);
		getStepNumberText().setFont(new Font("Vardana", Font.PLAIN, 14));
		getStepNumberText().setHorizontalAlignment(JTextField.CENTER);
		Label timeLabel = new Label("Time:"), stepLabel = new Label("StepNumber:");
		timeLabel.setAlignment(Label.RIGHT);
		stepLabel.setAlignment(Label.RIGHT);
		panel.setLayout(new GridLayout(1, 4));
		add(panel, BorderLayout.NORTH);
		panel.add(timeLabel);
		panel.add(getTimeText());
		panel.add(stepLabel);
		panel.add(getStepNumberText());
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!isComputerDo()) {
					requestFocus();
				}
			}
		});
		setKeyListener();
		createMaze();
	}

	public void init() {
		mazeLattice = new Lattice[getRowNumber() + 2][getColNumber() + 2];
		setPromptSolveMaze(false);
		setComputerDo(false);
		setThreadStop();
		resetStepNumber();
		resetTimer();
		for (int i = 1; i < getRowNumber() + 1; ++i)
			for (int j = 1; j < getColNumber() + 1; ++j) {
				mazeLattice[i][j] = new Lattice();
			}
		for (int i = 0; i < getRowNumber() + 2; ++i) {
			mazeLattice[i][0] = new Lattice();
			mazeLattice[i][getColNumber() + 1] = new Lattice();
		}
		for (int j = 0; j < getColNumber() + 2; ++j) {
			mazeLattice[0][j] = new Lattice();
			mazeLattice[getRowNumber() + 1][j] = new Lattice();
		}
		ball = new Ball(0, 1);
		setEntrance(new Point(0, 1));
		setExit(new Point(getColNumber() + 1, getRowNumber()));
		mazeLattice[getEntrance().y][getEntrance().x].setPassable(true);
		mazeLattice[getExit().y][getExit().x].setPassable(true);
	}

	public boolean isWin() {
		if (getExit().x == ball.getX() && getExit().y == ball.getY()) {
			return true;
		}
		return false;
	}

	private void GameOverMessage() {
		((Timers) getTimeText()).stop();
		JOptionPane.showMessageDialog(null,
				"Congratulations on getting out of the maze!\n" + "Time you have used to go out of the maze is: "
						+ timeText.getText() + "\nThe number of the steps you have used to go out of the maze is: "
						+ stepNmber,
				"GameOver", JOptionPane.INFORMATION_MESSAGE);
	}

	private boolean isOutofBorder(int x, int y) {
		if ((x == 0 && y == 1) || (x == getColNumber() + 1 && y == getRowNumber()))
			return false;
		else
			return (x > getColNumber() || y > getRowNumber() || x < 1 || y < 1) ? true : false;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (int i = 0; i < getRowNumber() + 2; ++i)
			for (int j = 0; j < getColNumber() + 2; ++j) {
				g.drawRect((j + 1) * LatticeWidth, (i + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
				if (mazeLattice[i][j].isPassable())
					g.setColor(Color.WHITE);
				else
					g.setColor(Color.BLACK);
				g.fillRect((j + 1) * LatticeWidth, (i + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
			}
		g.setColor(Color.RED);
		g.fillRect((getColNumber() + 2) * LatticeWidth, (getRowNumber() + 1) * LatticeWidth + 30, LatticeWidth,
				LatticeWidth);
		g.setColor(ball.getColor());
		g.drawOval((ball.getX() + 1) * LatticeWidth, (ball.getY() + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
		g.fillOval((ball.getX() + 1) * LatticeWidth, (ball.getY() + 1) * LatticeWidth + 30, LatticeWidth, LatticeWidth);
		if (isPromptSolveMaze()) {
			Stack<Point> pathStack = promptsolveMaze();
			g.setColor(Color.GREEN);
			Point start = pathStack.pop();
			while (!pathStack.isEmpty()) {
				Point end = pathStack.pop();
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(3.0f));
				g2.drawLine((int) (start.getX() + 1) * LatticeWidth + LatticeWidth / 2,
						(int) (start.getY() + 1) * LatticeWidth + 30 + LatticeWidth / 2,
						(int) (end.getX() + 1) * LatticeWidth + LatticeWidth / 2,
						(int) (end.getY() + 1) * LatticeWidth + 30 + LatticeWidth / 2);
				start = end;
			}
		}
	}

	synchronized private void move(int c) {
		int tx = ball.getX(), ty = ball.getY();
		switch (c) {
		case KeyEvent.VK_LEFT:
			--tx;
			break;
		case KeyEvent.VK_RIGHT:
			++tx;
			break;
		case KeyEvent.VK_UP:
			--ty;
			break;
		case KeyEvent.VK_DOWN:
			++ty;
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
			break;
		default:
			tx = 0;
			ty = 0;
			break;
		}
		if (!isOutofBorder(tx, ty) && mazeLattice[ty][tx].isPassable()) {
			ball.setX(tx);
			ball.setY(ty);
			++stepNmber;
			stepNumberText.setText(Integer.toString(stepNmber));
			if (!isStartTiming()) {
				setStartTiming(!isStartTiming());
				((Timers) getTimeText()).start();
			}
		}

	}

	private void setKeyListener() {
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (!isWin()) {
					int c = e.getKeyCode();
					move(c);
					repaint();
					if (isWin() && !isComputerDo())
						GameOverMessage();
				}
			}
		});
	}

	public void resetTimer() {
		setStartTiming(false);
		getTimeText().setText("00:00:00");
		((Timers) timeText).restart();
	}

	public void resetStepNumber() {
		setStepNmber(0);
		stepNumberText.setText(Integer.toString(stepNmber));
	}

	public void setBallPosition(Point p) {
		ball.setX(p.x);
		ball.setY(p.y);
		repaint();
	}

	public void createMaze() {
		init();
		AbstractCreateMaze c = null;
		if (getCreateMaze() == DepthFirstSearchCreateMaze)
			c = new DepthFirstSearchCreateMaze();
		c.createMaze(mazeLattice, getColNumber(), getRowNumber());
		repaint();
	}

	private Stack<Point> solveMaze(Point p) {
		AbstractSolveMaze a = null;
		if (getSolveMaze() == BreadthFirstSearchSolveMaze)
			a = new BreadthFirstSearchSolveMaze();
		else if (getSolveMaze() == DepthFirstSearchSolveMaze)
			a = new DepthFirstSearchSolveMaze();
		return a.solveMaze(mazeLattice, p, getExit(), getColNumber(), getRowNumber());
	}

	private Stack<Point> promptsolveMaze() {
		AbstractSolveMaze a = null;
		if (getSolveMaze() == BreadthFirstSearchSolveMaze)
			a = new BreadthFirstSearchSolveMaze();
		else if (getSolveMaze() == DepthFirstSearchSolveMaze)
			a = new DepthFirstSearchSolveMaze();
		return a.solveMaze(mazeLattice, new Point(ball.getX(), ball.getY()), getExit(), getColNumber(), getRowNumber());
	}
	
	private void computerSolveMazeForBallPositionForTime(int time) {
		if (getThread() == null)
			setThread(new Thread() {
				@Override
				public void run() {
					while (!isInterrupted())
						try {
							setPromptSolveMaze(true);
							repaint();
							Thread.sleep(time);
							setPromptSolveMaze(false);
							repaint();
							setThreadStop();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							break;
						}
				}
			});
		getThread().start();
	}

	public boolean computerSolveMazeForBallPosition() {
		setThreadStop();
		((Timers) getTimeText()).stop();
		int time = 0;
		Object[] selections = { "forever", "10s", "5s", "3s", "1s" };
		Object select = JOptionPane.showInputDialog(null, "Please select the speed of which the ball runs",
				"Maze", JOptionPane.INFORMATION_MESSAGE, null, selections, selections[2]);
		if (select != null) {
			switch ((String) select) {
			case "forever":
				time = 2000000000;
				break;
			case "10s":
				time = 10000;
				break;
			case "5s":
				time = 5000;
				break;
			case "3s":
				time = 3000;
				break;
			case "1s":
				time = 1000;
				break;
			default:
				break;
			}
			computerSolveMazeForBallPositionForTime(time);
			((Timers) getTimeText()).proceed();
			return true;
		} else
			return false;
	}
	
	private void computerSolveMazeForSpeed(int speed) {
		setComputerDo(true);
		Point p = null;
		if (isWin())
			p = getEntrance();
		else
			p = new Point(ball.getX(), ball.getY());
		Stack<Point> stack = solveMaze(p);
		resetTimer();
		resetStepNumber();
		if (getThread() == null)
			setThread(new Thread() {
				@Override
				public void run() {
					while (!isInterrupted())
						try {
							while (!stack.isEmpty()) {
								Point p = stack.pop();
								setBallPosition(p);
								++stepNmber;
								stepNumberText.setText(Integer.toString(stepNmber));
								Thread.sleep(speed);
							}
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							break;
						}
				}
			});
		getThread().start();
	}

	public boolean computerSolveMaze() {
		int speed = 0;
		setThreadStop();            //Stops the thread player
		Object[] selections = { "lower seed", "low speed", "medium speed", "high speed", "higher speed" };
		Object select = JOptionPane.showInputDialog(null, "Please select the speed of which the ball runs",
				"Maze", JOptionPane.INFORMATION_MESSAGE, null, selections, selections[2]);
		if (select != null) {
			switch ((String) select) {
			case "lower seed":
				speed = 400;
				break;
			case "low speed":
				speed = 300;
				break;
			case "medium speed":
				speed = 200;
				break;
			case "high speed":
				speed = 100;
				break;
			case "higher speed":
				speed = 20;
				break;
			default:
				break;
			}
			computerSolveMazeForSpeed(speed);
			return true;
		} else
			return false;
	}

	public int getLatticeWidth() {
		return LatticeWidth;
	}

	public void setLatticeWidth(int latticeWidth) {
		LatticeWidth = latticeWidth;
	}

	public JTextField getTimeText() {
		return timeText;
	}

	/**
	 * @return the startTiming
	 */
	public boolean isStartTiming() {
		return startTiming;
	}

	/**
	 * @param startTiming the startTiming to set
	 */
	public void setStartTiming(boolean startTiming) {
		this.startTiming = startTiming;
	}

	/**
	 * @return the entrance
	 */
	public Point getEntrance() {
		return entrance;
	}

	/**
	 * @param entrance the entrance to set
	 */
	public void setEntrance(Point entrance) {
		this.entrance = entrance;
	}

	/**
	 * @return the exit
	 */
	public Point getExit() {
		return exit;
	}

	/**
	 * @param exit the exit to set
	 */
	private void setExit(Point exit) {
		this.exit = exit;
	}

	/**
	 * @return the computerDo
	 */
	public boolean isComputerDo() {
		return computerDo;
	}

	/**
	 * @param computerDo the computerDo to set
	 */
	public void setComputerDo(boolean computerDo) {
		this.computerDo = computerDo;
	}

	/**
	 * @return the thread
	 */
	public Thread getThread() {
		return thread;
	}

	/**
	 * @param thread the thread to set
	 */
	private void setThread(Thread thread) {
		this.thread = thread;
	}

	public void setThreadStop() {
		if (getThread() != null) {
			if (isPromptSolveMaze())
				setPromptSolveMaze(false);
			thread.interrupt();
			setThread(null);
		}
	}

	/**
	 * @return the stepNumberText
	 */
	public JTextField getStepNumberText() {
		return stepNumberText;
	}

	/**
	 * @return the stepNmber
	 */
	public int getStepNmber() {
		return stepNmber;
	}

	/**
	 * @param stepNmber the stepNmber to set
	 */
	public void setStepNmber(int stepNmber) {
		this.stepNmber = stepNmber;
	}

	/**
	 * @return the rowNumber
	 */
	public int getRowNumber() {
		return rowNumber;
	}

	/**
	 * @param rowNumber the rowNumber to set
	 */
	public void setRowNumber(int rowNumber) {
		this.rowNumber = rowNumber;
	}

	/**
	 * @return the colNumber
	 */
	public int getColNumber() {
		return colNumber;
	}

	/**
	 * @param colNumber the colNumber to set
	 */
	public void setColNumber(int colNumber) {
		this.colNumber = colNumber;
	}

	/**
	 * @return the solveMaze
	 */
	public char getSolveMaze() {
		return solveMaze;
	}

	/**
	 * @param solveMaze the solveMaze to set
	 */
	public void setSolveMaze(char solveMaze) {
		this.solveMaze = solveMaze;
	}

	/**
	 * @return the createMaze
	 */
	public char getCreateMaze() {
		return createMaze;
	}

	/**
	 * @param createMaze the createMaze to set
	 */
	public void setCreateMaze(char createMaze) {
		this.createMaze = createMaze;
	}

	/**
	 * @return the promptSolveMaze
	 */
	public boolean isPromptSolveMaze() {
		return promptSolveMaze;
	}

	/**
	 * @param promptSolveMaze the promptSolveMaze to set
	 */
	public void setPromptSolveMaze(boolean promptSolveMaze) {
		this.promptSolveMaze = promptSolveMaze;
	}
}
