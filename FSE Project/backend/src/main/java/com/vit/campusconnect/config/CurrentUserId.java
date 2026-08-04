package com.vit.campusconnect.config;

import java.lang.annotation.*;

/**
 * Annotation to inject the currently authenticated user's ID into controller method parameters.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUserId {
}
