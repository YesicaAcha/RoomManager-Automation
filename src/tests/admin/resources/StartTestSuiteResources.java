package tests.admin.resources;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import framework.selenium.SeleniumDriverManager;

public class StartTestSuiteResources {
	private WebDriver driver = SeleniumDriverManager.getManager().getDriver();

	@BeforeSuite(groups = {"NEGATIVE","ACCEPTANCE","FUNCTIONAL"})
	public void init() {		
	}

	@AfterSuite(groups = {"NEGATIVE","ACCEPTANCE","FUNCTIONAL"})
	public void afterSuite() {
		driver.quit();
	}
}
