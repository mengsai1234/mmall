package com.mmall.dao;

import com.mmall.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int checkUsername(String username);

    User selectLogin(@Param("username") String username, @Param("password")String password);

    int checkEmail(String email);

    //不根据userId校验邮箱是否已存在（全局校验）
    int checkEmailByUserId(String email,int userId);

    String selectQuestionByUsername(String username);

    //验证问题答案
    int checkAnswer(@Param("username")String username,@Param("question")String question,@Param("answer")String answer);

    //修改密码
    int updatePasswordByUsername(@Param("username")String username,@Param("password")String password);

    //根据id校验密码
    int checkPassword(@Param("password")String password,@Param("id")Integer userId);
}