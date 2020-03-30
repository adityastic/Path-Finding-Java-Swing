package com.aditya.path_visual.grid_view;

import java.awt.*;
import java.io.Serializable;

public class Cell extends Vertex implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private int width;
	private int height;
	private Color color;
	private boolean isObstacle = false;

	public Cell(Point position, int width, int height){
		super(position);
		this.width = width;
		this.height = height;
		this.color = Color.BLACK;
	}

	public void setColor(Color color){
		this.color = color;
	}

	public Color getColor(){
		return this.color;
	}

	public boolean isObstacle() {
		return isObstacle;
	}

	public void setObstacle(boolean isObstacle) {
		this.isObstacle = isObstacle;
	}

	public void draw(Graphics g){
		g.setColor(color);
		g.fillRect(position.x, position.y, width, height);
		g.setColor(Color.WHITE);
		g.drawRect(position.x, position.y, width, height);

	}
}
