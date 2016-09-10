package com.unimelb.swen30006.metromadness.trains;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.passengers.Passenger;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;

public class BigPassengerTrain extends Train {

	public BigPassengerTrain(Line trainLine, Station start, boolean forward) {
		super(trainLine, start, forward);
		type = "BigPassengerTrain";
	}

	@Override
	public void embark(Passenger p) throws Exception {
		if (this.getPassengers().size() > 80) {
			throw new Exception();
		}
		this.getPassengers().add(p);
	}

//	@Override
//	public void render(ShapeRenderer renderer) {
//		if (!this.inStation()) {
//			Color col = this.forward ? FORWARD_COLOUR : BACKWARD_COLOUR;
//			float percentage = this.passengers.size() / 20f;
//			renderer.setColor(col.cpy().lerp(Color.DARK_GRAY, percentage));
//			renderer.circle(this.pos.x, this.pos.y, TRAIN_WIDTH * (1 + percentage));
//		}
//	}

}
