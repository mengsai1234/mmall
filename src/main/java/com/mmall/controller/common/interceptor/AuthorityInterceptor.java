package com.mmall.controller.common.interceptor;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * 管理员登陆拦截器
 * Created by Administrator on 2018/1/22 0022.
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    /**
     * 在进入请求之前，首先进入拦截器，执行此方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle");
        //请求中Controller中的方法名
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //解析handlerMethod
        String methodName = handlerMethod.getMethod().getName(); //获取方法名
        String className = handlerMethod.getBean().getClass().getSimpleName(); //获取类名

        //解析参数
        StringBuffer requestParamBuffer = new StringBuffer();

        Map paramMap = request.getParameterMap();
        Iterator iter = paramMap.entrySet().iterator();
        while(iter.hasNext()){
            //Map.Entry entry = (Map.Entry)iter.next();
            Map.Entry entry = (Map.Entry) iter.next();
            String mapKey = (String)entry.getKey();
            String mapValue = StringUtils.EMPTY;

            Object obj = entry.getValue();
            if(obj instanceof String[]){
                String[] strs = (String[]) obj;
                mapValue = Arrays.toString(strs);
            }
            requestParamBuffer.append(mapKey).append("=").append(mapValue);
        }

        //此处通过代码，规定不进行拦截的请求
        //如果拦截到登录的请求，直接放过
        if(StringUtils.equals(className,"UserManageController") && StringUtils.equals(methodName,"login")){
            log.info("权限拦截器拦截到请求,className:{},methodName:{}",className,methodName);
            //如果拦截到登录请求，不打印登录参数
            return true;
        }


        User user = null;
        String loginToken = CookieUtil.readLoginToken(request);
        if(StringUtils.isNotEmpty(loginToken)){
            String userJsonStr = RedisShardedPoolUtil.get(loginToken);
            user = JsonUtil.String2Obj(userJsonStr,User.class);
        }
        //用户为空或者不是管理员
        if(user == null || (user.getRole().intValue() != Const.Role.ROLE_ADMIN)){

            //重置response
            response.reset();
            response.setCharacterEncoding("utf-8");
            //返回json数据
            response.setContentType("application/json;charset=utf-8");
            //重新获取out对象
            PrintWriter out = response.getWriter();

            if(user == null){
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorMsg("拦截器拦截，用户未登录")));
            }else{
                out.print(JsonUtil.obj2String(ServerResponse.createByErrorMsg("用户不是管理员")));
            }
            out.flush();
            out.close();

            return false; //登录失败或者不是管理员
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        log.info("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        log.info("afterHandle");
    }
}
