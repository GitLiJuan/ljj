package com.company.dao.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28 0028.
 */
public class CartProductVoList {
    private List<CartProduct> cartProducts;
    private boolean allchecked;
    private BigDecimal cartTotalPrice;

    public CartProductVoList() {
    }

    public CartProductVoList(BigDecimal cartTotalPrice, List<CartProduct> cartProducts, boolean allchecked) {
        this.cartTotalPrice = cartTotalPrice;
        this.cartProducts = cartProducts;
        this.allchecked = allchecked;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public boolean isAllchecked() {
        return allchecked;
    }

    public void setAllchecked(boolean allchecked) {
        this.allchecked = allchecked;
    }

    public BigDecimal getCartTotalPrice() {
        return cartTotalPrice;
    }

    public void setCartTotalPrice(BigDecimal cartTotalPrice) {
        this.cartTotalPrice = cartTotalPrice;
    }

    @Override
    public String toString() {
        return "CartProductVoList{" +
                "cartProducts=" + cartProducts +
                ", allchecked=" + allchecked +
                ", cartTotalPrice=" + cartTotalPrice +
                '}';
    }
}
