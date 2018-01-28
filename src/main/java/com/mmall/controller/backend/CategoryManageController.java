package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.RedisShardedPoolUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 商品类控制器
 * 所有的登录管理员验证全部使用AuthorityInterceptor拦截器
 * Created by Administrator on 2017/12/25 0025.
 */
@Controller
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private ICategoryService iCategoryService;


    /**
     * 增加商品类
     * @RequestParam代表为参数指定一个默认值，当不传此参数的时候，默认值为0
     * @param request
     * @param categoryname
     * @param parentId
     * @return
     */
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpServletRequest request, String categoryname, @RequestParam(value = "parentId",defaultValue = "0") int parentId){
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
//            //是管理员，增加处理分类的逻辑
//            return iCategoryService.addCategory(categoryname,parentId);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        //所有的登录验证全部使用拦截器
        return iCategoryService.addCategory(categoryname,parentId);
    }

    /**
     * 修改商品类的名字
     * @param request
     * @param categoryId
     * @param categoryName
     * @return
     */
    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpServletRequest request,Integer categoryId,String categoryName){
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
//            //更新categoryName
//            return iCategoryService.updateCategory(categoryId,categoryName);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        //更新categoryName
        //所有的登录验证全部使用拦截器
        return iCategoryService.updateCategory(categoryId,categoryName);
    }

    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpServletRequest request, @RequestParam(value= "categoryId",defaultValue = "0")Integer categoryId){
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
//            //查询子节点的category信息，不递归，保持平级
//            return iCategoryService.getChildrenParallelCategory(categoryId);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        //所有的登录验证全部使用拦截器
        //查询子节点的category信息，不递归，保持平级
        return iCategoryService.getChildrenParallelCategory(categoryId);
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpServletRequest request,@RequestParam(value= "categoryId",defaultValue = "0")Integer categoryId){
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
//            //查询当前节点的id和递归子节点的id
//            return iCategoryService.selectCategoryAndChildrenById(categoryId);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }

        //查询当前节点的id和递归子节点的id
        return iCategoryService.selectCategoryAndChildrenById(categoryId);
    }
}
