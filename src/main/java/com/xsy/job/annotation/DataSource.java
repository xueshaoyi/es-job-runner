package com.xsy.job.annotation;

import org.apache.ibatis.annotations.Mapper;

import java.lang.annotation.*;

/**
 * @author xueshaoyi
 * @Date 2020/7/26 下午9:52
 **/

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PACKAGE, ElementType.TYPE, ElementType.METHOD})
@Mapper
public @interface DataSource {
	String value() default "";

	String[] sources() default {};
}
