package com.arslankucukkafa.labormarketauth.action;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Permissionable {
    PermissionType permissionType() default PermissionType.FOR_EVERYONE;
}