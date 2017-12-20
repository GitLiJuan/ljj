package com.company.service.impl;

import com.company.common.ResponseCode;
import com.company.common.ServerResponse;
import com.company.dao.idao.CartMapper;
import com.company.dao.pojo.Cart;
import com.company.dao.vo.CartProduct;
import com.company.dao.vo.CartProductVoList;
import com.company.service.iservice.CartService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
@Service("cartService")
public class CartServiceImpl  implements CartService {
    @Autowired
    private CartMapper cartMapper;

    //查询购物车列表
    @Transactional
    @Override
    public ServerResponse<CartProductVoList> findAllProduct(@Param("userId") Integer userId) {
        List<CartProduct> lists = new ArrayList<CartProduct>();
        boolean allChecked = false;
        BigDecimal cartTotalPrice = new BigDecimal(0.0);
        lists = cartMapper.findAllProduct(userId);
        if (lists!=null){
            for (CartProduct cartProduct : lists) {
                if (cartProduct.getQuantity() > cartProduct.getProductStock()) {//商品数量与库存数量的需求比较
                    cartProduct.setLimitQuantity("LIMIT_NUM_ERROR");
                } else {
                    cartProduct.setLimitQuantity("LIMIT_NUM_SUCCESS");
                }
                cartTotalPrice = cartTotalPrice.add(cartProduct.getProductPrice());
                if(cartProduct.getProductChecked() == 0){
                    allChecked = false;
                }
            }
        }
        CartProductVoList cartProductVoList = new CartProductVoList(cartTotalPrice,lists,allChecked);

        return ServerResponse.createSuccessResponse(cartProductVoList);
    }

    //添加商品
    @Transactional
    @Override
    public ServerResponse<CartProductVoList> addProduct(@Param("userId") int userId, @Param("productId") int productId, @Param("count") int count) {
        int num =0;
        List<CartProduct> list = cartMapper.findAllProduct(userId);
        if(list.size()!=0){
            for (CartProduct c:list){
                if(c.getProductId()==productId){//如果userId和productId都相等，就只添加产品数量
                    c.setQuantity(c.getQuantity()+count);
                    num = cartMapper.updateNum(c.getUserId(),c.getProductId(),c.getQuantity());
                    if(num == 0){
                        return ServerResponse.createErrorMessageResponse("添加失败！");
                    }else {
                        List<CartProduct> list1 = cartMapper.findAllProduct(userId);
                        boolean flag = false;
                        BigDecimal totalPrice = new BigDecimal(0.0);
                        int count1 = 0;//产品选中状态
                        for (CartProduct cartProduct : list1){
                            if (cartProduct.getProductChecked() == 1){
                                totalPrice = totalPrice.add(cartProduct.getProductTotalPrice());
                                count1 ++;
                            }
                        }
                        if (count1 == list1.size())
                            flag = true;
                        CartProductVoList cartProductVoList = new CartProductVoList(totalPrice, list1, flag);
                        return ServerResponse.createSuccessResponse(cartProductVoList);
                    }
                }
            }
        }
        cartMapper.addProduct(userId,productId,count);
        return findAllProduct(userId);
    }



    //更新购物车产品数量
    @Transactional(readOnly = true)
    @Override
    public int updateNum(@Param("userId") int userId, @Param("productId") int productId, @Param("count") int count) {
        return cartMapper.updateNum(userId,productId,count);
    }

    //删除商品
    @Transactional
    @Override
    public int deleteProduct(@Param("userId") int userId, @Param("productIds") int productIds[]) {
        return cartMapper.deleteProduct(userId,productIds);
    }

    //单选
    @Transactional
    @Override
    public int checkOneProduct(@Param("userId") int userId, @Param("productId") int productId) {
        return cartMapper.checkOneProduct(userId,productId);
    }

    //取消单选
    @Transactional
    @Override
    public int unCheckedProduct(@Param("userId") int userId, @Param("productId") int productId) {
        return cartMapper.unCheckedProduct(userId,productId);
    }

    //查看商品数量
    @Transactional
    @Override
    public ServerResponse<Integer> findCount(@Param("userId") int userId) {
        return ServerResponse.createSuccessResponse(cartMapper.findCount(userId));
    }

    //全选
    @Transactional
    @Override
    public int allCheck(@Param("userId") int userId) {
        return cartMapper.allCheck(userId);
    }

    //取消全选
    @Transactional
    @Override
    public int allUnCheck(@Param("userId") int userId) {
        return cartMapper.allUnCheck(userId);
    }


}