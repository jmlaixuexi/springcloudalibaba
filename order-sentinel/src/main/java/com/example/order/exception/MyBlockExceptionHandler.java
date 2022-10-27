package com.example.order.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.example.order.domain.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class MyBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {

        log.info("BlockExceptionHandler BlockException======================" + e.getRule());

        Result r = null;

        if (e instanceof FlowException) {
            //接口限流了
            r = Result.error(100,"接口限流了");
        } else if (e instanceof DegradeException) {
            //服务器降级了
            r = Result.error(100,"服务器降级了");
        } else if (e instanceof ParamFlowException) {
            //热点参数限流了
            r = Result.error(100,"热点参数限流了");
        } else if (e instanceof SystemBlockException) {
            //触发系统保护规则了
            r = Result.error(100,"触发系统保护规则了");
        } else if (e instanceof AuthorityException) {
            //授权规则不通过
            r = Result.error(100,"授权规则不通过");
        }

        httpServletResponse.setStatus(500);
        httpServletResponse.setCharacterEncoding("utf-8");
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(httpServletResponse.getWriter(), r);
    }
}
