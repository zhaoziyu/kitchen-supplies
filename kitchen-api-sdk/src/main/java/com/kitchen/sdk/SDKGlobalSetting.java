package com.kitchen.sdk;

/**
 * SDK的全局配置
 *
 * @date 2017-04-11
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class SDKGlobalSetting {
    //url必须以 /（斜线） 结束，否则会抛出IllegalArgumentException
    private static String ROOT_PATH_HTTP = "http://localhost:8080/";
    private static String ROOT_PATH_HTTPS = "https://localhost:8080/";

    // 开发者在平台中申请的密钥ID
    private static String DEVELOPER_APP_ID = "0241BDCE04E94DE9B20C9A5455D930B5";
    // 开发者的私钥
    private static String DEVELOPER_PRIVATE_KEY = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPb9qeaArrL/HqzKheoz1LSOg1BDwJP9LjPkYQUuVYFkiYmX88dwrfTnYSOIfUEHEPTkgaVGKLBcNVlTAoZvVvK/aVPb8os9I4TUxFvC4Qy+qGv5F6FqJFjBIr+olP4K3SKKJ8Ld+U9vT2Vcxs6PC9PoBvy9Zzn5Ch7tpz5FcKSBAgMBAAECgYEAnI2EV3pRQVu70cI8x4o61IdQbFvFgQgFdRbY+DO6Nt3G39PUzSF64bSXObKV0dXsxYzhMCUcPUz0871N6HBCJzSt9iG+cpGS8X13AIa/uQ9/hFMn0BPyDOvdsL+/kv7Ahq2zHAMTmbJySMO7WCgMzd5O90KPFoJAGNLVXAtz+sECQQD83/bKI68XiepErLMzVIuOVt/WzvrcGOt6z0v1zthRHKdt6+nuhM/jNV1sDeteimgdT77j65qUcjcNMcrxDUUdAkEA+gsViMtoKgDSsMbKhNlLIlAxU/qvNXXwLx0E1Y3zcq+o/yJ4lgYbR3+MZuw8bfqkfparBOdERlrzH8xaG1IztQJAJeECncr0mmkNT5YzDbhXY03+H7ZHe5q8A1xz+3EtlBDfv6Z8Fz+LyHQg92OqYzIGYIWmiYusTxpAxtgzlyIuvQJBAO3AGlK+7iV6MNuruacGIh3XWH/8jhpsMNvrYMxaNBBpnGwz76re1ZNvYSYAHBmKyFwhkS2RZObs1d33ZfoyeD0CQQDzOP5DW/4EvbWc65gEzOnzVc14/7p6MRSRR/lfUYiU1zY1RTgzKylEFee+z/Jqd0IOOpP5dkFdLxHMbrgPZ63K";
    // 服务端的公钥
    private static String SERVER_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDK7NiPMXg4tX1ZcbpcLFBi2/q7uV4hVO8W0d01g7eAqS1lDmdtZANVUT/QPe6g0FLRZE2v5i7ThWNEVxFDeqNlaYX54MEIRY6DZLejmhqnrSLXdDlbg8308A1GToHYG+hdJ/aZWxRoDNPM+AmyQwHqn5nAS6+x3jIT5/wsUiyOKwIDAQAB";

    public static void init(String httpRootPath, String httpsRootPath, String appId, String devPrivateKey, String serverPublicKey) {
        ROOT_PATH_HTTP = httpRootPath;
        ROOT_PATH_HTTPS = httpsRootPath;
        DEVELOPER_APP_ID = appId;
        DEVELOPER_PRIVATE_KEY = devPrivateKey;
        SERVER_PUBLIC_KEY = serverPublicKey;
    }

    public static String getHttpRootPath() {
        return ROOT_PATH_HTTP;
    }
    public static String getHttpsRootPath() {
        return ROOT_PATH_HTTPS;
    }
    public static String getDeveloperAppId() {
        return DEVELOPER_APP_ID;
    }
    public static String getDeveloperPrivateKey() {
        return DEVELOPER_PRIVATE_KEY;
    }
    public static String getServerPublicKey() {
        return SERVER_PUBLIC_KEY;
    }
}
