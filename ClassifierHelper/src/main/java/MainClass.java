package main.java;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Timer;

import javax.swing.JSlider;


public class MainClass {
	
	public static void main (String[] args) throws InterruptedException{
		Finder dataGetter = new Finder();
//		dataGetter.initialize();
//		Thread.sleep(5000);
		String[] fileNames = new String[2000];
		String[] textStrings = new String[2000];
		for (int i = 0; i < 2000; i++){
			fileNames[i] = "photo_"+i+".jpg";
		}
		for (int i = 0; i < 2000; i++){
			textStrings[i] = "C:\\opencv\\bluespeakerneg\\" + fileNames[i];
		}
		
		try {
			PrintWriter writer = new PrintWriter("bluespeakernegfiles.txt");
//			writer.println("The First Line");
//			writer.println("The Second CHANGEDEDEDED");
			
//			while (true)
//			dataGetter.onceThrough();
//			for (int i = 0; i < 2000; i++){
//				String[] now = dataGetter.onceThrough(textStrings[i]);
//				textStrings[i] += " " + now[0] + " " + now[1] + " " + now[2] + " " + now[3] + " " + now[4];
//				System.out.println(textStrings[i]);
//			}
			for (int i = 0; i < 2000; i++){
				writer.println(textStrings[i]);
			}
//			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String s: fileNames){
			System.out.println(s);
		}
	}

}
