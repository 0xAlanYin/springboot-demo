package starter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import starter.property.CustomProperties;
import starter.service.DefaultMsgService;
import starter.service.MsgService;

/**
 * 4.创建自动配置类
 * <p>
 * 自动配置类就是一个普通的java类，通过不同的注解来对其赋予不同的功能。其中最核心的当然是@Configuration注解。
 *
 * @author Alan Yin
 * @date 2021/1/18
 */
@Configuration
@ConditionalOnClass(MsgService.class)
@EnableConfigurationProperties(CustomProperties.class)
public class CustomAutoConfiguration {

    /**
     * 注入属性配置类
     */
    @Autowired
    private CustomProperties customProperties;

    @Bean
    @ConditionalOnMissingBean(MsgService.class)
    @ConditionalOnProperty(prefix = "msg", value = "enabled", havingValue = "true")
    public MsgService msgService() {
        MsgService msgService = new DefaultMsgService(customProperties);
        // 如果提供了其他set方法，在此也可以调用对应方法对其进行相应的设置或初始化
        return msgService;
    }

}

/**
 * 类上的注解:
 * @Configuration 用来声明该类为一个配置类；
 * @ConditionalOnClass 注解说明只有当 MsgService 类存在于 classpath 中时才会进行相应的实例化；
 * @EnableConfigurationProperties 将 application.properties 中对应的属性配置设置于 MsgProperties 对象中；
 * <p>
 * msgService 方法上的注解：
 * @Bean 表明该方法实例化的对象会被加载到容器当中；
 * @ConditionalOnMissingBean 指明当容器中不存在 MsgService 的对象时再进行实例化；
 * @ConditionalOnProperty 指定了配置文件中 msg.enabled=true 时才进行相应的实例化。
 */
