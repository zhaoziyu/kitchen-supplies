package com.kitchen.sdk.proxy;

import com.kitchen.common.api.pojo.ro.SignRequestObject;
import com.kitchen.common.api.pojo.ro.TokenRequestObject;
import com.kitchen.common.api.pojo.vo.JsonObjectVo;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * 请求服务接口定义（示例）
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public interface DemoService {

    /*@POST("demo/sign/login")
    Call<JsonObjectVo<Object>> testLogin(@Body SignRequestObject<DemoPerson> params);

    @POST("demo/sign/test")
    Call<JsonObjectVo<Object>> testSign(@Body SignRequestObject<DemoPerson> params);

    @POST("demo/token/test")
    Call<JsonObjectVo<Object>> testToken(@Body TokenRequestObject<Object> params);*/
}
