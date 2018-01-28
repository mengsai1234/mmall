package com.mmall.controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.pojo.User;
import com.mmall.service.IFileService;
import com.mmall.service.IProductService;
import com.mmall.service.IUserService;
import com.mmall.util.CookieUtil;
import com.mmall.util.JsonUtil;
import com.mmall.util.PropertiesUtil;
import com.mmall.util.RedisShardedPoolUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有的登录管理员验证全部通过AuthorityInterceptor拦截器
 * Created by Administrator on 2018/1/7 0007.
 */

@Controller
@RequestMapping("/manage/product")
public class ProductManageController {

    @Autowired
    private IUserService iUserService;
    @Autowired
    private IProductService iProductService;
    @Autowired
    private IFileService iFileService;

    /**
     * 根据产品参数更新或新增产品
     * @param request
     * @param product
     * @return
     */
    @RequestMapping("save.do")
    @ResponseBody
    public ServerResponse productSave(HttpServletRequest request, Product product){
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
//            //增加产品的业务逻辑
//            return iProductService.saveOrUpdateProduct(product);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        //增加产品的业务逻辑
        return iProductService.saveOrUpdateProduct(product);
    }

    /**
     * 修改商品销售状态
     * @param request
     * @param productId
     * @param status
     * @return
     */
    @RequestMapping("set_sale_status.do")
    @ResponseBody
    public ServerResponse setSaleStatus(HttpServletRequest request, Integer productId,Integer status){
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
//            return iProductService.setSaleStatus(productId,status);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iProductService.setSaleStatus(productId,status);
    }

    /**
     * 获取商品详情
     * @param request
     * @param productId
     * @return
     */
    @RequestMapping("detail.do")
    @ResponseBody
    public ServerResponse getDetail(HttpServletRequest request, Integer productId){
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
//            return iProductService.manageProductDetail(productId);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iProductService.manageProductDetail(productId);
    }

    /**
     * 使用PageHelper获取商品列表，实现分页效果
     * 默认显示第一页，每页显示10条
     * @param request
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list.do")
    @ResponseBody
    public ServerResponse getList(HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
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
//            return iProductService.getProductList(pageNum,pageSize);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iProductService.getProductList(pageNum,pageSize);
    }

    /**
     * 动态搜索商品并分页
     * @param request
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search.do")
    @ResponseBody
    public ServerResponse productSearch(HttpServletRequest request, String productName, Integer productId, @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum, @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
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
//            return iProductService.searchProduct(productName,productId,pageNum,pageSize);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }
        return iProductService.searchProduct(productName,productId,pageNum,pageSize);
    }

    /**
     *文件上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload.do")
    @ResponseBody
    public ServerResponse upload(@RequestParam(value="upload_file",required=false)MultipartFile file, HttpServletRequest request){
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
//            //获取名为upload文件夹的全部路径
//            String path = request.getSession().getServletContext().getRealPath("upload");
//            String targetFileaName = iFileService.upload(file,path);
//            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileaName;
//            Map fileMap = new HashMap();
//            fileMap.put("uri",targetFileaName);
//            fileMap.put("url",url);
//            return ServerResponse.createBySuccess(fileMap);
//        }else{
//            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
//        }

        String path = request.getSession().getServletContext().getRealPath("upload");
        String targetFileaName = iFileService.upload(file,path);
        String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileaName;
        Map fileMap = new HashMap();
        fileMap.put("uri",targetFileaName);
        fileMap.put("url",url);
        return ServerResponse.createBySuccess(fileMap);
    }


}
