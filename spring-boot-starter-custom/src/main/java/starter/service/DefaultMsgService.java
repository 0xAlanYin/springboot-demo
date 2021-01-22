package starter.service;

import starter.property.CustomProperties;

/**
 * @author Alan Yin
 * @date 2021/1/18
 */

public class DefaultMsgService implements MsgService {

    private CustomProperties customProperties;

    public DefaultMsgService(CustomProperties customProperties) {
        this.customProperties = customProperties;
    }

    public boolean sendMsg(String msg) {
        // 调用http服务并发送消息，返回结果
        return HttpClientUtils.sendMsg(customProperties.getUrl(), customProperties.getAppId(), customProperties.getSignKey(), msg);
    }

}
