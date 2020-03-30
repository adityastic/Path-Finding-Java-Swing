package com.aditya.path_visual;

import java.awt.Color;
import java.awt.Point;
import java.util.*;

import com.aditya.path_visual.grid_view.*;

public class PathFinder {

	private HashSet<Cell>closedList;
	private PriorityQueue<Cell>openList;
	
	public PathFinder(){
		openList = new PriorityQueue<Cell>();
		closedList = new LinkedHashSet<Cell>();
	}

	public LinkedList<Cell> findShortestPath(Cell start, Cell goal, Grid grid){

		start.setDistanceFromStart(0);

		start.setCost(euclideanDistance(start.position, goal.position));

		openList.add(start);

		while(!openList.isEmpty()){

			grid.update();

			Cell current = openList.poll();
			current.setColor(Color.DARK_GRAY);

			closedList.add(current);

			if(current == start)
				start.setColor(Color.GREEN);

			if(current == goal){
				goal.setColor(Color.GREEN);
				grid.update();
				break;
			}

			for(Edge e : current.getEdges()){
				Cell next = (Cell) e.getDestination();

				if(closedList.contains(next))
					continue;

				double distanceFromStart = current.getDistanceFromStart() + e.getCost();

				double distanceToGoal = next.isObstacle() ? Double.POSITIVE_INFINITY : euclideanDistance(next.getPosition(), goal.getPosition());

				double estimate = distanceFromStart + distanceToGoal;
				if(next.getColor() != Color.DARK_GRAY && next.getColor() != Color.GREEN && !next.isObstacle())
					next.setColor(Color.GRAY);

				if(!openList.contains(next) || distanceFromStart < next.getDistanceFromStart()){
					next.setDistanceFromStart(distanceFromStart);
					next.setCost(estimate);
					next.setPredecessor(current);
					openList.add(next);
				}
			}
		}

		LinkedList<Cell> shortestPath = new LinkedList<Cell>();
		Cell current = goal;
		shortestPath.add(current);

		while(current.getPredecessor()!= null){

			shortestPath.add((Cell)current.getPredecessor());
			current = (Cell)current.getPredecessor();

			if(current != start)
				current.setColor(Color.BLUE);
		}

		grid.update();

		Collections.reverse(shortestPath);

		return shortestPath;
	}

	public double euclideanDistance(Point p1, Point p2){
		return Math.sqrt(Math.pow(p1.getX() - p2.getX(), 2) + Math.pow(p1.getY() - p2.getY(), 2));
	}
}
