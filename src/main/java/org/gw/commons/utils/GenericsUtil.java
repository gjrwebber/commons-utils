package org.gw.commons.utils;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericsUtil {

    /**
     * Returns the generic types of the given {@link Class} or throws an
     * {@link IllegalStateException} if none are available
     *
     * @param clazz    The {@link Class} to inspect.
     * @param required The number of required types. If this number of generics are
     *                 not found an {@link IllegalStateException} is thrown
     * @return Returns the generic types of the given {@link Class} or throws an
     * {@link IllegalStateException} if none are available or null if
     * none are available
     * @throws IllegalStateException If the given number of required generics aren't found. Or
     *                               generic type could not be determined.
     */
    public static <T> Class<?>[] getGenericTypes(Class<T> clazz, int required) {
        Type type = clazz.getGenericSuperclass();

        while (type != null && type instanceof Class<?>) {
            type = ((Class<?>) type).getGenericSuperclass();
        }
        if (type != null && type instanceof ParameterizedType) {
            ParameterizedType paramType = (ParameterizedType) type;
            Type[] genericTypes = paramType.getActualTypeArguments();
            if (genericTypes == null || genericTypes.length != required) {
                throw new IllegalStateException(
                        clazz.getSimpleName()
                                + " does not provide a generic type as required. Wanted "
                                + required + " generic types, but found "
                                + genericTypes.length);
            }
            Class<?>[] generics = new Class<?>[genericTypes.length];
            for (int i = 0; i < generics.length; i++) {
                if (genericTypes[i] instanceof GenericArrayType) {
                    GenericArrayType arrayType = (GenericArrayType) genericTypes[i];
                    Class<?> arrayClassType = (Class<?>) arrayType
                            .getGenericComponentType();
                    generics[i] = Array.newInstance(arrayClassType, 1)
                            .getClass();
                } else {
                    generics[i] = (Class<?>) genericTypes[i];
                }
            }
            return generics;
        }
        throw new IllegalStateException(
                "Could not determine generic types for " + clazz.getName());
    }

    /**
     * Returns the generic type of the given {@link Class} or throws an
     * {@link IllegalStateException} if none are available
     *
     * @param clazz The {@link Class} to inspect.
     * @return Returns the generic type of the given {@link Class} or throws an
     * {@link IllegalStateException} if none are available
     * @throws IllegalStateException If no generic type is found. Or generic type could not be
     *                               determined.
     */
    public static <T> Class<?> getGenericType(Class<T> clazz) {
        return getGenericTypes(clazz, 1)[0];
    }
}
