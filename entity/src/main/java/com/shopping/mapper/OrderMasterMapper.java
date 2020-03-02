package com.shopping.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.shopping.entity.OrderMaster;
import com.shopping.entity.User;
import com.shopping.vo.order.OrderVo;
import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface OrderMasterMapper extends BaseMapper<OrderMaster> {

    @Select("<script>" +
            "SELECT  T.* from ( " +
            "SELECT a.*,b.currency_name  FROM tb_order_master a  " +
            " left join  tb_currency b on a.currency_id=b.id" +
            " WHERE  1=1 " +
            " <when test='userParam.wxUUId!=null'>" +
            " and  a.wx_uuid =#{userParam.wxUUId} " +
            "  </when> " +
            " <when test='userParam.aliUUId !=null'>" +
            " and  a.ali_uuid =#{userParam.aliUUId} " +
            "  </when> " +
            " <when test='userParam.id!=null'>" +
            " or a.user_id =#{userParam.id} " +
            "  </when> " +
            " and a.is_delete='0'" +
            " )T " +
            "  WHERE T.order_status in('1','2') " +
            " order by T.create_time desc,T.modified_time desc" +
            " </script>")
    @Results(id = "orderResultMap", value = {
            @Result(column = "master_id", property = "masterId", javaType = String.class),
            @Result(column = "user_id", property = "userId", javaType = String.class),
            @Result(column = "wx_uuid", property = "wxUUId", javaType = String.class),
            @Result(column = "ali_uuid", property = "aliUUId", javaType = String.class),
            @Result(column = "table_no", property = "tableNo", javaType = String.class),
            @Result(column = "dinner_code", property = "dinnerCode", javaType = Long.class),
            @Result(column = "currency_id", property = "currencyId", javaType = String.class),
            @Result(column = "payment_money", property = "paymentMoney", javaType = BigDecimal.class),
            @Result(column = "order_status", property = "orderStatus", javaType = String.class),
            @Result(column = "pay_type", property = "payType", javaType = String.class),
            @Result(column = "paycoin_amount", property = "paycoinAmount", javaType = BigDecimal.class),
            @Result(column = "supplier_id", property = "supplierId", javaType = String.class),
            @Result(column = "supplier_name", property = "supplierName", javaType = String.class),
            @Result(column = "create_time", property = "createTime", javaType = String.class),
            @Result(column = "currency_name", property = "currencyName", javaType = String.class)
    })
    List<OrderVo> selectListbByType(@Param("userParam") User  user);
}