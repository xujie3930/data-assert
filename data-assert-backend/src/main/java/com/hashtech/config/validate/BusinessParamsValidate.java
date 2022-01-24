package com.hashtech.config.validate;

import javax.validation.groups.Default;
import java.lang.annotation.*;

/**
 * @author liyanhui
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessParamsValidate {

    int[] argsIndexs() default {0};


    Class<?>[] groups() default {Default.class};
}
