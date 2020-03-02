package com.shopping.api;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.shopping.annotation.UserLoginToken;
import com.shopping.entity.OrderCartBean;
import com.shopping.entity.ProductInfo;
import com.shopping.entity.User;
import com.shopping.enums.GlobalIsDeleteEnums;
import com.shopping.enums.MessageEnums;
import com.shopping.entity.*;
import com.shopping.enums.*;
import com.shopping.redis.RedisUtil;
import com.shopping.service.mq.MQSender;
import com.shopping.service.mq.OrderCardQueueMessage;
import com.shopping.service.mq.OrderCardReturnMessage;
import com.shopping.service.mq.OrdersQueueMessage;
import com.shopping.service.order.OrderMasterService;
import com.shopping.service.product.ProductInfoService;
import com.shopping.service.user.OrderCartService;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.OrderNum;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.CartOrdersV0;
import com.shopping.vo.order.ClearOderCartRequest;
import com.shopping.vo.order.ProductToCartVo;
import com.shopping.vo.order.UserParame;
import com.shopping.vo.user.OrderCartVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Api(value = "/api/user", tags = "个人中心功能")
@RestController
@RequestMapping(value = "/api/user")
public class OrderCartApi {
    Logger logger = LoggerFactory.getLogger(OrderCartApi.class);

    @Autowired
    private OrderCartService orderCartService;

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderMasterService orderMasterService;

    @Autowired
    private MQSender sender;


    /**
     * @description: 添加商品进购物车
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/25 0025 17:08
     */
    @ApiOperation(value = "添加商品进购物车", notes = "添加多个商品进购物车", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/addProductToCart")
    @UserLoginToken
    public MessageVO addProductToCart(@RequestBody ProductToCartVo productInfoParam, HttpSession session) {
        try {
            User user = (User) session.getAttribute(session.getId());
            OrderCartBean orderCartBean = new OrderCartBean();
            EntityWrapper<ProductInfo> qryCurrencyBeanWrapper = new EntityWrapper<>();
            qryCurrencyBeanWrapper.eq("product_id", productInfoParam.getProductId());
            ProductInfo productInfo = productInfoService.selectOne(qryCurrencyBeanWrapper); //获取商品信息
            if (null == productInfo) {
                return MessageVO.builder().msgCode(MessageEnums.ADD_FAIL).build();
            }
            UserParame userParame = new UserParame();
            if (user != null) {
                BeanUtils.copyProperties(user, userParame);
            }
            userParame.setProductId(productInfoParam.getProductId());
            userParame.setProductAttr(productInfoParam.getProductAttrs());
            OrderCartBean orderCartBean1 = orderCartService.selectByProductId(userParame);
            if (Objects.isNull(orderCartBean1)) {
                orderCartBean.setCartId(IdWorker.getNewInstance().nextIdToString());
                orderCartBean.setProductId(productInfoParam.getProductId());
                orderCartBean.setSupplierId(productInfo.getSupplierId());   //供应商id
                orderCartBean.setProductImage(productInfo.getMasterPicUrl());
                orderCartBean.setProductName(productInfo.getProductName()); //商品名称
                orderCartBean.setRmbPrice(productInfoParam.getRmbPrice());  //人民币价格
                orderCartBean.setUserId(user.getId());
                orderCartBean.setAliUUId(user.getAliUUId());
                orderCartBean.setWxUUId(user.getWxUUId());
                orderCartBean.setProductAttr(productInfoParam.getProductAttrs());
                orderCartBean.setCreateTime(DateUtils.getDBDate());
                orderCartBean.setUpdateTime(DateUtils.getDBDate());
                orderCartBean.setProductCount(productInfoParam.getProductCount());
                orderCartBean.setIsDelete(GlobalIsDeleteEnums.IS_NORMAL.getCode());
                orderCartService.insert(orderCartBean);
            } else {
                //存在该商品则累加
                OrderCartBean orderCartBean2 = new OrderCartBean();
                orderCartBean2.setCartId(orderCartBean1.getCartId());
                orderCartBean2.setProductCount(orderCartBean1.getProductCount() + productInfoParam.getProductCount()); //累加商品
                orderCartBean2.setUpdateTime(DateUtils.getDBDate());
                orderCartService.updateById(orderCartBean2);
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder().msgCode(MessageEnums.ADD_SUCCESS).build();
    }


    /**
     * @description:查询用户购物车信息 默认查询所有
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/25 0025 17:30
     */
    @ApiOperation(value = "查询用户购物车信息", notes = "查询用户购物车信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/findCarts")
    @UserLoginToken
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "页数", required = false, dataType = "int", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", required = false, dataType = "int", defaultValue = "10"),
            @ApiImplicitParam(name = "cartId", value = "购物车id", required = false, dataType = "String"),
    })
    public MessageVO<BaseListResult<List<OrderCartVo>>> findCarts(@RequestParam(required = false) String cartId,
                                                                  @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                                                  @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                                  HttpSession session) {
        BaseListResult<List<OrderCartVo>> baseListResult = new BaseListResult<>();
        User user = (User) session.getAttribute(session.getId());
        UserParame userParame = new UserParame();
        if (user != null) {
            BeanUtils.copyProperties(user, userParame);
        }
        userParame.setCartId(cartId);
        try {
            Page<List<OrderCartBean>> page = PageHelper.startPage(pageNum, pageSize);
            List<OrderCartBean> orderCartList = orderCartService.selectListById(userParame);
            List<OrderCartVo> orderCartVoList = new ArrayList<>();
            for (OrderCartBean orderCartBean : orderCartList) {
                OrderCartVo orderCartVo = new OrderCartVo();
                BeanUtils.copyProperties(orderCartBean, orderCartVo);
                orderCartVoList.add(orderCartVo);
            }
            baseListResult.setListReult(orderCartVoList);
            baseListResult.setTotal(page.getTotal());
            baseListResult.setPageNum(pageNum);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder(baseListResult)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }


    /**
     * @description:修改购物车内订单信息
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/25 0025 17:30
     */
    @ApiOperation(value = "修改购物车内订单信息", notes = "修改购物车内订单信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/updateCartInfo")
    @UserLoginToken
    public MessageVO updateCartInfo(@RequestParam(value = "cartId", required = true) String cartId
                                    ,@RequestParam(value = "productCount", required = true) Integer productCount,
                                    HttpSession session) throws Exception {
        try {
            User user = (User) session.getAttribute(session.getId());
            UserParame userParame = new UserParame();
            if (user != null) {
                BeanUtils.copyProperties(user, userParame);
            }
            userParame.setCartId(cartId);
            List<OrderCartBean> orderCartBeanList = orderCartService.selectListById(userParame);
            if (orderCartBeanList != null && orderCartBeanList.size() > 0) {
                if (productCount > 0) {
                    OrderCartBean orderCartBean = orderCartBeanList.get(0);   //根据id查询到加入购物车的订单信息
                    orderCartBean.setProductCount(productCount);
                    orderCartBean.setUpdateTime(DateUtils.getDBDate());
                    orderCartService.updateById(orderCartBean);
                } else {
                    EntityWrapper<OrderCartBean> qryOrderCartBeanWrapper = new EntityWrapper<>();
                    qryOrderCartBeanWrapper.eq("cart_id", cartId);
                    orderCartService.delete(qryOrderCartBeanWrapper);
                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder()
                .msgCode(MessageEnums.UPDATE_SUCCESS)
                .build();
    }

    /**
     * @description:清除单个或清空购物车 不填id 则清空
     * @param:
     * @return:
     * @auther: Darryl_Tang
     * @time: 2019/6/25 0025 17:50
     */
    @ApiOperation(value = "清除购物车商品", notes = "清除购物车商品", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/deleteCarts")
    @UserLoginToken
    public MessageVO deleteCarts(@RequestBody ClearOderCartRequest requestVo, HttpSession session) {
        try {
            User user = (User) session.getAttribute(session.getId());
            orderMasterService.clearOrderCard(user);//清空购物车
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder()
                .msgCode(MessageEnums.DELETE_SUCCESS)
                .build();

    }

    @ApiOperation(value = "提交购物车订单", notes = "提交购物车订单", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @PostMapping("/addCartOrders")
    @UserLoginToken
    public MessageVO<String> addProductOrders(@Valid @RequestBody CartOrdersV0 orderCartVo,
                                              HttpSession session) throws Exception {
        String orderId = IdWorker.getNewInstance().nextIdToString();
        try {
            User user = (User) session.getAttribute(session.getId());
            if (user.getTableNo() == null && user.getSupplierId() == null) {
                return MessageVO.builder()
                        .msgCode(MessageEnums.ALIWX_AUTH)
                        .build();
            }
            OrderCardQueueMessage orderCardQueueMessage = new OrderCardQueueMessage();
            BeanUtils.copyProperties(user, orderCardQueueMessage);
            orderCardQueueMessage.setCartIdArray(orderCartVo.getCartIdArray());
            orderCardQueueMessage.setOrderSrc(orderCartVo.getOrderSrc());
            orderCardQueueMessage.setUserId(user.getId());
            orderCardQueueMessage.setOrderId(orderId);
            orderCardQueueMessage.setSupplierId(user.getSupplierId());
            orderCardQueueMessage.setTableNo(user.getTableNo());
            sender.sendOrderCardMessage(orderCardQueueMessage);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new RuntimeException(MessageEnums.API_ERROR.getDesc());
        }
        return MessageVO.builder(orderId)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }
}
