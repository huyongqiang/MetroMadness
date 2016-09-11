package com.unimelb.swen30006.metromadness.stations;

import java.util.ArrayList;
import java.util.Iterator;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.trains.Train;

public class NormalPlatformStation extends Station {

	// public PassengerGenerator g;
	private ArrayList<Passenger> waiting;
	private float maxVolume;

	public NormalPlatformStation(float x, float y, PassengerRouter router, String name, float maxPax) {
		super(x, y, router, name, maxPax);
		this.waiting = new ArrayList<Passenger>();
		// this.g = new PassengerGenerator(this, this.lines, maxPax);
		this.maxVolume = maxPax;
	}

	@Override
	public void enter(Train t) throws Exception {
		//checks if train is small enough
		if(getTrains().size() >= PLATFORMS && checkTrain(t)){
			throw new Exception();
		} else {
			// Add the train
			this.getTrains().add(t);
			// Add the waiting passengers
			Iterator<Passenger> pIter = this.waiting.iterator();
			while(pIter.hasNext()){
				Passenger p = pIter.next();
				try {
					t.embark(p);
					pIter.remove();
				} catch (Exception e){
					// Do nothing, already waiting
					break;
				}
			}
			
			//Do not add new passengers if there are too many already
			if (this.waiting.size() > maxVolume){
				return;
			}
		}
	}
	@Override
	public float getMaxPassengers() {
		return maxVolume;
	}
	/**
	 * assigns passengers to a Station
	 * 
	 * @param Passengers
	 *            the passengers to assign
	 */
	@Override
	public void assignPassengers(Passenger[] passengers) {
		for (Passenger p1 : passengers) {
			this.waiting.add(p1);
		}
	}

	public boolean checkTrain(Train t) {
		if (t.getType().equals("SmallPassengerTrain") | t.getType().equals("NormalPassengerTrain")) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * @returns the amount of space left in the station
	 */
	public float getPassengerSpace(){
		return maxPassengers - waiting.size();
	}
}
