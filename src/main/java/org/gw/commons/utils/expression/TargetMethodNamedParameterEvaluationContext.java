/**
 * ConditionalParameterEvaluationContext.java (c) Copyright 2013 Gman
 */
package org.gw.commons.utils.expression;

import org.springframework.aop.support.AopUtils;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * A {@link StandardEvaluationContext} which provides the method parameter names
 * as variables in the context. This is done lazily and uses a
 * {@link ParameterNameDiscoverer} that caches the parameter names for future
 * use.
 *
 * @author gman
 * @version 1.0
 * @since 1.0
 */
public class TargetMethodNamedParameterEvaluationContext
        extends
        StandardEvaluationContext {

    private final ParameterNameDiscoverer paramDiscoverer;

    private final Method method;

    private final Object[] args;

    private final Class<?> targetClass;

    private final Map<String, Method> methodCache;

    private boolean paramLoaded = false;

    TargetMethodNamedParameterEvaluationContext(Object rootObject,
                                                ParameterNameDiscoverer paramDiscoverer, Method method,
                                                Object[] args, Class<?> targetClass, Map<String, Method> methodCache) {
        super(rootObject);

        this.paramDiscoverer = paramDiscoverer;
        this.method = method;
        this.args = args;
        this.targetClass = targetClass;
        this.methodCache = methodCache;
    }

    public Method getMethod() {
        return method;
    }

    public Object[] getArgs() {
        return args;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    /**
     * Load the param information only when needed.
     */
    @Override
    public Object lookupVariable(String name) {
        Object variable = super.lookupVariable(name);
        if (variable != null) {
            return variable;
        }
        if (!this.paramLoaded) {
            loadArgsAsVariables();
            this.paramLoaded = true;
            variable = super.lookupVariable(name);
        }
        return variable;
    }

    private void loadArgsAsVariables() {
        // shortcut if no args need to be loaded
        if (ObjectUtils.isEmpty(this.args)) {
            return;
        }

        String mKey = toString(this.method);
        Method targetMethod = this.methodCache.get(mKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(this.method,
                    this.targetClass);
            if (targetMethod == null) {
                targetMethod = this.method;
            }
            this.methodCache.put(mKey, targetMethod);
        }

        String[] parameterNames = this.paramDiscoverer
                .getParameterNames(targetMethod);
        // save parameter names (if discovered)
        if (parameterNames != null) {
            for (int i = 0; i < parameterNames.length; i++) {
                setVariable(parameterNames[i], this.args[i]);
            }
        }
    }

    private String toString(Method m) {
        StringBuilder sb = new StringBuilder();
        sb.append(m.getDeclaringClass().getName());
        sb.append("#");
        sb.append(m.toString());
        return sb.toString();
    }
}
