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

    /**
     * @ConditionalOnMissingBean 与 @ConditionalOnBean 作用：根据当前环境或者容器情况来动态注入bean，要配合@Bean使用
     *
     * @ConditionalOnMissingBean 作用：判断当前需要注入 Spring 容器中的 bean 的实现类是否已经含有，有的话不注入，没有就注入
     * @ConditionalOnBean 作用：判断当前需要注册的 bean 的实现类否被 spring 管理，如果被管理则注入，反之不注入
     */

    /**
     * @Conditional 扩展注解 | 作用（判断是否满足当前指定条件）
     * @ConditionalOnJava 系统的java版本是否符合要求
     * @ConditionalOnBean 容器中存在指定Bean
     * @ConditionalOnMissingBean 容器中不存在指定Bean
     * @ConditionalOnExpression 满足SpEL表达式指定
     * @ConditionalOnClass 系统中有指定的类
     * @ConditionalOnMissingClass 系统中没有指定的类
     * @ConditionalOnSingleCandidate 容器中只有一个指定的Bean，或者这个Bean是首选Bean
     * @ConditionalOnProperty 系统中指定的属性是否有指定的值
     * @ConditionalOnResource 类路径下是否存在指定资源文件
     * @ConditionalOnWebApplication 当前是web环境
     * @ConditionalOnNotWebApplication 当前不是web环境
     * @ConditionalOnJndi JNDI存在指定项
     */

}

/**
 * 类上的注解:
 *
 * @Configuration 用来声明该类为一个配置类；
 * @ConditionalOnClass 注解说明只有当 MsgService 类存在于 classpath 中时才会进行相应的实例化；
 * @EnableConfigurationProperties 将 application.properties 中对应的属性配置设置于 MsgProperties 对象中；
 * <p>
 * msgService 方法上的注解：
 * @Bean 表明该方法实例化的对象会被加载到容器当中；
 * @ConditionalOnMissingBean 指明当容器中不存在 MsgService 的对象时再进行实例化；
 * @ConditionalOnProperty 指定了配置文件中 msg.enabled=true 时才进行相应的实例化。
 */
