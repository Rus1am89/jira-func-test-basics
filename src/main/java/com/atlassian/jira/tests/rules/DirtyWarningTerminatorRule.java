package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.webdriver.AtlassianWebDriver;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.NoAlertPresentException;

import javax.inject.Inject;

public class DirtyWarningTerminatorRule extends TestWatcher implements TestRule
{
	private AtlassianWebDriver driver;

	@Inject
	public DirtyWarningTerminatorRule(JiraTestedProduct jira)
	{
		this.driver = jira.getTester().getDriver();
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
			driver.getDriver().switchTo().alert().dismiss();
		}
		catch (NoAlertPresentException iDontReallyCare)
		{
		}
	}

	private void preventTheEvil()
	{
		// get out of any IFrame
		driver.getDriver().switchTo().defaultContent();
		// just make it WOOOORK
		driver.executeScript("window.onbeforeunload=null;");
	}
}
