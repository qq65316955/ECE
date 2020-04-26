package com.yhwl.yhwl.esc.api.tools.machine.security;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Random;

@Slf4j
public class SafeTools {

    public static String getSalt(){
        byte [] salt =Digests.generateSalt(Constants.SALT_SIZE);
        return Encodes.encodeHex(salt);
    }

    /**
     *
     * @param paramStr 输入需要加密的字符串
     * @return
     */
    public static String entryptPassword(String paramStr,String salt) {
        if(StringUtils.isNotEmpty(paramStr)){
            byte[] saltStr = Encodes.decodeHex(salt);
            byte[] hashPassword = Digests.sha1(paramStr.getBytes(), saltStr, Constants.HASH_INTERATIONS);
            String password = Encodes.encodeHex(hashPassword);
            return password;
        }else{
            return null;
        }
    }

    /**
     * 判断请求是否是ajax请求
     * @param request
     * @return
     */
    public static boolean isAjax(HttpServletRequest request){
        String accept = request.getHeader("accept");
        return accept != null && accept.contains("application/json") || (request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").contains("XMLHttpRequest"));
    }
    /**
     * 获取客户端的ip信息
     *
     * @param request
     * @return
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknow".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取操作系统,浏览器及浏览器版本信息
     * @param request
     * @return
     */
    public static Map<String,String> getOsAndBrowserInfo(HttpServletRequest request){
        Map<String,String> map = Maps.newHashMap();
        String  browserDetails  =   request.getHeader("User-Agent");
        String  userAgent       =   browserDetails;
        String  user            =   userAgent.toLowerCase();

        String os = "";
        String browser = "";

        //=================OS Info=======================
        if (userAgent.toLowerCase().contains("windows"))
        {
            os = "Windows";
        } else if(userAgent.toLowerCase().contains("mac"))
        {
            os = "Mac";
        } else if(userAgent.toLowerCase().contains("x11"))
        {
            os = "Unix";
        } else if(userAgent.toLowerCase().contains("android"))
        {
            os = "Android";
        } else if(userAgent.toLowerCase().contains("iphone"))
        {
            os = "IPhone";
        }else{
            os = "UnKnown, More-Info: "+userAgent;
        }
        //===============Browser===========================
        if (user.contains("edge"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Edge")).split(" ")[0]).replace("/", "-");
        } else if (user.contains("msie"))
        {
            String substring=userAgent.substring(userAgent.indexOf("MSIE")).split(";")[0];
            browser=substring.split(" ")[0].replace("MSIE", "IE")+"-"+substring.split(" ")[1];
        } else if (user.contains("safari") && user.contains("version"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Safari")).split(" ")[0]).split("/")[0]
                    + "-" +(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
        } else if ( user.contains("opr") || user.contains("opera"))
        {
            if(user.contains("opera")){
                browser=(userAgent.substring(userAgent.indexOf("Opera")).split(" ")[0]).split("/")[0]
                        +"-"+(userAgent.substring(userAgent.indexOf("Version")).split(" ")[0]).split("/")[1];
            }else if(user.contains("opr")){
                browser=((userAgent.substring(userAgent.indexOf("OPR")).split(" ")[0]).replace("/", "-"))
                        .replace("OPR", "Opera");
            }

        } else if (user.contains("chrome"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Chrome")).split(" ")[0]).replace("/", "-");
        } else if ((user.contains("mozilla/7.0")) || (user.contains("netscape6"))  ||
                (user.contains("mozilla/4.7")) || (user.contains("mozilla/4.78")) ||
                (user.contains("mozilla/4.08")) || (user.contains("mozilla/3")) )
        {
            browser = "Netscape-?";

        } else if (user.contains("firefox"))
        {
            browser=(userAgent.substring(userAgent.indexOf("Firefox")).split(" ")[0]).replace("/", "-");
        } else if(user.contains("rv"))
        {
            String IEVersion = (userAgent.substring(userAgent.indexOf("rv")).split(" ")[0]).replace("rv:", "-");
            browser="IE" + IEVersion.substring(0,IEVersion.length() - 1);
        } else
        {
            browser = "UnKnown, More-Info: "+userAgent;
        }
        map.put("os",os);
        map.put("browser",browser);
        return map;
    }

    /*
     * @Author cl
     * @Description  随机生成mac地址
     * @param
     * @return
     **/
    public static String getMac() {
        StringBuffer mac = new StringBuffer();
        mac.append("03:F2:36:");
        for(int i=1; i<=3; i++) {
            String one = getStringRandom(1);
            String two = getStringRandom(1);
            mac.append(one).append(two);
            if(i != 3) mac.append(":");
        }
        return mac.toString().toUpperCase();
    }

    /*
     * @Author cl
     * @Description  随机生成mac地址
     * @param
     * @return
     **/
    public static String getRandomMac() {
        StringBuffer mac = new StringBuffer();
        for(int i=1; i<=6; i++) {
            String one = getStringRandom(1);
            String two = getStringRandom(1);
            mac.append(one).append(two);
            if(i != 6) mac.append(":");
        }
        return mac.toString().toLowerCase();
    }

    /**
     * 获取随机IP
     * @return
     */
    public static String getRandomIp() {
        StringBuilder ip = new StringBuilder("192.168.");
        Random random = new Random();
        int random2 = random.nextInt(255);
        while (random2 == 1) {
            random2 = random.nextInt(255);
        }
        ip.append(random.nextInt(10)).append(".").append(random2);
        return ip.toString();
    }

    //生成随机数字和字母,
    public static String getStringRandom(int length) {
        String val = "";
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char)(random.nextInt(6) + temp);
            } else if( "num".equalsIgnoreCase(charOrNum) ) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }
}
