package com.kitchen.sdk.proxy;

import com.kitchen.sdk.sign.KitSignConstant;
import com.kitchen.sdk.sign.rsa.KitRSACheck;
import com.kitchen.sdk.sign.rsa.KitRSASign;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-01
 */
@Ignore
public class TestSign {
    private static final String privateKeyPKCS8 = "MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAMrs2I8xeDi1fVlxulwsUGLb+ru5XiFU7xbR3TWDt4CpLWUOZ21kA1VRP9A97qDQUtFkTa/mLtOFY0RXEUN6o2VphfngwQhFjoNkt6OaGqetItd0OVuDzfTwDUZOgdgb6F0n9plbFGgM08z4CbJDAeqfmcBLr7HeMhPn/CxSLI4rAgMBAAECgYEAhNIhrRgkKHoiYbke0dXvh8WUh+r8wbRmCfmzYKOmKICTReH3sJIV3HIh4pum5Xt1ubh9YPS5EZntL34Nvq025K3uU1foTbZ/ZKKaeGlBRGUmWTQ0fKBJfP07gzl+RLPdVovngLCE2NsC9J9nDSlaDW/uLsr0pBdtmpRVJIAfEwECQQD9fWc8mlowlBjtevwjiXZaUk8jjHV85FbM67xUcwJRkyOO/2aiFHoy9KU8PKXXIP4iwT90JILcFHVcE6VfUTfZAkEAzO9C32D9JvJsjffWhBGKeEbZs83ul6JOLLl0p0MTv6BPmYzYoXoYoUXmsWsx8f6UY6nRYNVJe0c9VFYwT42XowJBAMFbFJJPkpgXf2Q2OXnJ8vM2cZNGIqAfdG5fKoqoJ46d07PR8TGUuRmBL0DhagbM7c0I5yPqbb7+A/82JSCtzRECQQCVNhvFktOfWOErGOmKrU7ZthRqcyUmKJmsvLcv1Yn6exmZ3lAYelKWkdOfGEJ8RPT7/7ggPgtLhBomXr68HYbrAkEAtH+/kCjWQGsQkUXFelkb0TBbbubiU1tAxPPBHzPmDz7GhRnHE/y75JpeSw8sYCb6XVnLufrPNYZN/DlJibWtQA==";
    private static final String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK7NiPMXg4tX1ZcbpcLFBi2/q7uV4hVO8W0d01g7eAqS1lDmdtZANVUT/QPe6g0FLRZE2v5i7ThWNEVxFDeqNlaYX54MEIRY6DZLejmhqnrSLXdDlbg8308A1GToHYG+hdJ/aZWxRoDNPM+AmyQwHqn5nAS6+x3jIT5/wsUiyOKwIDAQAB";

    @Test
    public void testSignAndCheck() throws Exception {
        String content = "{'biz_code':'SUCCESS','biz_msg':','code':'00000','msg':','page_no':0,'page_size':0,'pages':0,'records':[{'category_id':9,'category_name':'视频点播','icon_url':'http://192.168.10.83:9000/resources/cotegory/icon/82d74ccd345046dba3a717812ac9f0dd1视频点播.png','index':1,'parent_category':1},{'category_id':10,'category_name':'电视直播','icon_url':'http://192.168.10.83:9000/resources/cotegory/icon/c2ccc4b7311d45c38d5d7b45dabc828b2电视直播.png','index':2,'parent_category':1},{'category_id':11,'category_name':'音乐特色','icon_url':'http://192.168.10.83:9000/resources/cotegory/icon/e70300fe394641098930f4f16b8a036b3音乐特色.png','index':3,'parent_category':1}],'sign':','sign_type':','success':true,'total':0}";

        long start = System.currentTimeMillis();
        String sign = KitRSASign.rsaSign(content, privateKeyPKCS8, KitSignConstant.SIGN_CHARSET_UTF8, KitSignConstant.SIGN_TYPE_RSA);
        long end = System.currentTimeMillis();
        System.err.println("生成签名：" + sign + "    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        boolean pass = KitRSACheck.rsaCheck(content, sign, publicKey, KitSignConstant.SIGN_CHARSET_UTF8, KitSignConstant.SIGN_TYPE_RSA);
        end = System.currentTimeMillis();
        System.err.println("验证签名：" + pass + "    用时：" + (end - start) + "ms");

        assert pass;
    }
}
