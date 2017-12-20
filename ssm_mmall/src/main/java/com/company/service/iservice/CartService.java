package com.company.service.iservice;

import com.company.common.ServerResponse;
import com.company.dao.pojo.Cart;
import com.company.dao.vo.CartProduct;
import com.company.dao.vo.CartProductVoList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
public interface CartService {

    //展示购物车商品列表
    ServerResponse<CartProductVoList> findAllProduct(@Param("userId") Integer userId);

    //购物车添加商品
    ServerResponse<CartProductVoList> addProduct(@Param("userId") int userId, @Param("productId") int productId, @Param("count") int count);

    //更新购物车某个产品数量
    int updateNum(@Param("userId") int userId, @Param("productId") int productId, @Param("count") int count);

    //移除购物车某个产品(批量删除)
    int deleteProduct(@Param("userId") int userId, @Param("productIds") int productId[]);

    //购物车选中某个商品
    int checkOneProduct(@Param("userId") int userId, @Param("productId") int productId);

    //购物车取消选中某个商品
    int unCheckedProduct(@Param("userId") int userId, @Param("productId") int productId);

    //查询购物车中的商品数量
    ServerResponse<Integer> findCount(@Param("userId") int userId);

    //购物车全选
    int allCheck(@Param("userId") int userId);

    //购物车取消全选
    int allUnCheck(@Param("userId") int userId);
}
