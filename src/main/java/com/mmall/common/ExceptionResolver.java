package com.mmall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常
 * Created by Administrator on 2018/1/22 0022.
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) {

        //异常类型
        log.error("{} Exception",request.getRequestURI(),e);

        //课程中使用jackson1.9，当使用Jackson2的时候，使用MappingJackson2JsonView
        //该对象用于处理modelandview的数据，改成json格式
        MappingJacksonJsonView mappingJacksonJsonView = new MappingJacksonJsonView();

        ModelAndView modelAndView = new ModelAndView(mappingJacksonJsonView);

        modelAndView.addObject("status",ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg","接口异常,详情请查看服务端的日志信息");
        modelAndView.addObject("data",e.toString()); //异常信息

        return modelAndView;
    }
}
