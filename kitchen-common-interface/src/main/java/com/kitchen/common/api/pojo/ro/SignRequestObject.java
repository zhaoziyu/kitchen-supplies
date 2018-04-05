package com.kitchen.common.api.pojo.ro;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-08-23
 */
public class SignRequestObject<T extends Object> extends BaseRequestObject {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = 5553162774361727861L;

    /**
     * 请求的业务参数
     */
    private T content;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }
}
