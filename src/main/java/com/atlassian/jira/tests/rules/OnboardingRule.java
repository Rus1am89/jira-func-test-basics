package com.atlassian.jira.tests.rules;

import com.atlassian.jira.functest.framework.util.junit.AnnotatedDescription;
import com.atlassian.jira.pageobjects.JiraTestedProduct;
import com.atlassian.jira.tests.EnableOnboarding;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import javax.annotation.Nonnull;

/**
 * Disables Onboarding in JIRA, unless the {@link com.atlassian.jira.tests.EnableOnboarding} annotation is
 * present for given test.
 *
 * @since 6.4
 */
public class OnboardingRule extends TestWatcher
{
    private JiraTestedProduct jira;

    public OnboardingRule(JiraTestedProduct jira) {
        this.jira = jira;
    }

    @Override
    protected void starting(@Nonnull final Description description)
    {
        final AnnotatedDescription annotatedDescription = new AnnotatedDescription(description);
        if (annotatedDescription.hasAnnotation(EnableOnboarding.class))
        {
            jira.backdoor().darkFeatures().disableForSite("jira.onboarding.feature.disabled");

        }
        else
        {
            // default
            jira.backdoor().darkFeatures().enableForSite("jira.onboarding.feature.disabled");
        }
    }

}
