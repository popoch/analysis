package com.analysisTest;

import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class Drawing1 extends JFrame {

	private DrawingPanel drawingpanel;
	private int Current_point;
	public Drawing1() {
		setResizable(false);
		setBounds(0, 0, 1920, 1080);
		drawingpanel = new DrawingPanel();		
		setContentPane(drawingpanel);
		drawingpanel.repaint();
	}
	
	public void Input_data(int input_start_point) {
		Current_point = input_start_point;
	}

	public class DrawingPanel extends JPanel {
		@Override
		protected void paintComponent(Graphics g) {			
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());
			
			g.setColor(Color.WHITE);
			g.drawLine(0, this.getHeight(), this.getWidth(), this.getHeight());
			g.drawLine(0, this.getHeight(), 0, 0);
						
			g.drawString(Data.points2.get(Current_point).date_info_start+" ~ "+Data.points2.get(Current_point).date_info_end, 10, this.getHeight()-10);
			
			g.drawString("Source file : " + Data.file_name, 10, this.getHeight()-30);
			
			g.setColor(Color.BLUE);
			for(int count = 0 ; count < Data.points2.size() ; count ++) {
				g.drawOval(Data.points2.get(count).x - (Data.points2.get(count).size+1) / 2 , this.getHeight()-Data.points2.get(count).y - (Data.points2.get(count).size+1) / 2, Data.points2.get(count).size+1, Data.points2.get(count).size+1);			
			}	

			g.setColor(Color.RED);
			g.fillOval(Data.points2.get(Current_point).x - (Data.points2.get(Current_point).size+1) / 2, this.getHeight()-Data.points2.get(Current_point).y- (Data.points2.get(Current_point).size+1) / 2, Data.points2.get(Current_point).size+1, Data.points2.get(Current_point).size+1);
			
						
			if(Current_point!=0) {
				g.setColor(Color.PINK);
				g.fillOval(Data.points2.get(Current_point-1).x- (Data.points2.get(Current_point-1).size+1) / 2, this.getHeight()-Data.points2.get(Current_point-1).y - (Data.points2.get(Current_point-1).size+1) / 2, Data.points2.get(Current_point-1).size+1, Data.points2.get(Current_point-1).size+1);				
			
				g.setColor(Color.BLACK);
				g.drawLine(Data.points2.get(Current_point-1).x, this.getHeight()-Data.points2.get(Current_point-1).y, Data.points2.get(Current_point).x, this.getHeight()-Data.points2.get(Current_point).y);
				g.drawString(Data.points2.get(Current_point).x+" , "+Data.points2.get(Current_point).y, Data.points2.get(Current_point).x, this.getHeight()-Data.points2.get(Current_point).y);
				g.drawString(Data.points2.get(Current_point-1).x+" , "+Data.points2.get(Current_point-1).y, Data.points2.get(Current_point-1).x, this.getHeight()-Data.points2.get(Current_point-1).y);
				
			} else {
				g.setColor(Color.BLACK);
				g.drawString(Data.points2.get(Current_point).x+" , "+Data.points2.get(Current_point).y, Data.points2.get(Current_point).x, this.getHeight()-Data.points2.get(Current_point).y);
			}	
			
			
			g.setColor(Color.BLACK);
			g.drawLine(1280, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
			g.drawLine(1280, this.getHeight()/4, this.getWidth(), this.getHeight()/4);
			g.drawLine(1280, this.getHeight()/2, 1280, 0);
			
			//700
			//1080 540 270
			
			for(int count = 1 ; count<Data.pupil.size() ; count++) {
				int x1 = Math.round(((float)((count - 1)  * 700)) /((float) Data.pupil.size())) + 1280;
				int y1 = this.getHeight() -( Math.round(((float) Data.pupil.get(count-1).left * 270 )/((float) 10)) + 810 );
				
				int x2 = Math.round(((float)(count * 700)) /((float) Data.pupil.size()) ) + 1280;
				int y2 = this.getHeight() -(Math.round(((float) Data.pupil.get(count).left * 270 )/((float) 10)) + 810 );
				
				g.drawLine(x1, y1, x2, y2);
				
				if(count == 1) {
					g.drawString("Left Pupil "+ String.valueOf(Data.pupil.get(count-1).left), 1200, y1);
				}
				
				
				x1 = Math.round(((float)((count - 1) * 700)) /((float) Data.pupil.size())) + 1280;
				y1 = this.getHeight() -( Math.round(((float) Data.pupil.get(count-1).right * 270 )/((float) 10)) + 540 );
				
				x2 = Math.round(((float)(count * 700)) /((float) Data.pupil.size())) + 1280;
				y2 = this.getHeight() -(Math.round(((float) Data.pupil.get(count).right * 270 )/((float) 10)) + 540 );
				
				g.drawLine(x1, y1, x2, y2);
				
				if(count == 1) {
					g.drawString("Right Pupil "+String.valueOf(Data.pupil.get(count-1).right), 1190, y1);
				}
			}
			
			g.drawString("Max Pupil Left : "+ Data.max_pupil_left, 1700, 560);
			g.drawString("Min Pupil Left : "+ Data.mim_pupil_left, 1700, 580);
			g.drawString("Average Pupil Left : "+ Data.average_pupil_left, 1700, 600);
			g.drawString("Std Pupil Left : "+ Data.std_pupil_left, 1700, 620);
			
			g.drawString("Max Pupil Right : "+ Data.max_pupil_right, 1700, 660);
			g.drawString("Min Pupil Right : "+ Data.mim_pupil_right, 1700, 680);
			g.drawString("Average Pupil Right : "+ Data.average_pupil_right, 1700, 700);
			g.drawString("Std Pupil Right : "+ Data.std_pupil_right, 1700, 720);
		}
		
	}
}
