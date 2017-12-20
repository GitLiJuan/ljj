package com.company.dao.idao;

import com.company.dao.pojo.Cart;

import com.company.dao.vo.CartProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CartMapper {

    int deleteByPrimaryKey(Integer id);//根据id删除购物车商品

    int insert(Cart record);//添加商品

    int insertSelective(Cart record);//动态添加

    Cart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cart record);

    int updateByPrimaryKey(Cart record);

    /**
     * 查询某个用户购物车所有选中的商品
     * @param userId
     * @return 返回一个购物项集合
     */
    List<Cart> selectByChecked(@Param("userId") Integer userId);
    //展示购物车商品
    List<CartProduct> findAllProduct(@Param("userId") Integer userId);

    //购物车添加商品
    int addProduct(@Param("userId") int userId, @Param("productId") int productId, @Param("count") int count);

    //更新购物车某个产品数量
    int updateNum(@Param("userId") int userId, @Param("productId") int productId, @Param("count") int count);

    //移除购物车某个产品
    int deleteProduct(@Param("userId") int userId, @Param("productIds") int productIds[]);

    //购物车选中某个商品
    int checkOneProduct(@Param("userId") int userId, @Param("productId") int productId);

    //购物车取消选中某个商品
    int unCheckedProduct(@Param("userId") int userId, @Param("productId") int productId);

    //查询购物车中的商品数量
    int findCount(@Param("userId") int userId);

    //购物车全选
    int allCheck(@Param("userId") int userId);

    //购物车取消全选
    int allUnCheck(@Param("userId") int userId);
}