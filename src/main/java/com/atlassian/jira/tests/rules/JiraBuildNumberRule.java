package com.atlassian.jira.tests.rules;

import com.atlassian.jira.functest.framework.Administration;
import com.atlassian.jira.tests.annotations.JiraBuildNumberDependent;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import javax.inject.Inject;

/**
 *
 * @since v5.2
 */
public class JiraBuildNumberRule implements TestRule {

	private final Administration jira;

	@Inject
	public JiraBuildNumberRule(Administration jira)
	{
		this.jira = jira;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		JiraBuildNumberDependent annotation = description.getAnnotation(JiraBuildNumberDependent.class);

		if (annotation != null) {
			long actualBuildNumber = jira.getBuildNumber();
			if(annotation.condition().fulfillsCondition(annotation.value(), actualBuildNumber)) {
				return base;
			}
		}

		return new Statement() {
			@Override
			public void evaluate() throws Throwable {
				// Return an empty Statement object for those tests
			}
		};
	}
}
