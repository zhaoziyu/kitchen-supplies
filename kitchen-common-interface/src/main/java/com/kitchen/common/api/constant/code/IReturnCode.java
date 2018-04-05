package com.kitchen.common.api.constant.code;

/**
 * 返回码枚举类需要继承此接口，并在枚举（非枚举实例）中实现此接口中的方法
 * 以便于BaseJsonVo中使用枚举设置相关返回值
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-19
 */
public interface IReturnCode {
    String getCode();
    String getMsg();
    String getBizCode();
    String getBizMsg();
}
