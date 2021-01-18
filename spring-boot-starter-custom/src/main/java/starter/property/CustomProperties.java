package starter.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 3.定义配置类: 通过@ConfigurationProperties注解来进行对应的属性的装配
 * <p>
 * 定义 starter.property.CustomProperties 配置类，用于封装 application.properties或application.yml中的基础配置。
 * 这里关于消息发送的配置前缀统一采用 msg。
 *
 * @author Alan Yin
 * @date 2021/1/18
 */
@ConfigurationProperties(prefix = "msg")
public class CustomProperties {

    /**
     * 请求的地址
     */
    private String url;

    /**
     * 请求 appId
     */
    private String appId;

    /**
     * 请求密钥
     */
    private String signKey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSignKey() {
        return signKey;
    }

    public void setSignKey(String signKey) {
        this.signKey = signKey;
    }
}
