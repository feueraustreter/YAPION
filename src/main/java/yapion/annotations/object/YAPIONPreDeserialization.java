// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020 yoyosource

package yapion.annotations.object;

import org.objenesis.ObjenesisBase;
import yapion.serializing.YAPIONDeserializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation describes one of the four steps in the process
 * of deserialization. You can use this annotation for a method
 * that defaults the data or object before deserialized by YAPION.
 * This can be useful to evaluate {@code null} pointer to their
 * default value or to set default values. This annotation is useful
 * if you use {@link YAPIONData} or {@link YAPIONObjenesis} as annotations.
 *
 * <br><br>The four Steps are:
 * <br>- Constructing the object (either by using the Constructor or {@link ObjenesisBase})
 * <br>- PreDeserialization method call
 * <br>- Deserializing all fields ({@link YAPIONDeserializer})
 * <br>- PostDeserialization method call
 *
 * <br><br>The context describes the state in which the {@link YAPIONDeserializer}
 * should be for this annotation to take effect.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface YAPIONPreDeserialization {
    String[] context() default {};
}