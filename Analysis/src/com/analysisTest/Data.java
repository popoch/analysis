package com.analysisTest;

import java.util.ArrayList;

public class Data {
	public static int window_size;
	
	public static float weight;
	public static ArrayList<String> original = new ArrayList<>();

	public static ArrayList<Point1> points1 = new ArrayList<>();
	public static ArrayList<Point2> points2 = new ArrayList<>();
	public static ArrayList<PupilObject1> pupil = new ArrayList<>();
	public static ArrayList<PupilObject1> pupil_normal = new ArrayList<>();
	
	public static float max_pupil_left = 0.0f;
	public static float mim_pupil_left = 0.0f;
	public static float average_pupil_left = 0.0f;
	public static float std_pupil_left = 0.0f;
	
	public static float max_pupil_right = 0.0f;
	public static float mim_pupil_right = 0.0f;
	public static float average_pupil_right = 0.0f;
	public static float std_pupil_right = 0.0f;
	
	public static float max_pupil_left_new = 0.0f;
	public static float mim_pupil_left_new = 0.0f;
	public static float average_pupil_left_new = 0.0f;
	public static float std_pupil_left_new = 0.0f;
	
	public static float max_pupil_right_new = 0.0f;
	public static float mim_pupil_right_new = 0.0f;
	public static float average_pupil_right_new = 0.0f;
	public static float std_pupil_right_new = 0.0f;
	
	public static int currentPoint2 = 0;
	public static Drawing2 DF2 = new Drawing2();
	public static Drawing1 DF1 = new Drawing1();
	
	public static String file_name;
	
	public static ArrayList<PupilObject1> normal_window = new ArrayList<>();
}