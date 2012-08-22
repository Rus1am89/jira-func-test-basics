/*
 * Copyright (C) 2012 Atlassian
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.atlassian.jira.tests;

import com.atlassian.jira.functest.framework.backdoor.Backdoor;
import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.jira.tests.rules.CleanAllCookies;
import com.atlassian.jira.tests.rules.DirtyWarningTerminatorRule;
import com.atlassian.jira.tests.rules.MaximizeWindow;
import com.atlassian.jira.tests.rules.WebDriverScreenshot;
import com.atlassian.pageobjects.PageBinder;
import org.junit.ClassRule;
import org.junit.Rule;

public abstract class TestBase {
	@Rule
	public CleanAllCookies cleanCookies = new CleanAllCookies(jira());

	@Rule
	public MaximizeWindow maximizeWindow = new MaximizeWindow(jira());

	@Rule
	public WebDriverScreenshot $screenshot = new WebDriverScreenshot(jira());

	@Rule
	public DirtyWarningTerminatorRule dirtyWarning = new DirtyWarningTerminatorRule(jira());

	@ClassRule
	public static FuncTestHelper funcTestHelper = new FuncTestHelper();

	@ClassRule
	public static JiraTestedProductHelper productHelper = new JiraTestedProductHelper();

	protected static JiraTestedProduct jira() {
		return productHelper.jira();
	}

	protected static PageBinder pageBinder() {
		return jira().getPageBinder();
	}

	protected static Backdoor backdoor() {
		return funcTestHelper.backdoor;
	}
}
