package com.mmall.controller.backend;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IOrderService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.vo.OrderVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 后台管理员订单管理接口
 * Created by Administrator on 2018/1/14 0014.
 */
@Controller
@RequestMapping("/manage/order/")
public class OrderManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IOrderService iOrderService;

    /**
     * 后台查看订单列表
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderList(HttpServletRequest request,
                                              @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                              @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        //User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr,User.class); //反序列化
//        if(user == null){
//            return ServerResponse.createByErrorCodeMsg( ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请先登录");
//        }
//        //校验是否是管理员
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            return iOrderService.manageList(pageNum,pageSize);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iOrderService.manageList(pageNum,pageSize);
    }

    /**
     * 后台查询订单详情
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse<OrderVo> orderDetail(HttpServletRequest request, Long orderNo){
        //User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr,User.class); //反序列化
//        if(user == null){
//            return ServerResponse.createByErrorCodeMsg( ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请先登录");
//        }
//        //校验是否是管理员
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            return iOrderService.manageDetail(orderNo);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iOrderService.manageDetail(orderNo);
    }

    /**
     * 后台管理根据订单号搜索订单（临时写死）
     * @param request
     * @param orderNo
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse<PageInfo> orderSearch(HttpServletRequest request, Long orderNo,
                                               @RequestParam(value = "pageNum",defaultValue = "1")int pageNum,
                                               @RequestParam(value = "pageSize",defaultValue = "10")int pageSize){
        //User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr,User.class); //反序列化
//        if(user == null){
//            return ServerResponse.createByErrorCodeMsg( ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请先登录");
//        }
//        //校验是否是管理员
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            return iOrderService.orderSearch(orderNo,pageNum,pageSize);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iOrderService.orderSearch(orderNo,pageNum,pageSize);
    }

    /**
     * 后台管理发货管理
     * @param request
     * @param orderNo
     * @return
     */
    @RequestMapping("send_goods.do")
    @ResponseBody
    public ServerResponse<String> orderSendGoods(HttpServletRequest request, Long orderNo){
        //User user = (User)session.getAttribute(Const.CURRENT_USER);
//        String loginToken = CookieUtil.readLoginToken(request);
//        if(StringUtils.isEmpty(loginToken)){
//            return ServerResponse.createByErrorMsg("用户未登录");
//        }
//        String userJsonStr = RedisShardedPoolUtil.get(loginToken);
//        User user = JsonUtil.String2Obj(userJsonStr,User.class); //反序列化
//        if(user == null){
//            return ServerResponse.createByErrorCodeMsg( ResponseCode.NEED_LOGIN.getCode(),"用户未登录，请先登录");
//        }
//        //校验是否是管理员
//        if(iUserService.checkAdminRole(user).isSuccess()){
//            return iOrderService.manageSendGoods(orderNo);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iOrderService.manageSendGoods(orderNo);
    }
}
