package org.gofleet.openLS.bean;

public class TSPStop {

	private double[] point;
	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public double[] getPoint() {
		return point;
	}

	public void setPoint(double[] point) {
		this.point = point;
	}

	public TSPStop(String s) {
		super();

		String[] array = s.split(",");

		this.point = new double[2];
		this.point[0] = new Double(array[0]);
		this.point[1] = new Double(array[1]);

		this.identifier = array[2];
	}

}
