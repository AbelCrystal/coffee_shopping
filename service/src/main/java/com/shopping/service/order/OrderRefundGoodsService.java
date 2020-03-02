package com.shopping.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.*;
import com.shopping.enums.OrderEmums;
import com.shopping.enums.PictureSourceEnums;
import com.shopping.enums.RefundEmums;
import com.shopping.mapper.OrderRefundsGoodsMapper;
import com.shopping.unit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("orderRefundGoodsService")
public class OrderRefundGoodsService extends ServiceImpl<OrderRefundsGoodsMapper, OrderRefundsGoods> {

    @Autowired
    private OSSConfig ossConfig;
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private OrderRefundGoodsService orderRefundService;
    @Autowired
    private OrderRefundRecordService orderRefundRecordService;
    @Autowired
    private PictureService pictureService;

    @Transactional
    public boolean refundGoogs(OrderRefundsGoods orderRefunds, List<String> files) throws RuntimeException{
        boolean flag = false;
        //上传图片并保存数据库
        if (files != null) {
            this.pictureService.uploadPicture(orderRefunds.getId(), PictureSourceEnums.REFUND.getCode(), PictureSourceEnums.REFUND.getDesc(), files);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setMasterId(orderRefunds.getOrderId());
        orderMaster.setOrderStatus(OrderEmums.ORDEER_STATE_APPLICATION_REFUNDALL.getCode());
        orderMaster.setModifiedTime(DateUtils.getDBDate());
        this.orderMasterService.updateById(orderMaster);
        //新增退款申请
        orderRefunds.setRefundStatus(RefundEmums.APPLICATION_REFUND.getCode());
        orderRefunds.setRefundRemark(RefundEmums.APPLICATION_REFUND.getDesc());
        orderRefunds.setRefundTradeNo("T" + OrderNum.nextNum(20));
        orderRefunds.setCreateTime(DateUtils.getDBDate());
        this.orderRefundService.insert(orderRefunds);
        //新增退款记录
        OrderRefundRecord orderRefundRecord = new OrderRefundRecord();
        orderRefundRecord.setId(IdWorker.getNewInstance().nextIdToString());
        orderRefundRecord.setRefundId(orderRefunds.getId());
        orderRefundRecord.setAuditorState(RefundEmums.APPLICATION_REFUND.getCode());
        orderRefundRecord.setStateDes(RefundEmums.APPLICATION_REFUND.getDesc());
        orderRefundRecord.setRefundType(RefundEmums.RETURN_GOODS.getCode());
        orderRefundRecord.setCreateTime(DateUtils.getDBDate());
        this.orderRefundRecordService.insert(orderRefundRecord);
        flag = true;
        return flag;
    }
}
