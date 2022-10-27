package com.example.order;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class ConfigNacosApplication {
//public class ConfigNacosApplication extends SpringBootServletInitializer implements CommandLineRunner {
    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ConfigNacosApplication.class,args);

//        String userName = applicationContext.getEnvironment().getProperty("user.name");
//        String userAge = applicationContext.getEnvironment().getProperty("user.age");
//        System.err.println("user name :"+userName+"; age: "+userAge);

        while(true) {
            //当动态配置刷新时，会更新到 Enviroment中，因此这里每隔一秒中从Enviroment中获取配置
            String userName = applicationContext.getEnvironment().getProperty("user.name");
            String userAge = applicationContext.getEnvironment().getProperty("user.age");
            String currentEnv = applicationContext.getEnvironment().getProperty("current.env");
            System.err.println("user name :" + userName + "; age: " + userAge + "; env: " + currentEnv);
            TimeUnit.SECONDS.sleep(1);
        }
    }

    @Resource
    private ConfigurableEnvironment springEnv;

    //@Override
    public void run(String... args) throws Exception {
        MutablePropertySources propSrcs = springEnv.getPropertySources();
        // 获取所有配置
        Map<String, String> props = StreamSupport.stream(propSrcs.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toMap(Function.identity(), springEnv::getProperty));

        // key 和 value 之间的最小间隙
        int interval = 20;
        int max = props.keySet().stream()
                .max(Comparator.comparingInt(String::length))
                .orElse("")
                .length();

        // 打印
        props.keySet().stream()
                .sorted()
                .forEach(k -> {
                    int i = max - k.length() + interval;
                    String join = String.join("", Collections.nCopies(i, " "));
                    System.out.println(String.format("%s%s%s", k, join, props.get(k)));
                });
    }
}
