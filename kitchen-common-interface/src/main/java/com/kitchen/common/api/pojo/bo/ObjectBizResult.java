package com.kitchen.common.api.pojo.bo;

/**
 * 业务逻辑层返回结果——对象类型
 *
 * @date 2017-05-17
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class ObjectBizResult<T extends Object> extends BaseBizResult {

    private static final long serialVersionUID = 8012044153202383787L;

    /**
     * 返回的数据
     * @data属性可以是继承于Java底层Object的任意对象类型，例如：
     * 可以是一个String、List、HashMap等Java框架中自带的数据结构类型；
     * 可以是自定义的一个包含多类型、多属性的领域模型；
     */
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
