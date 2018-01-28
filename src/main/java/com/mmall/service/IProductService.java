package com.mmall.service;

import com.github.pagehelper.PageInfo;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.Product;
import com.mmall.vo.ProductDetailVo;

/**
 * Created by Administrator on 2018/1/7 0007.
 */
public interface IProductService {

    //更新或新增商品信息
    ServerResponse saveOrUpdateProduct(Product product);

    //修改商品销售状态
    ServerResponse<String> setSaleStatus(Integer productId,Integer status);

    //获取商品详细信息
    ServerResponse<ProductDetailVo> manageProductDetail(Integer productId);

    //使用PagHelper插件查询商品列表，使用ProductListVo构建新的产品对象，展示所需要的信息
    ServerResponse getProductList(Integer pageNum,Integer pageSize);

    //动态搜索商品并分页
    ServerResponse<PageInfo> searchProduct(String productName, Integer productId, Integer pageNum, Integer pageSize);


    /**---------------前台-------------------**/
    //获取产品详情
    ServerResponse<ProductDetailVo> getProductDetail(Integer productId);

    //动态查询产品
    ServerResponse<PageInfo> getProductByKeywordCategory(String keyword,Integer categoryId,int pageNum,int pageSize,String orderBy);
}
