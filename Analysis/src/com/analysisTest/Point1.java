package com.analysisTest;

import com.analysisTest.Point1;

class Point1 implements Comparable<Point1> {
	int x, y;
	String date_info;

	public int compareTo(Point1 p) {
		if (this.x == p.x) {
			return this.y - p.y;
		} else {
			return this.x - p.x;
		}
	}

	public String toString() {
		return "("+x + "," + y+")";
	}
}