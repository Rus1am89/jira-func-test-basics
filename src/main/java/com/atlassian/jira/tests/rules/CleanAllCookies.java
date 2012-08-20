package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import javax.inject.Inject;

public class CleanAllCookies extends TestWatcher {

	private final JiraTestedProduct jira;

	@Inject
	public CleanAllCookies(JiraTestedProduct jira) {
		this.jira = jira;
	}

	@Override
	protected void starting(Description description) {
		super.starting(description);

		jira.getTester().getDriver().manage().deleteAllCookies();
	}
}
