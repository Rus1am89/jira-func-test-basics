package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.jira.rest.client.NullProgressMonitor;
import com.atlassian.jira.rest.client.internal.jersey.JerseyJiraRestClientFactory;
import com.atlassian.jira.tests.annotations.JiraBuildNumberDependent;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;

import java.net.URI;

/**
 *
 * @since v5.2
 */
public class JiraBuildNumberRule implements TestRule {

	private final String baseUrl;

	public JiraBuildNumberRule(JiraTestedProduct jira) {
		this(jira.getProductInstance().getBaseUrl());
	}

	public JiraBuildNumberRule(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	@Override
	public Statement apply(Statement base, Description description) {
		JiraBuildNumberDependent annotation = description.getAnnotation(JiraBuildNumberDependent.class);

		if (annotation != null) {
			long actualBuildNumber = getBuildNumber();
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

	public long getBuildNumber() {
		return new JerseyJiraRestClientFactory().create(URI.create(baseUrl), new AnonymousAuthenticationHandler()).getMetadataClient().getServerInfo(new NullProgressMonitor()).getBuildNumber();
	}
}
