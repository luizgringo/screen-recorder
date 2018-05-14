package com.hotmart.screencapture;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.media.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Main class that starts the Recording process of EasyCapture.
 * 
 * @author Senthil Balakrishnan
 */
 
public class ScreenRecorder {

	/**
	 * Screen Width.
	 */
	public static int screenWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();

	/**
	 * Screen Height.
	 */
	public static int screenHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

	/**
	 * Interval between which the image needs to be captured.
	 */
	public static int captureInterval = 50;

	/**
	 * Temporary folder to store the screenshot.
	 */
	public static String store = "tmp";

	/**
	 * Status of the recorder.
	 */
	public static boolean record = false;

	/**
	 * 
	 */
	public static void startRecord() {
		Thread recordThread = new Thread() {
			@Override
			public void run() {
				Robot rt;
				int cnt = 0;
				try {
					rt = new Robot();
					while (cnt == 0 || record) {
						BufferedImage img = rt
								.createScreenCapture(new Rectangle(screenWidth,
										screenHeight));
						ImageIO.write(img, "jpeg", new File("./"+store+"/"
								+ System.currentTimeMillis() + ".jpeg"));
						if (cnt == 0) {
							record = true;
							cnt = 1;
						}
						// System.out.println(record);
						Thread.sleep(captureInterval);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		recordThread.start();
	}

//	/**
//	 * @throws MalformedURLException 
//	 * 
//	 */
//	public static void makeVideo(String movFile) throws MalformedURLException {
//		System.out.println("#### Easy Capture making video, please wait!!! ####");
//		JpegImagesToMovie imageToMovie = new JpegImagesToMovie();
//		Vector<String> imgLst = new Vector<String>();
//		File f = new File(store);
//		File[] fileLst = f.listFiles();
//		for (int i = 0; i < fileLst.length; i++) {
//			imgLst.add(fileLst[i].getAbsolutePath());
//		}
//		// Generate the output media locators.
//		MediaLocator oml;
//		if ((oml = imageToMovie.createMediaLocator(movFile)) == null) {
//			System.err.println("Cannot build media locator from: " + movFile);
//			System.exit(0);
//		}
//		imageToMovie.doIt(screenWidth, screenHeight, (1000 / captureInterval),
//				imgLst, oml);
//
//	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("######### Starting Easy Capture Recorder #######");
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		System.out.println("Your Screen [Width,Height]:" + "["+ screen.getWidth() + "," + screen.getHeight() + "]");
		//Scanner sc = new Scanner(System.in);
		System.out.println("Rate 20 Frames/Per Sec.");
		
		System.out.print("Now move to the screen you want to record");
		
	    //Create a new instance of the Firefox driver
	    WebDriver driver = new FirefoxDriver();
	    driver.manage().window().fullscreen();
	 
	    //Call the start method of ScreenRecorder to begin recording
		File f = new File(store);
		if(!f.exists()){
			f.mkdir();
		}
		startRecord();
		System.out.println("\nEasy Capture is recording now!!!!!!!");
	 
		driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
		driver.get("http://www.hotmart.com/pt/"); 

		//Create an action object called myMouse
		Actions myMouse = new Actions(driver); 

		WebElement entreLink = driver.findElement(By.linkText("Entre")); 
		myMouse.moveToElement(entreLink).build().perform();   //Shows link
		Thread.sleep(5000L);
		entreLink.click();
		
		Thread.sleep(5000L);
		
		WebElement digiteSeuUsuarioLabel = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/div/div/div[2]/div[1]/div[2]/form[2]/div/span[1]/input"));
		myMouse.moveToElement(digiteSeuUsuarioLabel).build().perform();   //home link
		Thread.sleep(5000L);
		digiteSeuUsuarioLabel.sendKeys("luiz.bueno@hotmart.com");
		
		Thread.sleep(5000L);
		
		WebElement senhaLink = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/div/div/div[2]/div[1]/div[2]/form[2]/div/span[2]/input")); 
		myMouse.moveToElement(senhaLink).build().perform();
		Thread.sleep(5000L);
		
		Thread.sleep(5000L);
		
		WebElement botaoEntrar = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div/div/div/div/div[2]/div[1]/div[2]/form[2]/button")); 
		myMouse.moveToElement(botaoEntrar).build().perform();
		Thread.sleep(5000L);
		botaoEntrar.click();
		
		Thread.sleep(5000L);
 
	    //Close the browser
	    driver.quit();
	 
	    //Call the stop method of ScreenRecorder to end the recording
		record = false;
		System.out.println("Easy Capture has stopped.");
		//makeVideo(System.currentTimeMillis()+".mov");
	}
}

