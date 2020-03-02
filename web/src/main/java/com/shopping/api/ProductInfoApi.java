package com.shopping.api;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shopping.entity.*;
import com.shopping.enums.MessageEnums;
import com.shopping.enums.ProductAuditEnums;
import com.shopping.enums.ProductPublishEnums;
import com.shopping.service.index.SupplierInfoService;
import com.shopping.service.product.*;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.product.*;
import com.shopping.vo.supplier.SupplierInfoVo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author abel
 * @date 2019-05-30 20:45
 */
@Api(value = "/api/product", tags = "商品信息")
@RestController
@RequestMapping(value = "/api/product")
public class ProductInfoApi {
    Logger logger = LoggerFactory.getLogger(ProductInfoApi.class);

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductAttributeService productAttributeService;
    @Autowired
    private ProductSpecService productSpecService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/getProduct")
    @ApiOperation(value = "查询商品列表", notes = "查询商品列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oneCategoryId", value = "一级分类", required = false, dataType = "String")
    })
    public MessageVO<ProductInfoResultVo> getProduct(
            @RequestParam(required = false) String oneCategoryId
    ) {
        ProductInfoResultVo productInfoResultVo=new ProductInfoResultVo();
        List<ProductCategory> firstLeavelList = productCategoryService.findFirstLevel();
        List<ProductByCategoryVo> listProduct=new ArrayList<>();
        if(StringUtils.isEmpty(oneCategoryId)) {
            for (ProductCategory productCategory :
                    firstLeavelList) {
                ProductByCategoryVo productByCategoryVo = new ProductByCategoryVo();
                BeanUtils.copyProperties(productCategory, productByCategoryVo);
                List<ProductInfo> produceInfoList = productInfoService.getProduceInfoList(productCategory.getCategoryId());
                productByCategoryVo.setProductInfos(produceInfoList);
                listProduct.add(productByCategoryVo);
            }
        }else {
            EntityWrapper<ProductCategory> qryProductCategoryWrapper = new EntityWrapper<>();
            qryProductCategoryWrapper.eq("category_id", oneCategoryId);
            ProductCategory productCategory = productCategoryService.selectOne(qryProductCategoryWrapper);
            List<ProductInfo> produceInfoList = productInfoService.getProduceInfoList(oneCategoryId);
            ProductByCategoryVo productByCategoryVo=new ProductByCategoryVo();
            productByCategoryVo.setProductInfos(produceInfoList);
            BeanUtils.copyProperties(productCategory, productByCategoryVo);
            listProduct.add(productByCategoryVo);
        }
        productInfoResultVo.setProductCategories(firstLeavelList);
        productInfoResultVo.setListProduct(listProduct);
        return MessageVO.builder(productInfoResultVo)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @PostMapping("/getProductById")
    @ApiOperation(value = "查询单个商品详情", notes = "查询单个商品详情", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)

    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品id", required = false, dataType = "String"),
    })
    public MessageVO<ProductInfoVo> getProductById(String productId) {
        EntityWrapper<ProductInfo> qryProductInfoWrapper = new EntityWrapper<>();
        qryProductInfoWrapper.eq("product_id", productId);
        ProductInfo productInfo = productInfoService.selectOne(qryProductInfoWrapper);
        EntityWrapper<ProductAttribute> qryWrapper = new EntityWrapper<>();
        qryWrapper.eq("product_id", productId);
        List<ProductAttribute> attributes = productAttributeService.selectList(qryWrapper);
        Map<String, List<ProductAttribute>> mapAttr = new HashMap<>();
        if (attributes != null && attributes.size() > 0) {
            mapAttr = attributes.stream().collect(Collectors.groupingBy(ProductAttribute::getAttrName));
        }

        EntityWrapper<ProductSpec> qrySpec = new EntityWrapper<>();
        qrySpec.eq("product_id", productId);
        List<ProductSpec> attrSpec = productSpecService.selectList(qrySpec);
//        Map<String, List<ProductSpec>> mapSpec = new HashMap<>();
//        if (attrSpec != null && attrSpec.size() > 0) {
//            mapSpec = attrSpec.stream().collect(Collectors.groupingBy(ProductSpec::getSpecName));
//        }

        ProductInfoVo productInfoVo = new ProductInfoVo();
        if (productInfo != null) {
            BeanUtils.copyProperties(productInfo, productInfoVo);
            productInfoVo.setAtts(mapAttr);
            productInfoVo.setSpecs(attrSpec);
        } else {
            return MessageVO.builder(productInfoVo)
                    .msgCode(MessageEnums.FAIL)
                    .build();
        }
        return MessageVO.builder(productInfoVo)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

}
