package starter.service;

/**
 * 2.定义Service服务类，有两个作用: 一个为引入的项目需要的功能性服务，另外一个用来 springboot 自动配置时的判断依据。
 *
 * @author Alan Yin
 * @date 2021/1/18
 */

public interface MsgService {

    /**
     * 发送消息
     *
     * @param msg
     * @return
     */
    boolean sendMsg(String msg);

}

/**
 * 总结下Starter的工作流程：
 * <p>
 * Spring Boot在启动时扫描项目所依赖的JAR包，寻找包含spring.factories文件的JAR包；
 * 根据spring.factories配置加载AutoConfiguration类；
 * 根据@Conditional注解的条件，进行自动配置并将Bean注入Spring容器。
 */