package com.analysisTest;

import java.util.ArrayList;
import com.analysisTest.Point2;

class Point2 implements Comparable<Point2> {
	int x, y;
	int size;
	ArrayList<PupilObject1> pupil = new ArrayList<>();
	String date_info_start;
	String date_info_end;
	
	public int compareTo(Point2 p) {
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