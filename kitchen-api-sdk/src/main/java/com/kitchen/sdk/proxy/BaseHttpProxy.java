package com.kitchen.sdk.proxy;

import com.kitchen.sdk.SDKGlobalSetting;
import com.kitchen.sdk.converter.FastJsonConverterFactory;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * HTTP请求代理的基类，通过HTTP方式访问的业务的代理类需继承此类
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public abstract class BaseHttpProxy {
    protected Retrofit retrofit;

    protected BaseHttpProxy() {
        // 默认使用HTTP
        this(SDKGlobalSetting.getHttpRootPath(), null);
    }

    protected BaseHttpProxy(String rootPath) {
        this(rootPath, null);
    }
    protected BaseHttpProxy(String rootPath, Converter.Factory converterFactory) {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(rootPath);

        // 默认使用RxJava2
        //builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        if (converterFactory != null) {
            builder.addConverterFactory(converterFactory);
        } else {
            // 默认使用FastJson
            builder.addConverterFactory(FastJsonConverterFactory.create());
        }

        retrofit = builder.build();
    }

    protected <T> T createApiInstance(Class<T> cls) {
        return this.retrofit.create(cls);
    }

}
