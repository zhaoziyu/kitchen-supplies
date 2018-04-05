package com.kitchen.common.api.pojo.bo;

import java.util.List;

/**
 * 业务逻辑层返回结果——集合类型
 *
 * @date 2017-05-17
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class RecordBizResult<T extends Object> extends BaseBizResult {

    private static final long serialVersionUID = -3152916389959408474L;

    /**
     * 返回的数据
     * @records属性可以是继承于java.util.List的任意对象类型，例如：
     * 可以是一个ArrayList、Vector等Java框架中自带的数据结构类型；
     */
    private List<T> records;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }
}
