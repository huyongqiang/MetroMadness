package com.unimelb.swen30006.metromadness.stations;

import java.util.ArrayList;
import java.util.Iterator;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.routers.PassengerRouter;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.trains.Train;

public class ShortPlatformStation extends Station {

	private ArrayList<Passenger> waiting;
	private float maxVolume;

	public ShortPlatformStation(float x, float y, PassengerRouter router, String name, float maxPax) {
		super(x, y, router, name, maxPax);
		this.waiting = new ArrayList<Passenger>();
		this.maxVolume = maxPax;
	}

	@Override
	public void enter(Train t) throws Exception {
		// checks if train is small enough
		if (getTrains().size() >= PLATFORMS) {
			throw new Exception();
		} else if (checkTrain(t)) {
			// Add the train
			this.getTrains().add(t);
			// Add the waiting passengers
			Iterator<Passenger> pIter = this.waiting.iterator();
			while (pIter.hasNext()) {
				Passenger p = pIter.next();
				try {
					t.embark(p);
					pIter.remove();
				} catch (Exception e) {
					// Do nothing, already waiting
					break;
				}
			}

			// Do not add new passengers if there are too many already
			if (this.waiting.size() > maxVolume) {
				return;
			}
		} else {
			depart(t);
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

	/**
	 * checks if the train can stop at the station
	 * 
	 * @param Train
	 *            the train line
	 */
	public boolean checkTrain(Train t) {
		if (t.getType().equals("SmallPassengerTrain")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * checks if the train can enter the station and stop
	 * 
	 * @param Line
	 *            the train line
	 * @param Train
	 *            the train line
	 */
	@Override
	public boolean canEnter(Line l, Train t) throws Exception {
		if (getTrains().size() < PLATFORMS && checkTrain(t)) {
			return true;
		} else {
			return false;
		}
	}
	public int getPassengerSpace(){
		return (int) maxPassengers - waiting.size();
	}
}
