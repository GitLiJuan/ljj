package com.company.dao.idao;

import com.company.dao.pojo.Cart;
import com.company.dao.pojo.Category;
import com.github.pagehelper.PageHelper;

import java.util.List;
import java.util.Map;

public interface CategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);

    //根据id条件查询
    List<Category> getCategoryByConditions(Map<String, Object> map);
    //获取当前分类id以及递归子节点
    List<Integer> getDeepCategory(Integer id);
    List<Category> findAll();
}