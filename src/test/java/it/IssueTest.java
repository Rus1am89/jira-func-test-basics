package it;

import com.atlassian.jira.functest.framework.BaseJiraFuncTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IssueTest extends BaseJiraFuncTest {

    @Before
    public void initTest() {
        backdoor.project().addProject("mytestproject", "TEST", "admin");
        backdoor.issues().createIssue("TEST", "test issue");
    }

    @Test
    public void goToIssueTest() {
        navigation.login("admin");
        navigation.issueNavigator().createSearch("project = TEST");
        tester.assertTextPresent("TEST-1");
    }

    @After
    public void cleanupTest() {
        backdoor.project().deleteProject("TP");
    }

}
