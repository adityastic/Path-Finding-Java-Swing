package com.aditya.path_visual.grid_view;

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

import javax.swing.*;

import com.aditya.path_visual.PathFinder;

public class Grid extends JPanel implements MouseListener, KeyListener, Serializable{
	private static final long serialVersionUID = 1L;

	private int width;
	private int height;

	private int rowHeight;
	private int columnWidth;

	private int rows;
	private int columns;

	private boolean isSearching;
	private boolean isStartPositionable;
	private boolean isObstacle;
	private boolean isGoalPositionable;
	private boolean shiftPressed = false;
	
	private Cell cells[][];
	private Cell startCell;
	private Cell goalCell;

	private PathFinder pathfinder;

	public Grid(int width, int height, int rows, int columns){

		this.width = width;
		this.height = height;

		this.rows = rows;
		this.columns = columns;

		rowHeight = height / rows;
		columnWidth = width / columns;

		isSearching = false;
		isStartPositionable = true;
		isGoalPositionable = false;

		buildGraph();
		addKeyListener(this);
		addMouseListener(this);

		this.setPreferredSize(new Dimension(width,height));
	}

	public void start(){
		isSearching = true;
		pathfinder.findShortestPath(startCell, goalCell, this);
	}

	public void update(){
		this.repaint();
	}

	public void reset(){
		isSearching = false;
		pathfinder = new PathFinder();
		buildGraph();
		this.repaint();
	}

	private void buildGraph(){
		pathfinder = new PathFinder();
		cells = new Cell[rows][columns];
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				cells[i][j] = new Cell(new Point(i * columnWidth,j * rowHeight),columnWidth,rowHeight);
			}
		}

		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				if(i + 1 < rows)
					cells[i][j].addEdge(new Edge(columnWidth, cells[i + 1][j]));
				if(j + 1 < columns)
					cells[i][j].addEdge(new Edge(rowHeight, cells[i][j + 1]));
				if(i - 1 >= 0)
					cells[i][j].addEdge(new Edge(columnWidth, cells[i - 1][j]));
				if(j - 1 >= 0)
					cells[i][j].addEdge(new Edge(rowHeight, cells[i][j - 1]));
				if(i + 1 < rows && j + 1 < columns)
					cells[i][j].addEdge(new Edge((int)(rowHeight * 1.4), cells[i + 1][j + 1]));
				if(i - 1 >= 0 && j - 1 >= 0)
					cells[i][j].addEdge(new Edge((int)(rowHeight * 1.4), cells[i - 1][j - 1]));
				if(i + 1 < rows && j - 1 >= 0)
					cells[i][j].addEdge(new Edge((int)(rowHeight * 1.4), cells[i + 1][j - 1]));
				if(i - 1 >= 0 && j + 1 < columns)
					cells[i][j].addEdge(new Edge((int)(rowHeight * 1.4), cells[i - 1][j + 1]));

			}
		}

		startCell = cells[0][0];
		startCell.setColor(Color.GREEN);
		goalCell = cells[rows -1][columns - 1];
		goalCell.setColor(Color.GREEN);
		update();

	}

	public void setPositionable(int cellType){
		switch(cellType){
		case 0:
			isStartPositionable = true;
			isObstacle = false;
			isGoalPositionable = false;
			break;
		case 1:
			isStartPositionable = false;
			isObstacle = false;
			isGoalPositionable = true;
			break;
		case 2:
			isStartPositionable = false;
			isObstacle = true;
			isGoalPositionable = false;
			break;

		}
	}

	public void paintComponent(Graphics g){
		g.setColor(Color.WHITE);
		g.drawRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
		g.setColor(Color.WHITE);

		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				cells[i][j].draw(g);
			}
		}

	}

	public void mouseClicked(MouseEvent e){
		if(!isSearching){
			Point mousePos = new Point(e.getX(),e.getY());
			if(isStartPositionable){
				if(startCell != null)
					startCell.setColor(Color.BLACK);
				startCell = cells[(int)(mousePos.x/columnWidth)][(int)(mousePos.y/rowHeight)];
				startCell.setColor(Color.GREEN);
			}

			if(isGoalPositionable){
				if(goalCell != null)
					goalCell.setColor(Color.BLACK);
				goalCell = cells[(int)(mousePos.x/columnWidth)][(int)(mousePos.y/rowHeight)];
				goalCell.setColor(Color.GREEN);
			}
			
			if(isObstacle || shiftPressed) {
				Cell cell = cells[(int)(mousePos.x/columnWidth)][(int)(mousePos.y/rowHeight)];
				cell.setColor(Color.RED);
				cell.setObstacle(true);
			}
			
			update();
		}
	}

	public void mouseEntered(MouseEvent e){}

	public void mouseExited(MouseEvent e){}

	public void mousePressed(MouseEvent e){}

	public void mouseReleased(MouseEvent e){}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			shiftPressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
			shiftPressed = false;
		}
	}
}
