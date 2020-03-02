package com.shopping.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shopping.entity.ProductInfo;
import com.shopping.vo.product.ProductInfoPram;
import com.shopping.vo.product.ProductInfoVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    @Select("SELECT a.*,b.currency_name FROM tb_product_info a Left join tb_currency b on a.currency_id=b.id " +
            " left join tb_supplier_info c on a.supplier_id = c.id " +
            " WHERE a.audit_status='1' and a.publish_status='1' and a.inventory>0 and c.supplier_status='0' order by sales_volume desc ")
    @Results(id = "userMap", value = {
            @Result(column = "product_id", property = "productId", javaType = String.class),
            @Result(column = "product_name", property = "productName", javaType = String.class),
            @Result(column = "one_category_id", property = "oneCategoryId", javaType = String.class),
            @Result(column = "two_category_id", property = "twoCategoryId", javaType = String.class),
            @Result(column = "three_category_id", property = "threeCategoryId", javaType = String.class),
            @Result(column = "supplier_id", property = "supplierId", javaType = String.class),
            @Result(column = "rmb_price", property = "rmbPrice", javaType = BigDecimal.class),
            @Result(column = "market_price", property = "marketPrice", javaType = BigDecimal.class),
            @Result(column = "cost_price", property = "costPrice", javaType = BigDecimal.class),
            @Result(column = "master_pic_url", property = "masterPicUrl", javaType = String.class),
            @Result(column = "inventory", property = "inventory", javaType = Long.class),
            @Result(column = "advertising_slogans", property = "advertisingSlogans", javaType = String.class),
            @Result(column = "currency_name", property = "currencyName", javaType = String.class)
    })

    List<ProductInfoVo> selectListByPage();

    /**
     * 商品列表
     * @param oneCategoryId
     * @return
     */
    @Select("<script>" +
            "SELECT a.*,b.currency_name FROM tb_product_info a " +
             "Left join tb_currency b on a.currency_id=b.id " +
            " WHERE a.audit_status='1' and a.publish_status='1' " +
            " and a.one_category_id=#{oneCategoryId} " +
            " order by sales_volume desc" +
            "</script>")
    @Results(id = "productMap", value = {
            @Result(column = "product_id", property = "productId", javaType = String.class),
            @Result(column = "product_name", property = "productName", javaType = String.class),
            @Result(column = "one_category_id", property = "oneCategoryId", javaType = String.class),
            @Result(column = "two_category_id", property = "twoCategoryId", javaType = String.class),
            @Result(column = "three_category_id", property = "threeCategoryId", javaType = String.class),
            @Result(column = "supplier_id", property = "supplierId", javaType = String.class),
            @Result(column = "rmb_price", property = "rmbPrice", javaType = BigDecimal.class),
            @Result(column = "market_price", property = "marketPrice", javaType = BigDecimal.class),
            @Result(column = "cost_price", property = "costPrice", javaType = BigDecimal.class),
            @Result(column = "master_pic_url", property = "masterPicUrl", javaType = String.class),
            @Result(column = "inventory", property = "inventory", javaType = Long.class),
            @Result(column = "advertising_slogans", property = "advertisingSlogans", javaType = String.class),
            @Result(column = "currency_name", property = "currencyName", javaType = String.class)
    })
    List<ProductInfo> getProduceInfoList(@Param(value = "oneCategoryId") String oneCategoryId);

}