package org.gw.commons.utils;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;


public class GenericsUtilTest {

    @Test
    public void testGetGenericType() {
        Class<?>[] classes = GenericsUtil.getGenericTypes(ClassC.class, 2);
        Assert.assertEquals(2, classes.length);
        Assert.assertEquals(Object.class, classes[0]);
        Assert.assertEquals(Serializable.class, classes[1]);
    }

    private class ClassA<A extends Object, B extends Object, C extends Object> {
    }

    private class ClassB<X extends Object, Y extends Object> extends ClassA<X, Y, Object> {
    }

    private class ClassC extends ClassB<Object, Serializable> {
    }
}
