package com.kitchen.common.api.pojo.vo;

/**
 * 视图层返回json格式对象的容器类（即Controller中接口的返回类型）
 * 所有通过json进行返回数据的对象均需要放到此类的result属性中
 *
 * @date 2016-09-04
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class JsonObjectVo<T extends Object> extends BaseJsonVo {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 8141800867521702858L;

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
