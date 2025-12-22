package com.hkh.sa.admin;

import com.hkh.sa.base.listener.Ip2RegionListener;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * SmartAdmin 项目启动类
 *
 * @Author 1024创新实验室-主任:卓大
 * @Date 2022-08-29 21:00:58
 * @Wechat zhuoda1024
 * @Email lab1024@163.com
 * @Copyright  <a href="https://1024lab.net">1024创新实验室</a>
 */
@EnableCaching
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@ComponentScan({ "com.hkh.sa","com.hkh.agent","com.hkh.core","com.hkh.domain" })
@MapperScan(value = { "com.hkh.sa","com.hkh.agent","com.hkh.core","com.hkh.domain" }, annotationClass = Mapper.class)
@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class AdminApplication {

    public static final String[] COMPONENT_SCAN_PACKAGES = { "com.hkh.sa","com.hkh.agent","com.hkh.core","com.hkh.domain" };

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AdminApplication.class);
        // 添加 日志监听器，使 log4j2-spring.xml 可以间接读取到配置文件的属性
        application.addListeners(
//                new LogVariableListener(),
                new Ip2RegionListener()
        );
        application.run(args);
    }
}
