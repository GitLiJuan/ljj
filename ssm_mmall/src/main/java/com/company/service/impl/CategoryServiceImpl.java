package com.company.service.impl;

import com.company.common.ServerResponse;
import com.company.dao.idao.CategoryMapper;
import com.company.dao.pojo.Cart;
import com.company.dao.pojo.Category;
import com.company.service.iservice.CategoryService;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Whisper on 2017/11/28 0028.
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Transactional
    @Override
    //添加节点
    public ServerResponse<Category> insert(Category category) {
        if(category.getParentId() == null){
            category.setParentId(0);
        }
        int flag = categoryMapper.insert(category);
        if(flag == 1)
            return ServerResponse.createSuccessMessageResponse("添加节点成功!");
        return ServerResponse.createErrorMessageResponse("添加节点失败!");

    }
    @Transactional
    @Override
    public ServerResponse<Category> updateByPrimaryKeySelective(Category record) {
        int flag = categoryMapper.updateByPrimaryKeySelective(record);
        if(flag == 1)
            return ServerResponse.createSuccessMessageResponse("更新节点成功!");
        return ServerResponse.createErrorMessageResponse("更新节点失败!");
    }
    @Transactional
    @Override
    public ServerResponse<List<Category>> getCategoryByConditions(Integer id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        List<Category> list = categoryMapper.getCategoryByConditions(map);
        if (list != null && list.size() > 0)
            return ServerResponse.createSuccessResponse(list);
        return ServerResponse.createErrorMessageResponse("未找到该品类!");
    }
    @Transactional
    @Override
    public ServerResponse<List<Integer>> getDeepCategory(Integer id) {

        List<Integer> list = categoryMapper.getDeepCategory(id);
        if(list.size() > 0 && list != null)
            return ServerResponse.createSuccessResponse(list);
        return ServerResponse.createErrorMessageResponse("查找失败!");
    }



    @Transactional
    @Override
    public PageInfo<Category> findAll() {
        PageHelper.startPage(1, 4);
        List<Category> list = categoryMapper.findAll();
        PageInfo<Category> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
