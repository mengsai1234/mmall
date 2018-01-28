package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

/**
 * Created by Administrator on 2017/12/24 0024.
 *
 *
 */
public interface IUserService {

    //登录验证
    ServerResponse<User> login(String username, String password);

    //注册验证
    ServerResponse<String> register(User user);

    //实时校验邮箱和用户名是否已存在
    ServerResponse<String> checkValid(String str,String type);

    //获取密码找回问题
    ServerResponse selectQuestion(String username);

    //验证问题的答案
    ServerResponse<String> checkAnswer(String username,String question,String answer);

    //修改密码
    ServerResponse<String> forgetRestPassword(String username,String passwordNew,String forgetToken);

    //登录状态修改密码
    ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user);

    //更新个人信息
    ServerResponse<User> updateInfo(User user);

    //获取个人信息
    ServerResponse<User> getInfo(Integer userId);

    //校验是否是管理员
    ServerResponse checkAdminRole(User user);
}
