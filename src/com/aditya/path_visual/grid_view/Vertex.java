package com.aditya.path_visual.grid_view;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Vertex implements Comparable<Vertex>, Serializable{
	private static final long serialVersionUID = 1L;
	
	public Point position;
	private double distanceFromStart = Double.POSITIVE_INFINITY;
	private double cost;
	private Vertex predecessor;
	private LinkedList<Edge> edges;
	
	public Vertex(Point position){
		this.position = position;
		this.distanceFromStart = Double.POSITIVE_INFINITY;
		this.edges = new LinkedList<Edge>();
	}
		
	public void addEdge(Edge edge){
		edges.add(edge);		
	}
	
	public void setDistanceFromStart(double distanceFromStart){
		this.distanceFromStart = distanceFromStart;
	}	
	
	public void setCost(double cost){
		this.cost = cost;
	}
	
	public void setPredecessor(Vertex predecessor){
		this.predecessor = predecessor;
	}
	
	public double getDistanceFromStart(){
		return distanceFromStart;
	}
	
	public double getCost(){
		return cost;
	}
	
	public Point getPosition(){
		return position;
	}
	
	public Edge getEdge(int index){
		return edges.get(index);
	}
		
	public LinkedList<Edge> getEdges(){
		return edges;
	}
	
	public Vertex getPredecessor(){
		return predecessor;
	}
	
	public int compareTo(Vertex other){
		return Double.compare(cost, other.cost);
	}
}
