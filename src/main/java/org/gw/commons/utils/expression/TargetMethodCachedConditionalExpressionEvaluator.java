/**
 * ConditionalEvaluationExpression.java (c) Copyright 2013 Gman
 */
package org.gw.commons.utils.expression;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spel evaluator which evaluates expressions on a
 * {@link TargetMethodEvaluationRootObject}. This object holds the details of a
 * method signature like param names which are discovered using a
 * {@link LocalVariableTableParameterNameDiscoverer}.
 * <p/>
 * The expressions and target methods on which the evaluator will run are
 * cached.
 *
 * @author gman
 * @version 1.0
 * @since 1.0
 */
public class TargetMethodCachedConditionalExpressionEvaluator {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    /**
     * Shared {@link ParameterNameDiscoverer} as the
     * {@link LocalVariableTableParameterNameDiscoverer} caches data
     */
    private ParameterNameDiscoverer paramNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * Caches the conditional expressions
     */
    private Map<String, Expression> expressionCache = new ConcurrentHashMap<String, Expression>();

    /**
     * Caches the target {@link Method}
     */
    private Map<String, Method> targetMethodCache = new ConcurrentHashMap<String, Method>();

    /**
     * Create an {@link TargetMethodNamedParameterEvaluationContext} which evaluates a
     * {@link TargetMethodEvaluationRootObject}.
     *
     * @param method      the method being evaluated
     * @param args        the method arguments being evaluated
     * @param target      the target object being evaluated
     * @param targetClass the target class being evaluated
     * @return the {@link TargetMethodNamedParameterEvaluationContext}
     */
    private TargetMethodNamedParameterEvaluationContext createEvaluationContext(
            Method method, Object[] args, Object target, Class<?> targetClass,
            Object result) {
        TargetMethodEvaluationRootObject rootObject = new TargetMethodEvaluationRootObject(
                method, args, target, targetClass);
        TargetMethodNamedParameterEvaluationContext evaluationContext = new TargetMethodNamedParameterEvaluationContext(
                rootObject, this.paramNameDiscoverer, method, args,
                targetClass, this.targetMethodCache);
        if (result != null) {
            evaluationContext.setVariable("result", result);
        }
        return evaluationContext;
    }

    /**
     * Creates a {@link TargetMethodNamedParameterEvaluationContext} with the given
     * arguments, grabs the cached {@link Expression} and evaluates it.
     *
     * @param expression
     * @param method
     * @param args
     * @param target
     * @param targetClass
     * @param result
     * @return True if the expression passes.
     */
    public boolean evaluate(String expression, Method method, Object[] args,
                            Object target, Class<?> targetClass, Object result) {
        TargetMethodNamedParameterEvaluationContext evaluationContext = createEvaluationContext(
                method, args, target, targetClass, result);
        return getExpression(method, expression).getValue(evaluationContext,
                boolean.class);
    }

    /**
     * Creates a {@link TargetMethodNamedParameterEvaluationContext} with the given
     * arguments, grabs the cached {@link Expression} and returns the Object from it.
     *
     * @param expression
     * @param method
     * @param args
     * @param target
     * @param targetClass
     * @param result
     * @return The Object associated with the expression
     */
    public Object getObject(String expression, Method method, Object[] args, Object target, Class<?> targetClass, Object result) {
        TargetMethodNamedParameterEvaluationContext evaluationContext = createEvaluationContext(
                method, args, target, targetClass, result);
        return getExpression(method, expression).getValue(evaluationContext,
                Object.class);
    }

    /**
     * Returns an {@link Expression} for the given {@link Method} and expression
     * {@link String}. This will return the {@link Expression} from the cache if
     * it exists, and will add it if it does not.
     *
     * @param expression
     * @param method
     * @return The {@link org.springframework.expression.Expression} mapped to the given Method and SpEL expression
     */
    private Expression getExpression(Method method, String expression) {
        String key = generateKey(method, expression);
        Expression rtn = expressionCache.get(key);
        if (rtn == null) {
            rtn = this.parser.parseExpression(expression);
            expressionCache.put(key, rtn);
        }
        return rtn;
    }

    /**
     * Generates a unique key for the given {@link Method} and expression
     * {@link String}. It takes the form:
     * <p/>
     * com.test.DeclaringClass#methodString#expression
     *
     * @param method     The {@link Method}
     * @param expression The expression {@link String}
     * @return A unique key for the given {@link Method} and expression
     * {@link String}
     */
    private String generateKey(Method method, String expression) {
        StringBuilder sb = new StringBuilder();
        sb.append(method.getDeclaringClass().getName());
        sb.append("#");
        sb.append(method.toString());
        sb.append("#");
        sb.append(expression);
        return sb.toString();
    }
}
