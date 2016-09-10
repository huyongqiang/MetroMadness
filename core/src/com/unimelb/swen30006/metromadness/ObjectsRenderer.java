package com.unimelb.swen30006.metromadness;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.unimelb.swen30006.metromadness.stations.Station;
import com.unimelb.swen30006.metromadness.tracks.Line;
import com.unimelb.swen30006.metromadness.tracks.Track;
import com.unimelb.swen30006.metromadness.trains.Train;

public class ObjectsRenderer {
	private ShapeRenderer renderer;
	private ArrayList<Station> stations;
	private ArrayList<Line> lines;
	private ArrayList<Train> trains;

	public ObjectsRenderer(ArrayList<Line> lines, ArrayList<Station> stations, ArrayList<Train> trains) {
		this.lines = lines;
		this.stations = stations;
		this.trains = trains;
	}

	/**
	 * The three functions actually are just a little bit different, so these functions 
	 * can be refactored into one function.
	 * 
	 * @param train
	 */
	public void renderTrain(Train train) {
		if (!train.inStation()) {
			Color col = train.forward ? train.FORWARD_COLOUR : train.BACKWARD_COLOUR;
			renderer.setColor(col);
			float radius = train.TRAIN_WIDTH;
			if (train.getType().equals("BigPassengerTrain")) {
				float percentage = train.getPassengers().size() / 20f;
				renderer.setColor(col.cpy().lerp(Color.DARK_GRAY, percentage));
				radius = train.TRAIN_WIDTH * (1 + percentage);
			} else if (train.getType().equals("SmallPassengerTrain")) {
				float percentage = train.getPassengers().size() / 10f;
				renderer.setColor(col.cpy().lerp(Color.DARK_GRAY, percentage));
				radius = train.TRAIN_WIDTH * (1 + percentage);
			}
			renderer.circle(train.pos.x, train.pos.y, radius);
		}
	}

	// public void renderTrain(Train train) {
	// if (!train.inStation()) {
	// Color col = train.forward ? train.FORWARD_COLOUR : train.BACKWARD_COLOUR;
	// renderer.setColor(col);
	// renderer.circle(train.pos.x, train.pos.y, train.TRAIN_WIDTH);
	// }
	// }
	//
	// public void renderSmallPassengerTrain(Train train) {
	// if (!train.inStation()) {
	// Color col = train.forward ? train.FORWARD_COLOUR : train.BACKWARD_COLOUR;
	// float percentage = train.passengers.size() / 10f;
	// renderer.setColor(col.cpy().lerp(Color.DARK_GRAY, percentage));
	// // We also get slightly bigger with passengers
	// renderer.circle(train.pos.x, train.pos.y, train.TRAIN_WIDTH * (1 +
	// percentage));
	// }
	// }
	//
	// public void renderBigPassengerTrain(Train train) {
	// if (!train.inStation()) {
	// Color col = train.forward ? train.FORWARD_COLOUR : train.BACKWARD_COLOUR;
	// float percentage = train.passengers.size() / 20f;
	// renderer.setColor(col.cpy().lerp(Color.DARK_GRAY, percentage));
	// renderer.circle(train.pos.x, train.pos.y, train.TRAIN_WIDTH * (1 +
	// percentage));
	// }
	// }

	public void renderLine(Line line) {
		renderer.setColor(line.trackColour);

		// Draw all the track sections
		for (Track t : line.getTracks()) {
			if (t.getType().equals("DualTrack"))
				renderDualTrack(t);
			else
				renderTrack(t);
		}
	}

	public void renderTrack(Track track) {
		renderer.rectLine(track.startPos.x, track.startPos.y, track.endPos.x, track.endPos.y, track.LINE_WIDTH);
	}

	public void renderDualTrack(Track track) {
		renderer.rectLine(track.startPos.x, track.startPos.y, track.endPos.x, track.endPos.y, track.LINE_WIDTH);
		renderer.setColor(new Color(245f / 255f, 245f / 255f, 245f / 255f, 0.5f).lerp(track.trackColour, 0.5f));
		renderer.rectLine(track.startPos.x, track.startPos.y, track.endPos.x, track.endPos.y, track.LINE_WIDTH / 3);
		renderer.setColor(track.trackColour);
	}

	public void renderStation(Station station) {
		float radius = station.RADIUS;
		for (int i = 0; (i < station.getLines().size() && i < station.MAX_LINES); i++) {
			Line l = station.getLines().get(i);
			renderer.setColor(l.lineColour);
			renderer.circle(station.position.x, station.position.y, radius, station.NUM_CIRCLE_STATMENTS);
			radius = radius - 1;
		}

		// Calculate the percentage
		float t = station.getTrains().size() / (float) station.PLATFORMS;
		Color c = Color.WHITE.cpy().lerp(Color.DARK_GRAY, t);
		renderer.setColor(c);
		renderer.circle(station.position.x, station.position.y, radius, station.NUM_CIRCLE_STATMENTS);
	}

	public void rendererObjects(ShapeRenderer renderer) {
		this.renderer = renderer;
		for (Line l : this.lines) {
			renderLine(l);
		}

		for (Train t : this.trains) {
			// if(t.getType().equals("BigPassengerTrain"))
			// renderBigPassengerTrain(t);
			// else if(t.getType().equals("SmallPassengerTrain"))
			// renderSmallPassengerTrain(t);
			// else
			// renderTrain(t);
			renderTrain(t);
		}

		for (Station s : this.stations) {
			renderStation(s);
		}

	}
}
