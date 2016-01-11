package com.atlassian.jira.tests.rules;

import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.jira.rest.client.api.domain.ServerInfo;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.jira.tests.annotations.JiraBuildNumberDependent;
import com.google.common.base.Function;
import com.google.common.util.concurrent.ListenableFuture;
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import com.atlassian.jira.rest.client.auth.AnonymousAuthenticationHandler;
import java.net.URI;
import java.util.concurrent.ExecutionException;

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
		ListenableFuture<Integer> future = new AsynchronousJiraRestClientFactory().create(URI.create(baseUrl), new AnonymousAuthenticationHandler())
			.getMetadataClient().getServerInfo().map(new Function<ServerInfo, Integer>(){
				public Integer apply(ServerInfo s) {
					return s.getBuildNumber();
				}
			});

		long result = 0;
		try {
			result = Long.valueOf(future.get());
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			System.err.println("Interrupted during JiraBuildNumberRule.getBuildNumber()");
		} catch (ExecutionException e) {
			System.err.println("ExecutionException in JiraBuildNumberRule.getBuildNumber(): " + e.getCause());
		}

		return result;
	}
}