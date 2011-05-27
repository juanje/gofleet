package org.gofleet.openLS.bean;

import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;

public class TSPPlan {

	private Double distance;

	public TSPPlan(Double distance, Double time, MultiLineString way,
			Point[] stops, Point origin) {
		super();
		this.distance = distance;
		this.time = time;
		this.way = way;
		this.stops = stops;
		this.origin = origin;
	}

	private Double time;
	private MultiLineString way;

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public MultiLineString getWay() {
		return way;
	}

	public void setWay(MultiLineString way) {
		this.way = way;
	}

	public Point[] getStops() {
		return stops;
	}

	public void setStops(Point[] stops) {
		this.stops = stops;
	}

	public Point getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = origin;
	}

	private Point[] stops;
	private Point origin;
}
