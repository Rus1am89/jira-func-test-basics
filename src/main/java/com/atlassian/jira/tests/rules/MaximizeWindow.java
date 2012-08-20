package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

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

		jira.getTester().getDriver().executeScript(
				"if (window.screen){"
						+ "window.moveTo(0, 0);"
						+ "window.resizeTo(window.screen.availWidth,window.screen.availHeight);"
						+ "};");
	}
}
