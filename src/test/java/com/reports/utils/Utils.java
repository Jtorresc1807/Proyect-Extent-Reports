package com.reports.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class    Utils {
	
	static String ScreenName2 = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
	
	//java (methods, function (return)
	
	public static String getScreenshotForReport(WebDriver driver, String ScreenName) throws IOException {
		
		TakesScreenshot ts = (TakesScreenshot)driver;
		
		//base 64
		//String srcBase64 = ts.getScreenshotAs(OutputType.BASE64);
		
		//bits
		//byte[] srcByte = ts.getScreenshotAs(OutputType.BYTES);
		
		File srcImage = ts.getScreenshotAs(OutputType.FILE);
		String temp = System.getProperty("user.dir")+"/screenshot/fails/"+ScreenName+"_"+ScreenName2+".png"; //ruta completa de la imagen
		File destination = new File(temp);				
		FileUtils.copyFile(srcImage, destination);		//Clase para copiar el archivo al directorio destination
		
		return temp;		//agrega la ruta del reporte
		
	}

}
