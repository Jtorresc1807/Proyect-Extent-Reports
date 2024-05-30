package com.reports.tests;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.reports.utils.Utils;

public class BaseTest {

	public WebDriver driver;
	public ExtentSparkReporter spark;		// Obejtos para la creacion de reportes
	public ExtentReports extent;
	public ExtentTest test;
	String reportName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

	@BeforeTest
	public void starReport() {
		// config e inicializacion del report
		spark = new ExtentSparkReporter(System.getProperty("user.dir") + "/extentreport/" + reportName + ".html");
		extent = new ExtentReports();
		extent.attachReporter(spark);
	}

	@BeforeMethod
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://juice-shop.herokuapp.com/#/");
	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {		//ITestResult listener que conoce los eventos de ejecucion
		if (result.getStatus() == ITestResult.FAILURE) {
			// agregar label
			test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test case Failed", ExtentColor.RED));

			// posible root cause
			test.log(Status.FAIL,
					MarkupHelper.createLabel(result.getThrowable() + " - Possible root cause", ExtentColor.AMBER));

			// take screenshot
			String screenPath = Utils.getScreenshotForReport(driver, result.getName());
			test.fail("more details", MediaEntityBuilder.createScreenCaptureFromPath(screenPath).build());

		} else if (result.getStatus() == ITestResult.SUCCESS) {

			test.log(Status.PASS,
					MarkupHelper.createLabel(result.getName() + " - Test case Passed", ExtentColor.GREEN));

		} else if (result.getStatus() == ITestResult.SKIP) {

			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test case Passed", ExtentColor.ORANGE));

		}

		driver.quit();
	}

	@AfterTest
	public void makeReport() {
		extent.flush();				// Genera el reporte
	}
}
