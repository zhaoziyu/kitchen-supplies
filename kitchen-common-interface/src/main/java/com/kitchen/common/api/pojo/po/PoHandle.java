package com.kitchen.common.api.pojo.po;

import java.io.Serializable;

/**
 * PO对象默认的操作
 * PO对象生成时继承此接口
 *
 * @date 2016-12-28
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface PoHandle<T> extends Serializable {
    /**
     * 深拷贝
     *
     * @param bean
     * @throws CloneNotSupportedException
     */
    void cloneSelf(T bean) throws CloneNotSupportedException;
}
