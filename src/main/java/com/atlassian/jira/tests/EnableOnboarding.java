package com.atlassian.jira.tests;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * By default onboarding in JIRA will be disabled before each test. Mark test method or class with this annotation to
 * enable onboarding for given test methods.
 *
 * @since v6.4
 */
@Retention (RetentionPolicy.RUNTIME)
@Target ( { ElementType.TYPE, ElementType.METHOD })
public @interface EnableOnboarding
{
}
