package com.kitchen.common.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * 用于标注忽略签名验证的接口
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/19
 */
@Target(ElementType.METHOD)
@Documented
public @interface IgnoreSignVerify {
}
