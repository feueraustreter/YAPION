// SPDX-License-Identifier: Apache-2.0
// YAPION
// Copyright (C) 2019,2020 yoyosource

package yapion.annotations.serialize;

import yapion.serializing.YAPIONSerializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation describes a Field or Type to be excluded in the process
 * of serialization. Excluding means that the Field or Type annotated by
 * this annotation will not be written into the YAPION object. If this
 * annotation annotates a Type it is considered as a class description.
 *
 * <br><br>The context describes the state in which the {@link YAPIONSerializer}
 * should be for this annotation to take effect.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface YAPIONSaveExclude {
    String[] context() default {};
}