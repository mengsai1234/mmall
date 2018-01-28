package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Category;

import java.util.List;

/**
 * 处理商品类
 * Created by Administrator on 2017/12/25 0025.
 */
public interface ICategoryService {

    //增加商品类别
    ServerResponse addCategory(String categoryName, Integer parentId);

    //修改商品类别名字
    ServerResponse updateCategory(Integer categoryId,String categoryName);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer categoryId);

    //递归查询当前节点的Id和子节点的id集合
    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
