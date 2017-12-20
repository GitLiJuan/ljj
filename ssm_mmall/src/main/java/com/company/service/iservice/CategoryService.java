package com.company.service.iservice;

import com.company.common.ServerResponse;
import com.company.dao.pojo.Cart;
import com.company.dao.pojo.Category;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Whisper on 2017/11/28 0028.
 */
public interface CategoryService {
    //添加节点
    ServerResponse<Category> insert(Category category);
    //修改品类名称
    ServerResponse<Category> updateByPrimaryKeySelective(Category record);
    //根据id条件查询
    ServerResponse<List<Category>> getCategoryByConditions(Integer id);
    //根据id查询当前分类id以及递归子节点
    ServerResponse<List<Integer>> getDeepCategory(Integer id);
    PageInfo<Category> findAll();
}
