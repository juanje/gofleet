package org.gofleet.openLS.bean;

import java.io.Serializable;

import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.io.WKTWriter;

public class TSPPlan implements Serializable {

	private static final long serialVersionUID = -8921665472351369061L;

	private double distance;
	private double time;
	private String way;
	private String[] stops;
	private double[] origin;

	public TSPPlan(Double distance, Double time, MultiLineString way,
			String[] stops, Point origin) {
		super();
		this.distance = distance;
		this.time = time;
		this.way = (new WKTWriter()).write(way);
		this.stops = stops;
		this.origin = getPoint(origin);
	}

	public TSPPlan() {
		super();
	}

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

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public void setStops(String[] stops) {
		this.stops = stops;
	}

	public void setOrigin(double[] origin) {
		this.origin = origin;
	}

	public String[] getStops() {
		return stops;
	}

	public double[] getOrigin() {
		return origin;
	}

	public void setOrigin(Point origin) {
		this.origin = getPoint(origin);
	}

	private double[] getPoint(Point p) {
		double[] res = new double[2];

		res[0] = p.getX();
		res[1] = p.getY();

		return res;
	}

	@Override
	public String toString() {
		return "TSPPlan[(numStops: " + this.getStops().length + ") " + " way: "
				+ this.getWay() + "]";
	}
}
