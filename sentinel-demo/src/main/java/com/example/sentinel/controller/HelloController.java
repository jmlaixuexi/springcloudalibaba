package com.example.sentinel.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.example.sentinel.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class HelloController {

    private static final String RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGRADE_RESOURCE_NAME = "degrade";

    //进行sentinel流控
    @RequestMapping(value = "/hello")
    public String hello(){
        Entry entry = null;

        try {
            //sentinel针对资源进行限制的
            entry = SphU.entry(RESOURCE_NAME);
            //被保护的业务逻辑
            String str = "hello world";
            log.info("==========" + str + "==============");
            return str;
        } catch (BlockException e) {
            // 资源访问阻止，被限流或者降级
            //进行相应的处理操作
            log.info("block!");
            return "被流控了！";
        } catch (Exception e) {
            //若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(e, entry);
        }finally {
            if(entry!=null){
                entry.close();
            }
        }
        return null;
    }

    /**
     * spring 的初始化方法
     */
    @PostConstruct
    private static void initFlowRules(){
        //流控规则
        List<FlowRule> rules = new ArrayList<>();
        //流控
        FlowRule rule = new FlowRule();
        //设置保护的资源
        rule.setResource(RESOURCE_NAME);
        //设置流控规则QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置受保护的资源阀值
        // Set limit QPS to 20.
        rule.setCount(1);
        rules.add(rule);

        //流控
        FlowRule rule2 = new FlowRule();
        //设置保护的资源
        rule2.setResource(USER_RESOURCE_NAME);
        //设置流控规则QPS
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置受保护的资源阀值
        // Set limit QPS to 20.
        rule2.setCount(1);
        rules.add(rule2);

        FlowRuleManager.loadRules(rules);
    }



    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    /**
     * @SentinelResource 改善接口中资源定义和流控降级后的处理方法
     * 怎么使用：
     * 1.添加依赖<artifactId>sentinel-annotation-aspectj</artifactId>
     * 2.配置bean--SentinelResourceAspectj
     *              vaule   定义资源
     *              blockHandler    设置 流控降级后的处理方法（默认该方法必须声明在同一类）
     *              如果不设置在同一类中，使用blockHandlerClass指定类
     *              fallback 当接口出现了异常，可以交给fallback指定的方法镜像处理
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/user/{id}")
    @SentinelResource(value = USER_RESOURCE_NAME,fallback = "fallbackHandlerForGetUser", blockHandler = "blockHandlerForGetUser")
    public User user(@PathVariable String id){
        int a = 1/0;
        return new User(id,"ceshi");
    }


    public User fallbackHandlerForGetUser(String id,Throwable ex){
        ex.printStackTrace();
        return  new User(id,"异常处理！");
    }

    /**
     * 注意：
     * 1.一定要用public
     * 2.返回值一定要和源方法保证一致，包含源方法的参数
     * 3.可以在参数最后添加BlockException可以区分是什么规则的处理方法
     * @param id
     * @param ex
     * @return
     */
    public User blockHandlerForGetUser(String id,BlockException ex){
        ex.printStackTrace();
        return  new User(id,"流控！");
    }

    @PostConstruct
    public void initDegradeRule(){
        //降级规则 异常
        List<DegradeRule> degradeRules = new ArrayList<>();
        DegradeRule degradeRule = new DegradeRule();
        //资源名，即规则的作用对象
        degradeRule.setResource(DEGRADE_RESOURCE_NAME);
        //熔断策略，支持慢调用比例/异常比例/异常数策略 默认值:慢调用比例
        //慢调用比例 (SLOW_REQUEST_RATIO)
        //异常比例 (ERROR_RATIO)
        //异常数 (ERROR_COUNT)
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        //慢调用比例模式下为慢调用临界 RT（超出该值计为慢调用）；异常比例/异常数模式下为对应的阈值
        degradeRule.setCount(2);
        //熔断时长，单位为 s
        degradeRule.setTimeWindow(10);
        //熔断触发的最小请求数，请求数小于该值时即使异常比率超出阈值也不会熔断（1.7.0 引入）  默认值:5
        degradeRule.setMinRequestAmount(2);
        //统计时长（单位为 ms），如 60*1000 代表分钟级（1.8.0 引入）    默认：1000 ms
        degradeRule.setStatIntervalMs(60*1000); //时间太短不好测
        //慢调用比例阈值，仅慢调用比例模式有效（1.8.0 引入） 0-1C
        degradeRule.setSlowRatioThreshold(0.6);
        degradeRules.add(degradeRule);

        //setStatIntervalMs设置时长内：执行 setMinRequestAmount 次数  出现异常数 setCount  就会触发熔断

        DegradeRuleManager.loadRules(degradeRules);
    }


    @RequestMapping(value = "/degrade/{id}")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME, entryType = EntryType.IN,blockHandler = "blockHandlerForFb")
    public User degrade(@PathVariable String id) throws InterruptedException {
        //异常数\比例
        throw new RuntimeException("异常");

//        //慢调比例
//        TimeUnit.SECONDS.sleep(1);
//        return new User(id,"正常");
    }

    public User blockHandlerForFb(String id,BlockException e){
        e.printStackTrace();
        return new User(id,"熔断降级");
    }
}
