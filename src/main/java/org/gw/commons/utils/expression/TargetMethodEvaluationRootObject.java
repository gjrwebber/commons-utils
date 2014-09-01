/**
 * ConditionalEvaluationObject.java (c) Copyright 2013 Gman
 */
package org.gw.commons.utils.expression;

import org.springframework.util.Assert;

import java.lang.reflect.Method;

/**
 * Root object to be evaluated by the
 * {@link TargetMethodCachedConditionalExpressionEvaluator}
 *
 * @author gman
 * @version 1.0
 * @since 1.0
 */
public class TargetMethodEvaluationRootObject {

    private final Method method;

    private final Object[] args;

    private final Object target;

    private final Class<?> targetClass;

    public TargetMethodEvaluationRootObject(Method method, Object[] args,
                                            Object target, Class<?> targetClass) {

        Assert.notNull(method, "Method is required");
        Assert.notNull(targetClass, "targetClass is required");
        this.method = method;
        this.target = target;
        this.targetClass = targetClass;
        this.args = args;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getMethodName() {
        return this.method.getName();
    }

    public Object[] getArgs() {
        return this.args;
    }

    public Object getTarget() {
        return this.target;
    }

    public Class<?> getTargetClass() {
        return this.targetClass;
    }

}
