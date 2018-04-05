package com.kitchen.market.common.web;

//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;

//import javax.servlet.http.HttpServletRequest;

/**
 * 服务器地址工具类
 *
 * @implNote kitchen-market去除spring-web和javaee依赖，此类中的方法仅供参考
 *
 * @date 2016-12-29
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitServerAddressUtil {
    private static String SERVER_ROOT_LOCATION = null;

    /**
     * 通过Spring容器，获取当前服务器的地址
     * 格式：scheme://***.**.**.**:port/contextPath
     * @return
     */
    /*public static String getServerPath() {
        if (SERVER_ROOT_LOCATION == null || SERVER_ROOT_LOCATION.isEmpty()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String location = getServerPath(request);
            SERVER_ROOT_LOCATION = location;
        }

        return SERVER_ROOT_LOCATION;
    }*/
    /**
     * 通过Http请求对象，获取当前服务器的地址
     * 格式：scheme://***.**.**.**:port/contextPath
     * @param request
     * @return
     */
    /*public static String getServerPath(HttpServletRequest request) {
        String fullPath = "";

        if (request != null) {
            String contextPath = request.getContextPath();
            fullPath = request.getScheme() + "://" + request.getHeader("Host") + contextPath;
        }

        return fullPath;
    }*/
}
