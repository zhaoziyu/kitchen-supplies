package common.security;

import com.kitchen.market.common.security.sign.key.KitSignTextByKey;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2017-09-05
 */
@Ignore
public class TestTextSignByKey {
    private static String privateKey_PKCS8 = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPb9qeaArrL/HqzKheoz1LSOg1BDwJP9LjPkYQUuVYFkiYmX88dwrfTnYSOIfUEHEPTkgaVGKLBcNVlTAoZvVvK/aVPb8os9I4TUxFvC4Qy+qGv5F6FqJFjBIr+olP4K3SKKJ8Ld+U9vT2Vcxs6PC9PoBvy9Zzn5Ch7tpz5FcKSBAgMBAAECgYEAnI2EV3pRQVu70cI8x4o61IdQbFvFgQgFdRbY+DO6Nt3G39PUzSF64bSXObKV0dXsxYzhMCUcPUz0871N6HBCJzSt9iG+cpGS8X13AIa/uQ9/hFMn0BPyDOvdsL+/kv7Ahq2zHAMTmbJySMO7WCgMzd5O90KPFoJAGNLVXAtz+sECQQD83/bKI68XiepErLMzVIuOVt/WzvrcGOt6z0v1zthRHKdt6+nuhM/jNV1sDeteimgdT77j65qUcjcNMcrxDUUdAkEA+gsViMtoKgDSsMbKhNlLIlAxU/qvNXXwLx0E1Y3zcq+o/yJ4lgYbR3+MZuw8bfqkfparBOdERlrzH8xaG1IztQJAJeECncr0mmkNT5YzDbhXY03+H7ZHe5q8A1xz+3EtlBDfv6Z8Fz+LyHQg92OqYzIGYIWmiYusTxpAxtgzlyIuvQJBAO3AGlK+7iV6MNuruacGIh3XWH/8jhpsMNvrYMxaNBBpnGwz76re1ZNvYSYAHBmKyFwhkS2RZObs1d33ZfoyeD0CQQDzOP5DW/4EvbWc65gEzOnzVc14/7p6MRSRR/lfUYiU1zY1RTgzKylEFee+z/Jqd0IOOpP5dkFdLxHMbrgPZ63K";
    private static String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQD2/anmgK6y/x6syoXqM9S0joNQQ8CT/S4z5GEFLlWBZImJl/PHcK3052EjiH1BBxD05IGlRiiwXDVZUwKGb1byv2lT2/KLPSOE1MRbwuEMvqhr+RehaiRYwSK/qJT+Ct0iiifC3flPb09lXMbOjwvT6Ab8vWc5+Qoe7ac+RXCkgQIDAQAB";

    @Before
    public void setUp() throws Exception {

    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testRSA() throws Exception {
        String data = "测试内容测试内容测试内容测试内容测试内容测试内容测试内容";
        System.out.println("待签名数据：" + data);
        long start = System.currentTimeMillis();
        String sign = KitSignTextByKey.rsaSign(data, privateKey_PKCS8);
        long end = System.currentTimeMillis();
        System.err.println("生成签名：" + sign + "    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        boolean result = KitSignTextByKey.verifyRsaSign(data, sign, publicKey);
        end = System.currentTimeMillis();
        System.err.println("校验签名结果：" + result + "    用时：" + (end - start) + "ms");
    }

    @Test
    public void testRSA256() throws Exception {
        String data = "测试内容测试内容测试内容测试内容测试内容测试内容测试内容";
        System.out.println("待签名数据：" + data);
        long start = System.currentTimeMillis();
        String sign = KitSignTextByKey.rsa256Sign(data, privateKey_PKCS8);
        long end = System.currentTimeMillis();
        System.err.println("生成签名：" + sign + "    用时：" + (end - start) + "ms");

        start = System.currentTimeMillis();
        boolean result = KitSignTextByKey.verifyRsa256Sign(data, sign, publicKey);
        end = System.currentTimeMillis();
        System.err.println("校验签名结果：" + result + "    用时：" + (end - start) + "ms");
    }
}
