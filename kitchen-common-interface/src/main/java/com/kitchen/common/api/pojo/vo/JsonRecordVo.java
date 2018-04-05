package com.kitchen.common.api.pojo.vo;

import java.util.List;

/**
 * 视图层返回json格式集合的容器类（即Controller中接口的返回类型）
 * 所有通过json进行返回数据的对象均需要放到此类的records属性中
 *
 * @date 2017-04-28
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class JsonRecordVo<T extends Object> extends BaseJsonVo {
    /**
     * 序列化标识
     */
    private static final long serialVersionUID = -2104776899007974599L;

    /**
     * 返回的数据
     * @records属性可以是继承于java.util.List的任意对象类型，例如：
     * 可以是一个ArrayList、Vector等Java框架中自带的数据结构类型；
     */
    private List<T> records;

    /**
     * 当前页码
     */
    private Integer pageNo;

    /**
     * 每页记录数
     */
    private Integer pageSize;

    /**
     * 总条目数量
     */
    private Integer total;

    /**
     * 总页数
     */
    private Integer pages;

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }
}
