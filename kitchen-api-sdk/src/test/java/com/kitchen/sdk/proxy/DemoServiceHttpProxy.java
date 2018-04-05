package com.kitchen.sdk.proxy;

/**
 * HTTP请求代理示例
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class DemoServiceHttpProxy extends BaseHttpProxy {
    private DemoService demoService;

    public DemoServiceHttpProxy() {
        demoService = this.createApiInstance(DemoService.class);
    }

    public DemoService getDemoService() {
        return this.demoService;
    }
}
