package com.kitchen.sdk.proxy;

import com.kitchen.sdk.SDKGlobalSetting;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - zhaoziyu@inspur.com
 * @date 2017-07-13
 */
public class DemoServiceHttpProxyTest {

    private void initConfig() {
        String httpRootPath = "";
        String httpsRootPath = "";
        String signId = "";
        String devPrivateKey = "";
        String serverPublicKey = "";
        SDKGlobalSetting.init(httpRootPath, httpsRootPath, signId, devPrivateKey, serverPublicKey);
    }

    // 登录
    private String login() throws Exception {
        initConfig();
        /*DemoServiceHttpProxy proxy = new DemoServiceHttpProxy();
        SignRequestObject<DemoPerson> params = new SignRequestObject<>();
        SDKSignUtils.initSignRequestObj(params);

        DemoPerson demoPerson = new DemoPerson();
        demoPerson.setId("zhaozy");
        params.setContent(demoPerson);

        // 签名
        params.setSign(SDKSignUtils.sign(params));

        // 调用
        Call<JsonObjectVo<Object>> callResult = proxy.getDemoService().testLogin(params);
        JsonObjectVo<Object> result = callResult.execute().body();

        // 验签
        if (SDKSignUtils.checkSign(result)) {
            String strResult = result.isSuccess() + result.getCode() + result.getBizCode();
            System.out.println(strResult);
        } else {
            System.out.println("签名验证失败");
        }

        System.out.println("Token:" + result.getData());
        return (String) result.getData();*/

        return null;
    }

    @Ignore
    @Test
    public void testTokenCall() throws Exception {
        initConfig();
        /*String token = this.login();

        if (token == null) {
            System.out.println("未设置Token");
            return;
        }
        DemoServiceHttpProxy proxy = new DemoServiceHttpProxy();

        TokenRequestObject<Object> params = new TokenRequestObject<>();
        SDKSignUtils.initSignRequestObj(params);

        params.setAuth_token(token);
        params.setUser_id("zhaozy");

        DemoPerson demoPerson = new DemoPerson();
        demoPerson.setId("zhaozy");
        demoPerson.setName("赵梓彧");
        params.setContent(demoPerson);

        // 签名
        params.setSign(SDKSignUtils.sign(params));

        // 调用
        Call<JsonObjectVo<Object>> callResult = proxy.getDemoService().testToken(params);
        JsonObjectVo<Object> result = callResult.execute().body();

        // 验签
        if (SDKSignUtils.checkSign(result)) {
            String strResult = result.isSuccess() + result.getCode() + result.getBizCode();
            System.out.println(strResult);
        } else {
            System.out.println("签名验证失败");
        }*/
    }

    @Ignore
    @Test
    public void test1() throws Exception {
        initConfig();
        /*DemoServiceHttpProxy proxy = new DemoServiceHttpProxy();

        // 赋值
        SignRequestObject<DemoPerson> params = new SignRequestObject<>();
        SDKSignUtils.initSignRequestObj(params);

        DemoPerson demoPerson = new DemoPerson();
        demoPerson.setName("赵梓彧");
        demoPerson.setPhone("");
        params.setContent(demoPerson);

        // 签名
        params.setSign(SDKSignUtils.sign(params));

        // 调用
        Call<JsonObjectVo<Object>> callResult = proxy.getDemoService().testSign(params);
        JsonObjectVo<Object> result = callResult.execute().body();

        // 验签
        if (SDKSignUtils.checkSign(result)) {
            String strResult = result.isSuccess() + result.getCode() + result.getBizCode();
            System.out.println(strResult);
        } else {
            System.out.println("签名验证失败");
        }*/

    }


}