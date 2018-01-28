package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.vo.CartVo;

/**
 * Created by Administrator on 2018/1/8 0008.
 */
public interface ICartService {

    ServerResponse<CartVo> add(Integer userId, Integer productId, Integer count);

    //更新购物车商品数量
    ServerResponse<CartVo> update(Integer userId,Integer productId,Integer count);

    //删除购物车产品
    ServerResponse<CartVo> deleteProduct(Integer userId,String productIds);

    //查询商品
    ServerResponse<CartVo> list(Integer userId);

    ServerResponse<CartVo> selectOrUnselect(Integer userId,Integer checked,Integer productId);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
