package com.unimelb.swen30006.metromadness.tracks;

import java.awt.geom.Point2D;

import com.badlogic.gdx.graphics.Color;
import com.unimelb.swen30006.metromadness.trains.Train;
/**
* @author ElliottUpton, Guangling Yang, Di Wang
* Group 15
*/
public class Track {
	public static final float DRAW_RADIUS=10f;
	public static final int LINE_WIDTH=6;
	public Point2D.Float startPos;
	public Point2D.Float endPos;
	public Color trackColour;
	private boolean occupied;
	protected String type = "Track";
	
	public Track(Point2D.Float start, Point2D.Float end, Color trackCol){
		this.startPos = start;
		this.endPos = end;
		this.trackColour = trackCol;
		this.occupied = false;
	}
	
	public boolean canEnter(boolean forward){
		return !this.occupied;
	}
	
	public void enter(Train t){
		this.occupied = true;
	}
	
	@Override
	public String toString() {
		return "Track [startPos=" + startPos + ", endPos=" + endPos + ", trackColour=" + trackColour + ", occupied="
				+ occupied + "]";
	}

	public void leave(Train t){
		this.occupied = false;
	}
	
	public String getType() {
		return type;
	}
}
