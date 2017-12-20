package com.company.controller;

import com.company.common.Const;
import com.company.common.ServerResponse;
import com.company.dao.pojo.Product;
import com.company.service.iservice.ProductService;
import com.company.util.Base64;
import com.company.vo.ProductDetailVo;
import com.company.vo.ProductImageVO;
import com.company.vo.ProductListVo;
import com.github.pagehelper.PageInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/28.
 */
@Controller
@RequestMapping("manage/product")
public class MangerProductController {
    @Autowired
    private ProductService productservice;

    //更新、增加商品
    @RequestMapping(value = "save.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Product> insertorupdate(Product product, HttpSession session) {
        ServerResponse responseResult = productservice.insertorupdate(product);
        if (responseResult.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, responseResult.getData());
        }
        return responseResult;
    }

    //后台：查询商品列表
    @RequestMapping(value = "list.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<PageInfo<ProductListVo>> findall(Integer PageNum, Integer PageSize, HttpSession session) {
        if (PageSize == null) {
            PageSize = 10;
        }
        if (PageNum == null) {
            PageNum = 1;
        }
        ServerResponse responseResult = productservice.findAll(PageNum, PageSize);
        if (responseResult.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, responseResult.getData());
        }
        return responseResult;
    }

    //根据id或商品名查找商品
    @RequestMapping(value = "search.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductListVo> selectbyidorname(Integer PageNum, Integer PageSize, Product product, HttpSession session) {
        if (PageSize == null) {
            PageSize = 10;
        }
        if (PageNum == null) {
            PageNum = 1;
        }

        ServerResponse responseResult = productservice.selectByIdOrName(PageNum, PageSize, product);
        if (responseResult.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, responseResult.getData());
        }
        return responseResult;
    }

    //商品详情
    @RequestMapping(value = "detail.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<ProductDetailVo> selectmandetail(Integer productId, HttpSession session) {
        ServerResponse responseResult = productservice.selectmandetail(productId);
        if (responseResult.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, responseResult.getData());
        }
        return responseResult;
    }

    //商品上下架
    @RequestMapping(value = "set_sale_status.do", method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<Product> upanddown( Integer productId, Integer status, HttpSession session) {
        ServerResponse responseResult = productservice.upAndDown(productId, status);
        if (responseResult.isSuccess()) {
            session.setAttribute(Const.CURRENT_USER, responseResult.getData());
        }
        return responseResult;
    }

  //图片上传
    @RequestMapping(value = "upload.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<ProductImageVO> upload(@RequestParam("fileFromClient") MultipartFile sourceFile, HttpSession session) throws Exception {
        if (!sourceFile.isEmpty()) {
            File targetFile = new File("D:/3_develop/temp",
                    System.currentTimeMillis() + sourceFile.getOriginalFilename());
            FileUtils.copyInputStreamToFile(sourceFile.getInputStream(), targetFile);
            String fileName = targetFile.getName();
            String ftpName = "http://img.emall.com/" + fileName;
            ProductImageVO productImageVO = new ProductImageVO();
            productImageVO.setUri(fileName);
            productImageVO.setUrl(ftpName);
            return ServerResponse.createSuccessResponse(productImageVO);
        }
        return ServerResponse.createErrorMessageResponse("上传错误");
    }
   //多张图片上传
    @RequestMapping(value = "richtext_img_upload.do", method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<ProductImageVO>> richtextimgupload(@RequestParam("fileFromClient") List<MultipartFile> sourceFiles, HttpSession session) throws Exception {
       List<ProductImageVO> list = new ArrayList<>();
       try {
           for (MultipartFile sourceFile : sourceFiles) {
               if (!sourceFile.isEmpty()) {
                   File targetFile = new File("D:/3_develop/temp",
                           System.currentTimeMillis() + sourceFile.getOriginalFilename());
                   FileUtils.copyInputStreamToFile(sourceFile.getInputStream(), targetFile);
                   String fileName = targetFile.getName();
                   String ftpName = "http://img.emall.com/" + fileName;
                   ProductImageVO productImageVO = new ProductImageVO();
                   productImageVO.setUri(fileName);
                   productImageVO.setUrl(ftpName);
                    list.add(productImageVO);
               }

           }
           return ServerResponse.createSuccessResponse(list);
       }catch (Exception e){
           e.printStackTrace();
           return ServerResponse.createErrorMessageResponse("上传错误");
       }
    }



}