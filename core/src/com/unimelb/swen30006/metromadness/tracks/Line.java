package com.unimelb.swen30006.metromadness.tracks;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.stations.Station;

public class Line {
	
	// The colour of this line
	public Color lineColour;
	public Color trackColour;
	
	// The name of this line
	public String name;
	// The stations on this line
	private ArrayList<Station> stations;
	// The tracks on this line between stations
	private ArrayList<Track> tracks;
		
	// Create a line
	public Line(Color stationColour, Color lineColour, String name){
		// Set the line colour
		this.lineColour = stationColour;
		this.trackColour = lineColour;
		this.name = name;
		
		// Create the data structures
		this.setStations(new ArrayList<Station>());
		this.setTracks(new ArrayList<Track>());
	}
	
	
	public void addStation(Station s, Boolean two_way){
		// We need to build the track if this is adding to existing stations
		if(this.getStations().size() > 0){
			// Get the last station
			Station last = this.getStations().get(this.getStations().size()-1);
			
			// Generate a new track
			Track t;
			if(two_way){
				t = new DualTrack(last.position, s.position, this.trackColour);
			} else {
				t = new Track(last.position, s.position, this.trackColour);
			}
			this.getTracks().add(t);
		}
		
		// Add the station
		s.registerLine(this);
		this.getStations().add(s);
	}
	
	@Override
	public String toString() {
		return "Line [lineColour=" + lineColour + ", trackColour=" + trackColour + ", name=" + name + "]";
	}


	public boolean endOfLine(Station s) throws Exception{
		if(this.getStations().contains(s)){
			int index = this.getStations().indexOf(s);
			return (index==0 || index==this.getStations().size()-1);
		} else {
			throw new Exception();
		}
	}

	
	
	public Track nextTrack(Station currentStation, boolean forward) throws Exception {
		if(this.getStations().contains(currentStation)){
			// Determine the track index
			int curIndex = this.getStations().lastIndexOf(currentStation);
			// Increment to retrieve
			if(!forward){ curIndex -=1;}
			
			// Check index is within range
			if((curIndex < 0) || (curIndex > this.getTracks().size()-1)){
				throw new Exception();
			} else {
				return this.getTracks().get(curIndex);
			}
			
		} else {
			throw new Exception();
		}
	}
	
	public Station nextStation(Station s, boolean forward) throws Exception{
		if(this.getStations().contains(s)){
			int curIndex = this.getStations().lastIndexOf(s);
			if(forward){ curIndex+=1;}else{ curIndex -=1;}
			
			// Check index is within range
			if((curIndex < 0) || (curIndex > this.getStations().size()-1)){
				throw new Exception();
			} else {
				return this.getStations().get(curIndex);
			}
		} else {
			throw new Exception();
		}
	}


	public ArrayList<Station> getStations() {
		return stations;
	}


	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}


	public ArrayList<Track> getTracks() {
		return tracks;
	}


	public void setTracks(ArrayList<Track> tracks) {
		this.tracks = tracks;
	}
	
//	public void render(ShapeRenderer renderer){
//		// Set the color to our line
//		renderer.setColor(trackColour);
//	
//		// Draw all the track sections
//		for(Track t: this.tracks){
//			t.render(renderer);
//		}	
//	}
	
}
