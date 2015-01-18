package com.atlassian.jira.tests.rules;

import com.atlassian.jira.functest.framework.util.junit.AnnotatedDescription;
import com.atlassian.jira.pageobjects.JiraTestedProduct;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import javax.annotation.Nonnull;

/**
 * Disables Onboarding in JIRA, unless the {@link com.atlassian.jira.tests.rules.EnableOnboarding} annotation is
 * present for given test.
 *
 * @since 6.4
 */
public class OnboardingRule extends TestWatcher
{
    static final String DARK_FEATURE_DISABLE_ONBOARDING_FLAG = "jira.onboarding.feature.disabled";

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
            jira.backdoor().darkFeatures().disableForSite(DARK_FEATURE_DISABLE_ONBOARDING_FLAG);

        }
        else
        {
            // default
            jira.backdoor().darkFeatures().enableForSite(DARK_FEATURE_DISABLE_ONBOARDING_FLAG);
        }
    }

}
