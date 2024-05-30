package com.reports.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import static org.testng.Assert.assertFalse;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class ReportTest extends BaseTest {

	@Test  (priority = 1)
	public void getTitle() { // pass
		test = extent.createTest("verify title", "Test que verifica el titulo de la pagina");
		test.assignAuthor("Carla");
		test.assignCategory("End2End");
		test.assignDevice("Win 11");
		AssertJUnit.assertEquals(driver.getTitle(), "OWASP Juice Shop");
	}

	@Test (priority = 3)
	public void getLogo() { // fail
		test = extent.createTest("verify logo", "Test que verifica el logo de la pagina");
		test.assignAuthor("Cesar");
		test.assignCategory("Regression");
		test.assignDevice("oS");
		boolean logo = driver.findElement(By.className("logo")).isDisplayed();
		AssertJUnit.assertFalse(logo);
	}
	
	@Test (priority = 2)
	public void skipTest() { // skip
		test = extent.createTest("Skip Test", "Test no implementado");
		test.assignAuthor("Carlos");
		test.assignCategory("Smoke");
		test.assignDevice("Linux");
		throw new SkipException("pending to implement");
	}
}