package com.company.controller;

import com.company.common.ServerResponse;
import com.company.dao.pojo.Category;
import com.company.service.iservice.CategoryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.UncategorizedMappingException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * Created by Whisper on 2017/11/28 0028.
 */
@Controller
@RequestMapping("manage/category")
@ResponseBody
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @RequestMapping(value = "add_category.do", method = RequestMethod.POST)
    //增加节点
    public ServerResponse<Category> addCategory(String categoryName, Integer parentId){
        Category category = new Category(parentId, categoryName);
        ServerResponse<Category> serverResponse = categoryService.insert(category);
        return serverResponse;
    }
    @RequestMapping(value = "set_category_name.do", method = RequestMethod.POST)
    //修改品类名字
    public ServerResponse<Category> setCategoryName(Integer categoryId, String categoryName){
        Category category = new Category();
        category.setId(categoryId);
        category.setName(categoryName);
        ServerResponse<Category> serverResponse = categoryService.updateByPrimaryKeySelective(category);
        return serverResponse;
    }
    @RequestMapping(value = "get_category.do", method = RequestMethod.POST)
    //获取品类子节点
    public ServerResponse<List<Category>> getCategory(Integer categoryId){
        return categoryService.getCategoryByConditions(categoryId);
    }
    @RequestMapping(value = "get_deep_category.do", method = RequestMethod.POST)
    public ServerResponse<List<Integer>> getDeepCategory(Integer categoryId){
        return categoryService.getDeepCategory(categoryId);
    }

    @RequestMapping(value = "findAll.do", method = RequestMethod.GET)
    public PageInfo<Category> findAll(){
        PageInfo<Category> pageInfo = categoryService.findAll();
        return pageInfo;
    }
}
