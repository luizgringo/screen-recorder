package com.hotmart.screencapture;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.monte.media.math.Rational;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;
import static org.monte.media.AudioFormatKeys.*;
import static org.monte.media.VideoFormatKeys.*;
import java.awt.*;
 
public class SeScreenCastDemo {
     
    public static void main(String[] args) throws Exception {
 
	    //Create a instance of GraphicsConfiguration to get the Graphics configuration
	    //of the Screen. This is needed for ScreenRecorder class.
	    GraphicsConfiguration gc = GraphicsEnvironment//
	    .getLocalGraphicsEnvironment()//
	    .getDefaultScreenDevice()//
	        .getDefaultConfiguration();
	 
	    //Create a instance of ScreenRecorder with the required configurations
	    ScreenRecorder screenRecorder = new ScreenRecorder(gc,
	    new Format(MediaTypeKey, MediaType.FILE, MimeTypeKey, MIME_AVI),
	    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	    CompressorNameKey, ENCODING_AVI_TECHSMITH_SCREEN_CAPTURE,
	    DepthKey, (int)24, FrameRateKey, Rational.valueOf(15),
	    QualityKey, 1.0f,
	    KeyFrameIntervalKey, (int) (15 * 60)),
	    new Format(MediaTypeKey, MediaType.VIDEO, EncodingKey,"black",
	    FrameRateKey, Rational.valueOf(30)),
	    null);
	 
	    //Create a new instance of the Firefox driver
	    WebDriver driver = new FirefoxDriver();
	 
	    //Call the start method of ScreenRecorder to begin recording
	    screenRecorder.start();
	 
	    //And now use this to visit Google
	    driver.get("http://www.google.com");
	 
	    //Find the text input element by its name
	    WebElement element = driver.findElement(By.name("q"));
	 
	    //Enter something to search for
	    element.sendKeys("Cheese!");
	 
	    //Now submit the form. WebDriver will find the form for us from the element
	    element.submit();
	 
	    //Check the title of the page
	    System.out.println("Page title is: " + driver.getTitle());
	 
	    //Google's search is rendered dynamically with JavaScript.
	    //Wait for the page to load, timeout after 10 seconds
	    (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
	    public Boolean apply(WebDriver d) {
	    return d.getTitle().toLowerCase().startsWith("cheese!");
	    }});
	 
	    //Should see: "cheese! - Google Search"
	    System.out.println("Page title is: " + driver.getTitle());
	 
	    //Close the browser
	    driver.quit();
	 
	    //Call the stop method of ScreenRecorder to end the recording
	    screenRecorder.stop();
    }
}
