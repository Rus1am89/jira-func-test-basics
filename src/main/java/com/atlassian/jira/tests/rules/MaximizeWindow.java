package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

public class MaximizeWindow extends TestWatcher {

	private final AtlassianWebDriver driver;

	public MaximizeWindow(JiraTestedProduct jira) {
		this.driver = jira.getTester().getDriver();
	}

	public MaximizeWindow() {
		this.driver = WebDriverBrowserAutoInstall.INSTANCE.getDriver();
	}

	@Override
	protected void starting(Description description) {
		super.starting(description);

		maximize(driver);
	}

	public static void maximize(WebDriver driver) {
		driver.manage().window().setPosition(new Point(0,0));
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
		driver.manage().window().setSize(dim);
	}
}
