package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.webdriver.AtlassianWebDriver;
import com.atlassian.webdriver.browsers.WebDriverBrowserAutoInstall;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.NoAlertPresentException;

import javax.inject.Inject;

public class DirtyWarningTerminatorRule extends TestWatcher implements TestRule
{
	private final AtlassianWebDriver driver;

	@Inject
	public DirtyWarningTerminatorRule(JiraTestedProduct jira)
	{
		this.driver = jira.getTester().getDriver();
	}

	public DirtyWarningTerminatorRule() {
		this.driver = WebDriverBrowserAutoInstall.INSTANCE.getDriver();
	}

	@Override
	protected void finished(Description description)
	{
		htfuDirtyWarnings();
	}

	/**
	 * Eat that, dirty warning!
	 *
	 */
	public void htfuDirtyWarnings()
	{
		removeExistingEvil();
		preventTheEvil();
	}

	private void removeExistingEvil()
	{
		try
		{
			driver.switchTo().alert().dismiss();
		}
		catch (NoAlertPresentException iDontReallyCare)
		{
		}
	}

	private void preventTheEvil()
	{
		// get out of any IFrame
		driver.switchTo().defaultContent();
		// just make it WOOOORK
		driver.executeScript("window.onbeforeunload=null;");
	}
}
