package com.analysisTest;

import java.awt.EventQueue;

public class Start {
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
		
			public void run() {
				try {
					CVSAnalysis window = new CVSAnalysis();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
