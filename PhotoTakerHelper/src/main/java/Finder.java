package main.java;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.MatOfPoint;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.*;

public class Finder extends JPanel{
	
//	private BufferedImage image;
	public static int h_min = 0;
	public static int h_max = 255;
	public static int s_min = 0;
	public static int s_max = 255;
	public static int v_min = 0;
	public static int v_max = 255;
	
	private JFrame frame1;
	private JFrame frame2;
	private JFrame frame3;
	private JFrame frame4;
	private JFrame frame5;
	private JFrame frame6;
	
	private Panel panel1;
	private Panel panel2;
	private Panel panel3;
	private Panel panel4;
	private Panel panel5;
	private Panel panel6;
	
	private JSlider slider1;
	private JSlider slider2;
	private JSlider slider3;
	private JSlider slider4;
	private JSlider slider5;
	private JSlider slider6;
	
	private VideoCapture capture;
	
	private Mat webcam_image;
	private Mat hsv_image;
	private Mat hsv_filtered;
	private Mat final_image;
	private Mat contour_image;
	
	private Mat extra_image;
	private Mat tennis_image;
	
	
	private Mat erodeElement;
	private Mat dilateElement;
	
	public void initialize(){
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		frame1 = new JFrame("Camera");
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(640,480);
		frame1.setBounds(0, 0, frame1.getWidth(), frame1.getHeight());
		panel1 = new Panel();
		frame1.setContentPane(panel1);  
	    frame1.setVisible(true);
	    
	    frame2 = new JFrame("HSV");
		frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame2.setSize(640,480);
		frame2.setBounds(0, 0, frame2.getWidth(), frame2.getHeight());
		panel2 = new Panel();
		frame2.setContentPane(panel2);  
	    frame2.setVisible(true);
	    
	    frame3 = new JFrame("Filtered");
		frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame3.setSize(640,480);
		frame3.setBounds(0, 0, frame3.getWidth(), frame3.getHeight());
		panel3 = new Panel();
		frame3.setContentPane(panel3);  
	    frame3.setVisible(true);
	    
	    frame4 = new JFrame("Eroded and Dilated");
		frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame4.setSize(640,480);
		frame4.setBounds(0, 0, frame4.getWidth(), frame4.getHeight());
		panel4 = new Panel();
		frame4.setContentPane(panel4);  
	    frame4.setVisible(true);
	    
	    frame5 = new JFrame("Contour Image");
		frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame5.setSize(640,480);
		frame5.setBounds(0, 0, frame5.getWidth(), frame5.getHeight());
		panel5 = new Panel();
		frame5.setContentPane(panel5);  
	    frame5.setVisible(true);
	    
	    frame6 = new JFrame("HSV Value Sliders");
		frame6.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame6.setSize(640,480);
		frame6.setBounds(0, 0, 400, 300);
		panel6 = new Panel();
		
		slider1 = new JSlider();
		slider1.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 500));
        slider1.setMajorTickSpacing(5);
        slider1.setPaintTicks(true);
        slider1.setSize(0, 255);
        slider1.setValue(h_min);
        slider1.setVisible(true);
        
        slider2 = new JSlider();
		slider2.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 500));
        slider2.setMajorTickSpacing(5);
        slider2.setPaintTicks(true);
        slider2.setSize(0, 255);
        slider2.setValue(h_max);
        slider2.setVisible(true);
        
        slider3 = new JSlider();
		slider3.setLayout(new FlowLayout(FlowLayout.CENTER, 250, 500));
        slider3.setMajorTickSpacing(5);
        slider3.setPaintTicks(true);
        slider3.setSize(0, 255);
        slider3.setValue(s_min);
        slider3.setVisible(true);
        
        slider4 = new JSlider();
		slider4.setLayout(new FlowLayout(FlowLayout.CENTER, 300, 500));
        slider4.setMajorTickSpacing(5);
        slider4.setPaintTicks(true);
        slider4.setSize(0, 255);
        slider4.setValue(s_max);
        slider4.setVisible(true);
        
        slider5 = new JSlider();
		slider5.setLayout(new FlowLayout(FlowLayout.CENTER, 400, 500));
        slider5.setMajorTickSpacing(5);
        slider5.setPaintTicks(true);
        slider5.setSize(0, 255);
        slider5.setValue(v_min);
        slider5.setVisible(true);
        
        slider6 = new JSlider();
		slider6.setLayout(new FlowLayout(FlowLayout.CENTER, 450, 500));
        slider6.setMajorTickSpacing(5);
        slider6.setPaintTicks(true);
        slider6.setSize(0, 255);
        slider6.setValue(v_max);
        slider6.setVisible(true);
        
        panel6.add(slider1);
        panel6.add(slider2);
        panel6.add(slider3);
        panel6.add(slider4);
        panel6.add(slider5);
        panel6.add(slider6);
        
        frame6.setContentPane(panel6);
	    frame6.setVisible(true);
	    
		capture = new VideoCapture(2);
		
		webcam_image = new Mat();
		hsv_image = new Mat();
		hsv_filtered = new Mat();
		final_image = new Mat();
		contour_image = new Mat();
		
		extra_image = new Mat();
		tennis_image = new Mat();
		
		
		erodeElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(3,3));
		dilateElement = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(8,8));
		
		capture.read(webcam_image);
		capture.read(hsv_image);
		capture.read(hsv_filtered);
		capture.read(final_image);
		capture.read(contour_image);
		
	//	capture.read(extra_image);
		
		frame1.setSize(webcam_image.width()+40,webcam_image.height()+60);
		frame2.setSize(webcam_image.width()+40,webcam_image.height()+60);
		frame3.setSize(webcam_image.width()+40,webcam_image.height()+60);
		frame4.setSize(webcam_image.width()+40,webcam_image.height()+60);
		frame5.setSize(webcam_image.width()+40,webcam_image.height()+60);
				
//		Scalar hsv_min = new Scalar(50, 80, 80, 0); //red 160
//	    Scalar hsv_max = new Scalar(80, 120, 120, 0); //red 180
	    
	    int kernelSize = 9;
	}
	
	public String[] onceThrough(String fileName){
		String[] coordinates = new String[5];
		for (int i = 0; i < 5; i++){
			coordinates[i] = Integer.toString(0);
		}
		if(capture.isOpened()){
//			while(true){
			
				capture.read(webcam_image);
				Imgcodecs.imwrite(fileName, webcam_image);
				if (!webcam_image.empty()){
					h_min = slider1.getValue()*255/100;
					h_max = slider2.getValue()*255/100;
					s_min = slider3.getValue()*255/100;
					s_max = slider4.getValue()*255/100;
					v_min = slider5.getValue()*255/100;
					v_max = slider6.getValue()*255/100;
//					Scalar hsv_min = new Scalar(h_min, s_min, v_min, 0); //red 160
//				    Scalar hsv_max = new Scalar(h_max, s_max, v_max, 0); //red 180
//					Scalar hsv_min2 = new Scalar(33, 43, 114, 0); //tennis ball
//				    Scalar hsv_max2 = new Scalar(94, 193, 255, 0); //tennis ball
					Scalar hsv_min = new Scalar(104, 94, 132, 0); //speaker
				    Scalar hsv_max = new Scalar(135, 242, 255, 0); //speaker
//					Scalar hsv_min = new Scalar(38, 114, 99, 0); //water bottle
//				    Scalar hsv_max = new Scalar(91, 254, 224, 0); //water bottle
//				    Scalar hsv_min1 = new Scalar(66, 25, 135, 0); //phone
//				    Scalar hsv_max1 = new Scalar(124, 86, 255, 0); //phone
					
					panel1.setimagewithMat(webcam_image);
					frame1.repaint();
					
					Imgproc.cvtColor(webcam_image, hsv_image, Imgproc.COLOR_BGR2HSV);
					panel2.setimagewithMat(hsv_image);
					frame2.repaint();
					
				
					Core.inRange(hsv_image, hsv_min, hsv_max, hsv_filtered);
//					Core.inRange(hsv_image, hsv_min1, hsv_max1, extra_image);
//					Core.inRange(hsv_image, hsv_min2, hsv_max2, tennis_image);
					//Core.inRange(hsv_image, hsv_min1, hsv_max1, hsv_filtered);//Ethan added
					panel3.setimagewithMat(hsv_filtered);
					frame3.repaint();
					
//					Imgproc.erode(hsv_filtered, final_image, erodeElement);
//					Imgproc.erode(hsv_filtered, final_image, erodeElement);
//					Imgproc.erode(hsv_filtered, final_image, erodeElement);
					Imgproc.erode(hsv_filtered, final_image, erodeElement);
					Imgproc.erode(hsv_filtered, final_image, erodeElement);
					
//					Imgproc.erode(extra_image, extra_image, erodeElement);
//					Imgproc.erode(extra_image, extra_image, erodeElement);
//					
//					Imgproc.erode(tennis_image, tennis_image, erodeElement);
//					Imgproc.erode(tennis_image, tennis_image, erodeElement);
//					Imgproc.dilate(hsv_filtered, final_image, dilateElement);
//					Imgproc.dilate(hsv_filtered, final_image, dilateElement);
					
					panel4.setimagewithMat(final_image);
					frame4.repaint();
					
					
					capture.read(contour_image);
					//final_image.copyTo(contour_image);
					List<MatOfPoint> contours = new ArrayList<MatOfPoint>();    
				    Imgproc.findContours(final_image, contours, new Mat(), Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
				    int largestRect = 0;
				    double previousRectArea = 0.0;
				    for(int i=0; i< contours.size();i++){
//				        System.out.println(Imgproc.contourArea(contours.get(i)));
				        if (Imgproc.contourArea(contours.get(i)) > 1000 ){
				            Rect rect = Imgproc.boundingRect(contours.get(i));
				            //RotatedRect rect = Imgproc.minAreaRect(contours.get(i));
//				            System.out.println(rect.height);
				            if (rect.area() > previousRectArea){
				            	largestRect = i;
				            }
				            	
				            	
//				            if (rect.height > 28){
////				            System.out.println(rect.x +","+rect.y+","+rect.height+","+rect.width);
//				            Imgproc.rectangle(contour_image, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(255,0,0), 10);
//				            //	for (int j = 0; j < 4; j++){
//				            		//Imgproc.line(contour_image, new Point(rect.points(j)), pt2, color);
//				        	}
				            previousRectArea = rect.area();
				            
				        }
				    }
				    if (largestRect != 0){
					    Rect rect = Imgproc.boundingRect(contours.get(largestRect));
			            if (rect.height > 28){
	//		            System.out.println(rect.x +","+rect.y+","+rect.height+","+rect.width);
			            Imgproc.rectangle(contour_image, new Point(rect.x,rect.y), new Point(rect.x+rect.width,rect.y+rect.height),new Scalar(255,0,0), 10);
			            coordinates[0] = Integer.toString(1);
			            coordinates[1] = Integer.toString(rect.x);
			            coordinates[2] = Integer.toString(rect.y);
			            coordinates[3] = Integer.toString(rect.width);
			            coordinates[4] = Integer.toString(rect.height);
			            
			        	}
				    }
		        }
			        
					panel5.setimagewithMat(contour_image);
					frame5.repaint();
					
					
				}else{
					System.out.println(" --(!) No captured frame -- Break!");
//					break;
				}
//			}
		return coordinates;
		}
	}

