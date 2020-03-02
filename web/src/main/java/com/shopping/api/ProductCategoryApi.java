package com.shopping.api;

import com.shopping.entity.ProductCategory;
import com.shopping.enums.MessageEnums;
import com.shopping.service.product.ProductCategoryService;
import com.shopping.vo.MessageVO;
import com.shopping.vo.product.ProductCategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author abel
 * @date 2019-05-30 20:45
 */
@Api(value = "/api/category", tags = "商品分类菜单")
@RestController
@RequestMapping(value = "/api/category")
public class ProductCategoryApi {
    Logger logger = LoggerFactory.getLogger(ProductCategoryApi.class);
    @Autowired
    private ProductCategoryService productCategoryService;


    @PostMapping("/getAllCategory")
    @ApiOperation(value = "查询所有分类", notes = "查询所有分类", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public MessageVO<List<ProductCategoryVo>> getAllCategory() {
        List<ProductCategory> firstLeavelList = productCategoryService.findFirstLevel();
        return MessageVO.builder(firstLeavelList)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }
}
