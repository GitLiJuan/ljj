package com.company.dao.idao;

import com.company.dao.pojo.Product;
import com.company.vo.ProductDetailVo;
import com.company.vo.ProductListVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductMapper {
    //新增商品
    int insert(Product record);
    //动态生成
    int insertSelective(Product record);
    int updateByPrimaryKeySelective(Product record);
    //更新产品
    int updateByPrimaryKey(Product record);
    //后台：查询商品列表
    List<ProductListVo> findAll();
    //根据id,商品名查询商品
    List<ProductListVo>  selectByIdOrName(Product record);

    //前台：查询商品详情
    Product selectdetail(Integer id);
    //商品下架（修改状态）
    int up(@Param("id") Integer id, @Param("status") Integer status);
    //商品下架（修改状态）
    int down(@Param("id") Integer id, @Param("status") Integer status);

    //前台：商品查询及动态排序
    List<ProductListVo> orderByprice(@Param("name") String name,@Param("categoryId") Integer categoryId,@Param("flag") Integer flag);
    //后台：查询商品详情
    ProductDetailVo selectmandetail(Integer id);


    Product selectByPrimaryKey(Integer productId);
}