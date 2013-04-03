# JIRA Func Test Basics

This plugin was created to make it easier to write integration tests that are compiled and run against different versions of JIRA.
We use it internally with JIRA Importers Plugin, JIRA Issue Collector Plugin, JIRA Remote Copy Plugin and many more. Now you can use it too!

## Using JIRA Func Test Basics

Func Test Basics combined with TestKit gives you a new way to run your tests. The biggest advantage is that they are proven to work across different JIRA versions.
You can use the same Func Test Basics to run tests against 5.0.2, 5.1, 5.2, and 6.0.

### How to start


### How to write tests

#### Using Rules without extending TestBase

Here's an example of an integration test using three JIRA instances at once. It will execute tests in the browser using Atlassian Selenium.

TestedProductFactory comes from JIRA PageObjects, it offers API to drive the browser and access JIRA pages. Beware most of the pages change from version to version.
So if your product heavily integrates with JIRA you may need to write your own versions. We use it mostly to log in the user during tests, then we use our own PageObjects to test the plugin.

Backdoor is [TestKit backdoor|https://bitbucket.org/atlassian/jira-testkit] that allows you to setup JIRA for tests (create users, issues, groups, workflows, etc.).

Here's couple of rules we use to setup test environment:

* EmptySystemDashboardRule - when you log in the user with PageObjects it will be redirected to Dashboard. We empty the Dashboard first so it loads instantly (no need to wait for rendering gadgets)
* SessionCleanupRule - this rule deletes session cookie after each test case, so you end up with a fresh session
* MaximizeWindow - makes Selenium browser full size so everything fits on screen and Selenium doesn't have to scroll the page which sometimes is flaky
* WebSudoRule - disable WebSudo to speed up tests (use it if you're testing Administration pages)
* WebDriverScreenshot - produces screenshot after each failed test
* DrityWarningTerminatorRule - JIRA doesn't allow you to leave a dirty form, which is fine for the user, but we don't want that during integration tests

```java
public abstract class AbstractCopyIssueTest
{
    static JiraTestedProduct jira1 = TestedProductFactory.create(JiraTestedProduct.class, new DefaultProductInstance("http://localhost:2990/jira", "jira1", 2990, "/jira"), null);
    static JiraTestedProduct jira2 = TestedProductFactory.create(JiraTestedProduct.class, new DefaultProductInstance("http://localhost:2991/jira", "jira2", 2991, "/jira"), null);
    static JiraTestedProduct jira3 = TestedProductFactory.create(JiraTestedProduct.class, new DefaultProductInstance("http://localhost:2992/jira", "jira3", 2992, "/jira"), null);

	static Backdoor testkit1 = getBackdoor(jira1);
	static Backdoor testkit2 = getBackdoor(jira2);
	static Backdoor testkit3 = getBackdoor(jira3);

	@ClassRule
	public static EmptySystemDashboardRule emptySystemDashboardRule = new EmptySystemDashboardRule(testkit1, testkit2, testkit3);

	@Rule
	public static SessionCleanupRule sessionCleanupRule = new SessionCleanupRule();
	@Rule
	public static MaximizeWindow maximizeWindow = new MaximizeWindow();
	@Rule
	public WebSudoRule webSudoRule = new WebSudoRule(testkit1, testkit2, testkit3);
	@Rule
	public static WebDriverScreenshot webDriverScreenshot = new WebDriverScreenshot();
	@Rule
	public static DirtyWarningTerminatorRule dirtyWarningTerminatorRule = new DirtyWarningTerminatorRule();

	public static Backdoor getBackdoor(JiraTestedProduct jira) {
		final TestKitLocalEnvironmentData testKitLocalEnvironmentData = getTestKitLocalEnvironmentData(jira);
		return new Backdoor(testKitLocalEnvironmentData);
	}

	private static TestKitLocalEnvironmentData getTestKitLocalEnvironmentData(JiraTestedProduct jira) {
		Properties props = new Properties();
		props.put("jira.port", Integer.toString(jira.getProductInstance().getHttpPort()));
		props.put("jira.context", jira.getProductInstance().getContextPath());
		props.put("jira.host", URI.create(jira.getProductInstance().getBaseUrl()).getHost());
		props.put("jira.xml.data.location", ".");
		return new TestKitLocalEnvironmentData(props, null);
	}
```