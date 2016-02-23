package com.analysisTest;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class CVSAnalysis {
	
	public JFrame frame;
	public JTextField Window_Size_TextField;
	public JTextField Weight_TextField;
	public Thread th2;
	
	public CVSAnalysis() {
		run();
	}

	private void run() {
		
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		
////////////////////////////////////////////////////////////////////////main frame
		frame = new JFrame();
		frame.setBounds(width/2-250, height/2-150, 500, 300);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
////////////////////////////////////////////////////////////////////////label = window size
		JLabel lblWindowSize = new JLabel("Window Size");
		lblWindowSize.setBounds(310, 15, 80, 25);
		frame.getContentPane().add(lblWindowSize);
		
////////////////////////////////////////////////////////////////////////text input field = window size
		Window_Size_TextField = new JTextField();
		Window_Size_TextField.setText("1");
		Window_Size_TextField.setBounds(400, 15, 80, 25);
		frame.getContentPane().add(Window_Size_TextField);
		Window_Size_TextField.setColumns(10);
		
////////////////////////////////////////////////////////////////////////label = weight
		JLabel lblWeight = new JLabel("Weight");
		lblWeight.setBounds(310, 50, 80, 25);
		frame.getContentPane().add(lblWeight);
				
////////////////////////////////////////////////////////////////////////text input field = weight
		Weight_TextField = new JTextField();
		Weight_TextField.setText("1");
		Weight_TextField.setBounds(400, 50, 80, 25);
		frame.getContentPane().add(Weight_TextField);
		Weight_TextField.setColumns(10);
		
////////////////////////////////////////////////////////////////////////button = file import
		JButton btnFileImport = new JButton("File Import");
		btnFileImport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser JFC = new JFileChooser("/Users/Administrator/Desktop/151204/Edited_data");
				JFC.setFileFilter(new FileNameExtensionFilter("CSV", "csv"));
				JFC.setMultiSelectionEnabled(false);
				JFC.showOpenDialog(frame);
				analyze(JFC.getSelectedFile());
			}	
		});
		btnFileImport.setBounds(15, 15, 150, 25);
		frame.getContentPane().add(btnFileImport);
		
////////////////////////////////////////////////////////////////////////button = clear memory
		JButton btnClearMem = new JButton("Clear Memory");
		btnClearMem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Data.points1.clear();
				Data.points2.clear();
				Data.original.clear();
				Data.currentPoint2 = 0;
				Data.pupil.clear();
				Data.pupil_normal.clear();
				Data.normal_window.clear();
			}	
		});
		btnClearMem.setBounds(15, 50, 150, 25);
		frame.getContentPane().add(btnClearMem);
		
////////////////////////////////////////////////////////////////////////button = next window
		JButton btnNextWindow = new JButton("Next Window");
		btnNextWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Data.currentPoint2++;
				Data.DF2.Input_data(Data.currentPoint2);
				Data.DF2.repaint();
			}
		});
		btnNextWindow.setBounds(330, 200, 150, 25);
		frame.getContentPane().add(btnNextWindow);
		
////////////////////////////////////////////////////////////////////////button = start analysis
		JButton btnInitAnalysis = new JButton("Analysis");
		btnInitAnalysis.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Data.currentPoint2 = 0;
				Data.weight = Float.valueOf(Weight_TextField.getText());
				Data.window_size = Integer.valueOf(Window_Size_TextField.getText());
				Data.DF2.Input_data(Data.currentPoint2);
				Data.DF2.repaint();
				Data.DF2.setVisible(true);
			}
		});
		btnInitAnalysis.setBounds(172, 200, 150, 25);
		frame.getContentPane().add(btnInitAnalysis);

////////////////////////////////////////////////////////////////////////button = previous window
		JButton btnPreWindow = new JButton("Previous Window");
		btnPreWindow.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Data.currentPoint2--;
				Data.DF2.Input_data(Data.currentPoint2);
				Data.DF2.repaint();
			}
		});
		btnPreWindow.setBounds(15, 200, 150, 25);
		frame.getContentPane().add(btnPreWindow);
		
////////////////////////////////////////////////////////////////////////button = play
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				th2 = new Thread(new video2());
				th2.start();
			}
		});
		btnPlay.setBounds(15, 235, 150, 25);
		frame.getContentPane().add(btnPlay);
		
////////////////////////////////////////////////////////////////////////button = stop
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				th2.stop();
			}
		});
		btnStop.setBounds(330, 235, 150, 25);
		frame.getContentPane().add(btnStop);
		
////////////////////////////////////////////////////////////////////////button = save image
		JButton btnSaveImage = new JButton("Save Image");
		btnSaveImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					BufferedImage bi = new BufferedImage(Data.DF2.getSize().width, Data.DF2.getSize().height, BufferedImage.TYPE_INT_ARGB);
					Graphics g = bi.createGraphics();
					Data.DF2.paint(g);
					g.dispose();
					JFileChooser JFC = new JFileChooser("/Users/Administrator/Downloads");	
					JFC.setSelectedFile(new File("PIC_"+Data.file_name));
					int retrival = JFC.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				    	File ifile = new File(JFC.getSelectedFile()+".png");
				    	ImageIO.write(bi, "png", ifile);				    	
				    }
				} catch(Exception Ex) {
					
				}
			}
		});
		btnSaveImage.setBounds(172, 235, 150, 25);
		frame.getContentPane().add(btnSaveImage);
		
////////////////////////////////////////////////////////////////////////button = normalize
		JButton btnNorButton = new JButton("Normalize");
		btnNorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try	{
					normalize nr = new normalize();
					nr.run();
					
					JFileChooser JFC = new JFileChooser("/Users/Administrator/Downloads");
					JFC.setSelectedFile(new File("NOR_"+Data.file_name));
					int retrival = JFC.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				    	
				    	FileWriter fw = new FileWriter(JFC.getSelectedFile()+".csv");
				    	for(int count = 0; count < Data.pupil_normal.size(); count++) {
				    		if(count == 0) {
				    			fw.write(String.valueOf(Data.pupil.get(count).left) + ","
				    					+ String.valueOf(Data.pupil.get(count).right) + ","
				    					+ "Average" + ","
				    					+ String.valueOf(Data.average_pupil_left) + ","
				    					+ String.valueOf(Data.average_pupil_right) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).left) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).right) + ","
				    					+ "Average" + ","
				    					+ String.valueOf(Data.average_pupil_left_new) + ","
				    					+ String.valueOf(Data.average_pupil_right_new) + "\n");
				    		} else if(count == 1) {
				    			fw.write(String.valueOf(Data.pupil.get(count).left) + ","
				    					+ String.valueOf(Data.pupil.get(count).right) + ","
				    					+ "Max" + ","
				    					+ String.valueOf(Data.max_pupil_left) + ","
				    					+ String.valueOf(Data.max_pupil_right) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).left) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).right) + ","
				    					+ "Max" + ","
				    					+ String.valueOf(Data.max_pupil_left_new) + ","
				    					+ String.valueOf(Data.max_pupil_right_new) + "\n");
				    		} else if(count == 2) {
				    			fw.write(String.valueOf(Data.pupil.get(count).left) + ","
				    					+ String.valueOf(Data.pupil.get(count).right) + ","
				    					+ "Min" + ","
				    					+ String.valueOf(Data.mim_pupil_left) + ","
				    					+ String.valueOf(Data.mim_pupil_right) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).left) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).right) + ","
				    					+ "Min" + ","
				    					+ String.valueOf(Data.mim_pupil_left_new) + ","
				    					+ String.valueOf(Data.mim_pupil_right_new) + "\n");
				    		} else if(count == 3) {
				    			fw.write(String.valueOf(Data.pupil.get(count).left) + ","
				    					+ String.valueOf(Data.pupil.get(count).right) + ","
				    					+ "Std" + ","
				    					+ String.valueOf(Data.std_pupil_left) + ","
				    					+ String.valueOf(Data.std_pupil_right) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).left) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).right) + ","
				    					+ "Std" + ","
				    					+ String.valueOf(Data.std_pupil_left_new) + ","
				    					+ String.valueOf(Data.std_pupil_right_new) + "\n");
				    		} else {
				    			fw.write(String.valueOf(Data.pupil.get(count).left) + ","
				    					+ String.valueOf(Data.pupil.get(count).right) + ","
				    					+ "," + ","
				    					+ ","
				    					+ String.valueOf(Data.pupil_normal.get(count).left) + ","
				    					+ String.valueOf(Data.pupil_normal.get(count).right) + ","
				    					+ ","+","
				    					+ ","
				    					+ "\n");
				    		}
				    	}
				    	fw.close();
				    }
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNorButton.setBounds(172, 165, 150, 25);
		frame.getContentPane().add(btnNorButton);
		
////////////////////////////////////////////////////////////////////////button = Window normalize
		JButton btnWNButton = new JButton("Window_Normalize");
		btnWNButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try	{
					window_size_normalize wnr = new window_size_normalize();
					wnr.run();
				
					JFileChooser JFC2 = new JFileChooser("/Users/Administrator/Downloads");
					JFC2.setSelectedFile(new File("WNOR_"+Data.file_name));
					int retrival2 = JFC2.showSaveDialog(null);
					if (retrival2 == JFileChooser.APPROVE_OPTION) {
						
						FileWriter fw2 = new FileWriter(JFC2.getSelectedFile()+".csv");
						for(int count = 0; count < Data.normal_window.size(); count++) {
							fw2.write(String.valueOf(Data.normal_window.get(count).left) + ","
									 + String.valueOf(Data.normal_window.get(count).right) + "\n");
						}
						fw2.close();
					}
				} catch(Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		btnWNButton.setBounds(172, 130, 150, 25);
		frame.getContentPane().add(btnWNButton);

////////////////////////////////////////////////////////////////////////button = histogram source
		JButton btnHistogramSource = new JButton("Histogram Source");
		btnHistogramSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser JFC = new JFileChooser("/Users/Administrator/Downloads");
					JFC.setSelectedFile(new File("HS_"+Data.file_name));
					int retrival = JFC.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				    	FileWriter fw = new FileWriter(JFC.getSelectedFile());
				    	
				    	for(Point2 temp : Data.points2) {
				    		fw.write(String.valueOf(temp.size)+","+String.valueOf(((float) temp.size)/120.0f)+"\n");
				    	}
				    	fw.close();
				    }
				} catch(Exception ex) {
					
				}
			}
		});
		btnHistogramSource.setBounds(330, 165, 150, 25);
		frame.getContentPane().add(btnHistogramSource);
		
//////////////////////////////////////////////////////////////////////////button = APACS
		JButton btnAPACS = new JButton("APACS");
		btnAPACS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					JFileChooser JFC = new JFileChooser("/Users/Administrator/Downloads");
					JFC.setSelectedFile(new File("AP_"+Data.file_name));
					int retrival = JFC.showSaveDialog(null);
				    if (retrival == JFileChooser.APPROVE_OPTION) {
				    	FileWriter fw = new FileWriter(JFC.getSelectedFile());
				    	
				    	for(Point2 temp : Data.points2) {
				    		ArrayList<PupilObject1> n_pupil= normalize_function(temp.pupil);
				    		float left_pupil = 0.0f;
				    		float right_pupil = 0.0f;
				    		for(int count = 0; count < n_pupil.size(); count++) {
				    			left_pupil += n_pupil.get(count).left;
				    			right_pupil += n_pupil.get(count).right;				    			
				    		}
				    		left_pupil = left_pupil / ((float)  n_pupil.size());
				    		right_pupil = right_pupil / ((float)  n_pupil.size());
				    		fw.write(String.valueOf(left_pupil)+","+String.valueOf(right_pupil)+"\n");
				    	}
				    	fw.close();
				    }
				} catch(Exception ex) {
					
				}
			}
		});
		btnAPACS.setBounds(15, 165, 150, 25);
		frame.getContentPane().add(btnAPACS);
	}
////////////////////////////////////////////////////////////////////////normalize_function	
	public ArrayList<PupilObject1> normalize_function(ArrayList<PupilObject1> input) {
		ArrayList<PupilObject1> answer = new ArrayList<>();
		for(PupilObject1 temp : input) {
			PupilObject1 new_data = new PupilObject1();
			new_data.left = (temp.left - Data.average_pupil_left) / Data.std_pupil_left;
			new_data.right = (temp.right - Data.average_pupil_right) / Data.std_pupil_right;
			answer.add(new_data);
		}
		return answer;
	}
////////////////////////////////////////////////////////////////////////normalize
	public class normalize {
		public void run() {
			Data.pupil_normal.clear();
			for(PupilObject1 temp : Data.pupil) {
				PupilObject1 new_data = new PupilObject1();
				if(temp.left != 0 && temp.right != 0) {
					new_data.left = (temp.left - Data.average_pupil_left) / Data.std_pupil_left;
					new_data.right = (temp.right - Data.average_pupil_right) / Data.std_pupil_right;
					
					Data.pupil_normal.add(new_data);
				}
				if(temp.left == 0 && temp.right != 0) {
					new_data.left = 0;
					new_data.right = (temp.right - Data.average_pupil_right) / Data.std_pupil_right;
					
					Data.pupil_normal.add(new_data);
				}
				if(temp.left != 0 && temp.right == 0) {
					new_data.left = (temp.left - Data.average_pupil_left) / Data.std_pupil_left;
					new_data.right = 0;
					
					Data.pupil_normal.add(new_data);
				}
				if(temp.left == 0 && temp.right == 0) {
					new_data.left = 0;
					new_data.right = 0;
					
					Data.pupil_normal.add(new_data);
				}
			}
			
			float max_left = -1.0f;
			float min_left = 100.0f;
			float average_left = 0.0f;
			float std_left = 0.0f;
			
			float max_right = -1.0f;
			float min_right = 100.0f;
			float average_right = 0.0f;
			float std_right = 0.0f;
			
			for(PupilObject1 temp : Data.pupil_normal) {
				if(temp.left > max_left)
				{
					max_left = temp.left;
				}
				
				if(temp.left < min_left)
				{
					min_left = temp.left;
				}
				
				average_left += temp.left;
				
				if(temp.right > max_right)
				{
					max_right = temp.right;
				}
				
				if(temp.right < min_right)
				{
					min_right = temp.right;
				}
				
				average_right += temp.right;
			}
			
			average_left = average_left / ((float) Data.pupil_normal.size());
			average_right = average_right / ((float) Data.pupil_normal.size());
			
			for(PupilObject1 temp : Data.pupil_normal) {
				std_left += (float) Math.pow(temp.left - average_left, 2.0);
				std_right += (float) Math.pow(temp.right - average_right, 2.0);
			}
			std_left = (float) Math.sqrt(std_left/((float) Data.pupil_normal.size()));
			std_right = (float) Math.sqrt(std_right/((float) Data.pupil_normal.size()));
			
			Data.max_pupil_left_new = max_left;
			Data.mim_pupil_left_new = min_left;
			Data.average_pupil_left_new = average_left;
			Data.std_pupil_left_new = std_left;
			
			Data.max_pupil_right_new = max_right;
			Data.mim_pupil_right_new = min_right;
			Data.average_pupil_right_new = average_right;
			Data.std_pupil_right_new = std_right;
		}
	}
	
////////////////////////////////////////////////////////////////////////window_size fixed analyze
	public class window_size_normalize {
		public void run() {
			Data.pupil_normal.clear();
			Data.normal_window.clear();
			for(PupilObject1 temp2 : Data.pupil) {
				PupilObject1 new_data2 = new PupilObject1();
				new_data2.left = (temp2.left - Data.average_pupil_left) / Data.std_pupil_left;
				new_data2.right = (temp2.right - Data.average_pupil_right) / Data.std_pupil_right;
				
				Data.pupil_normal.add(new_data2);
			}
			
			int window_size_input = Integer.valueOf(Window_Size_TextField.getText()) * 120;
			int array_size = ((int)(Data.pupil_normal.size()/window_size_input)) + 1;
			
			float[][] temp2 = new float[array_size][window_size_input];
			float[][] temp3 = new float[array_size][window_size_input];
			
			int i = 0;
			int j = 0;
			for(PupilObject1 temp : Data.pupil_normal) {	
				temp2[i][j] = temp.left;
				temp3[i][j] = temp.right;
				j++;
				if(j == window_size_input) {
					i++;
					j = 0;
				}
			}
			
			
			
			for(int k = 0; k < array_size; k++) {
			
				PupilObject1 temp_data = new PupilObject1();
				float normal_left = 0;
				float normal_right = 0;
				for(int z = 0; z < window_size_input; z++) {
					if(temp2[k][z] == 0 && temp3[k][z] == 0) {
						window_size_input = z;
					} else {
					 normal_left += temp2[k][z];
					 normal_right += temp3[k][z];
					}
				}
			
				temp_data.left = normal_left / window_size_input;
				temp_data.right = normal_right / window_size_input;
				Data.normal_window.add(temp_data);
			}
		}
	}
	
////////////////////////////////////////////////////////////////////////analyze
	private void analyze(File inputFile) {	
		
		try {
			Data.original.clear();
			Data.points1.clear();
			Data.points2.clear();
			Data.original = (ArrayList<String>) Files.readAllLines(inputFile.toPath(), Charset.forName("UTF-8"));
			Data.original.remove(0);
			
			inputFile.getName();
			Data.file_name = inputFile.getName();
			
			int count = 0;
			int weight = Integer.valueOf(Weight_TextField.getText());
			int x = -999999999;
			int y = -999999999;
			String date_info_start = null;
			String date_info_end = null;
						
			ArrayList<PupilObject1> temppupil = new ArrayList<>();
			
			for(String temp : Data.original) {
				String[] temps = temp.split(",", -1);
//////////////////////////////////////////////////////////////////////////current point
//				try {
//					if(Integer.valueOf(temps[6]) >= 0 && Integer.valueOf(temps[7]) >= 0) {			//33:AH(GazePointX (MCSpx))
//						Point1 CP = new Point1();													//34:AI(GazePointY (MCSpx))
//						CP.x = Integer.valueOf(temps[6]);									
//						CP.y = Integer.valueOf(temps[7]);
//						CP.date_info = temps[24];													//17:R(LocalTimeStamp)
//					}				
//				} catch(Exception ex) {
//					
//				}
//////////////////////////////////////////////////////////////////////////gaze point and duration
//					try {
//							if(Integer.valueOf(temps[3]) >= 0 && Integer.valueOf(temps[4])>= 0) {	//24:Y(FixationPointX (MCSpx))
//								if(count == 0) {													//25:Z(FixationPointY (MCSpx))
//									PupilObject1 temp_pu = new PupilObject1();
//									x = Integer.valueOf(temps[3]);
//									y = Integer.valueOf(temps[4]);
//									
//									temp_pu.left = Float.valueOf(temps[12]);						//49:AX(PupilLeft)
//									temp_pu.right = Float.valueOf(temps[19]);						//50:AY(PupilRight)
//									
//									temppupil.add(temp_pu);
//									date_info_start = temps[0];
//									date_info_end = temps[0];
//									count++;
//									
//
//								} else {
//									if(x == Integer.valueOf(temps[3]) && y == Integer.valueOf(temps[4])) {
//										weight++;
//										date_info_end = temps[17];
//										PupilObject1 temp_pu = new PupilObject1();
//										
//										temp_pu.left = Float.valueOf(temps[12]);
//										temp_pu.right = Float.valueOf(temps[19]);
//										temppupil.add(temp_pu);
//										count++;
//									} else {
//										Point2 CP = new Point2();
//										CP.date_info_start = date_info_start;
//										CP.date_info_end = date_info_end;
//										CP.x = x;
//										CP.y = y;
//										CP.size = weight;
//										CP.pupil = (ArrayList<PupilObject1>) temppupil.clone();								
//										Data.points2.add(CP);
//																		
//										temppupil.clear();
//										x = Integer.valueOf(temps[3]);
//										y = Integer.valueOf(temps[4]);
//										PupilObject1 temp_pu = new PupilObject1();
//										
//										temp_pu.left = Float.valueOf(temps[12]);
//										temp_pu.right = Float.valueOf(temps[19]);
//										temppupil.add(temp_pu);
//										date_info_start = temps[0];
//										date_info_end = temps[0];
//										weight = Integer.valueOf(Weight_TextField.getText());
//										count++;
//									}						
//								}					
//							}	
//					} catch(Exception ex) {
//						
//					}
////////////////////////////////////////////////////////////////////////pupil
				try {
					if(temps[0].equalsIgnoreCase("tracker") == true) {
//						if(temps[12].isEmpty() && temps[19].isEmpty()) {
						if(Float.valueOf(temps[12]) <= 0.0f && Float.valueOf(temps[19]) <= 0.0f) {
							PupilObject1 PO = new PupilObject1();
							PO.left = 0;
							PO.right = 0;
							PO.date_info = temps[2];
							
							Data.pupil.add(PO);
						}else if(Float.valueOf(temps[12]) <= 0.0f && Float.valueOf(temps[19]) > 0.0f) {
							PupilObject1 PO = new PupilObject1();
							PO.left = 0;
							PO.right = Float.valueOf(temps[19]);
							PO.date_info = temps[2];
							
							Data.pupil.add(PO);
						}else if(Float.valueOf(temps[12]) > 0.0f && Float.valueOf(temps[19]) <= 0.0f) {
							PupilObject1 PO = new PupilObject1();
							PO.left = Float.valueOf(temps[12]);
							PO.right = 0;
							PO.date_info = temps[2];
							
							Data.pupil.add(PO);
						}else if(Float.valueOf(temps[12]) > 0.0f && Float.valueOf(temps[19])> 0.0f) {
							PupilObject1 PO = new PupilObject1();
							PO.left = Float.valueOf(temps[12]);
							PO.right = Float.valueOf(temps[19]);
							PO.date_info = temps[2];
							
							Data.pupil.add(PO);
						}		
					}
				} catch(Exception ex) {
					
				}
			}
			float max_left = -1.0f;
			float min_left = 100.0f;
			float average_left = 0.0f;
			float std_left = 0.0f;
			
			float max_right = -1.0f;
			float min_right = 100.0f;
			float average_right = 0.0f;
			float std_right = 0.0f;
			
			for(PupilObject1 temp : Data.pupil) {
				if(temp.left > max_left) {
					max_left = temp.left;
				}
				
				if(temp.left < min_left) {
					min_left = temp.left;
				}
				
				average_left += temp.left;
				
				if(temp.right > max_right) {
					max_right = temp.right;
				}
				
				if(temp.right < min_right) {
					min_right = temp.right;
				}
				
				average_right += temp.right;
			}
			
			average_left = average_left / ((float) Data.pupil.size());
			average_right = average_right / ((float) Data.pupil.size());
			
			for(PupilObject1 temp : Data.pupil) {
				std_left += (float) Math.pow(temp.left - average_left, 2.0);
				std_right += (float) Math.pow(temp.right - average_right, 2.0);
			}
			std_left = (float) Math.sqrt(std_left/((float) Data.pupil.size()));
			std_right = (float) Math.sqrt(std_right/((float) Data.pupil.size()));
			
			Data.max_pupil_left = max_left;
			Data.mim_pupil_left = min_left;
			Data.average_pupil_left = average_left;
			Data.std_pupil_left = std_left;
			
			Data.max_pupil_right = max_right;
			Data.mim_pupil_right = min_right;
			Data.average_pupil_right = average_right;
			Data.std_pupil_right = std_right;
			
			JOptionPane.showMessageDialog(frame, "Data Importing Complete");			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
////////////////////////////////////////////////////////////////////////run animation
	public class video2 implements Runnable {

		@Override
		public void run() {
			while(true) {
				try {
					Thread.sleep((long) 300.0);
					Data.currentPoint2++;
					Data.DF2.Input_data(Data.currentPoint2);
					Data.DF2.repaint();
					
					if(Data.points2.size() - Data.currentPoint2 < 0) {
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
