package com.unimelb.swen30006.metromadness.trains;

import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class SmallPassengerTrain extends Train {

	public SmallPassengerTrain(Line trainLine, Station start, boolean forward) {
		super(trainLine, start, forward);
		type = "SmallPassengerTrain";
	}

	@Override
	public void embark(Passenger p) throws Exception {
		if(this.getPassengers().size() > 10){
			throw new Exception();
		}
		this.getPassengers().add(p);
	}
}
