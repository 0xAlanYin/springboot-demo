package starter.service;

/**
 * @author Alan Yin
 * @date 2021/1/18
 */

public class HttpClientUtils {

    public static boolean sendMsg(String url, String accessKeyId, String accessKeySecret, String msg) {
        // TODO 调用指定 url 进行请求的业务逻辑
        System.out.println("Http请求，url=" + url + ";appId=" + accessKeyId + ";signKey=" + accessKeySecret + ";msg=" + msg);
        return true;
    }
}
