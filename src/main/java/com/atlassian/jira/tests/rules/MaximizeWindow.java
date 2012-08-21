package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import javax.inject.Inject;

public class MaximizeWindow extends TestWatcher {

	private final JiraTestedProduct jira;

	@Inject
	public MaximizeWindow(JiraTestedProduct jira) {
		this.jira = jira;
	}

	@Override
	protected void starting(Description description) {
		super.starting(description);

		final AtlassianWebDriver driver = jira.getTester().getDriver();

		driver.manage().window().setPosition(new Point(0,0));
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		Dimension dim = new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight());
		driver.manage().window().setSize(dim);
	}
}
