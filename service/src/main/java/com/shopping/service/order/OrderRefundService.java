package com.shopping.service.order;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.shopping.entity.OrderMaster;
import com.shopping.entity.OrderRefundRecord;
import com.shopping.entity.OrderRefunds;
import com.shopping.entity.OrderRefundsGoods;
import com.shopping.enums.OrderEmums;
import com.shopping.enums.PictureSourceEnums;
import com.shopping.enums.RefundEmums;
import com.shopping.mapper.OrderRefundsGoodsMapper;
import com.shopping.mapper.OrderRefundsMapper;
import com.shopping.unit.DateUtils;
import com.shopping.unit.IdWorker;
import com.shopping.unit.OSSConfig;
import com.shopping.unit.OrderNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service("orderRefundService")
public class OrderRefundService extends ServiceImpl<OrderRefundsMapper, OrderRefunds> {

    @Autowired
    private OSSConfig ossConfig;
    @Autowired
    private OrderMasterService orderMasterService;
    @Autowired
    private OrderRefundService orderRefundService;
    @Autowired
    private OrderRefundRecordService orderRefundRecordService;
    @Autowired
    private PictureService pictureService;

    @Transactional
    public boolean refund(OrderRefunds orderRefunds, List<String> files) throws RuntimeException{
        boolean flag = false;
        //上传图片并保存数据库
        this.pictureService.uploadPicture(orderRefunds.getId(), PictureSourceEnums.REFUND_MONEY.getCode(), PictureSourceEnums.REFUND_MONEY.getDesc(), files);
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setMasterId(orderRefunds.getOrderId());
        orderMaster.setOrderStatus(OrderEmums.ORDEER_STATE_APPLICATION_REFUND.getCode());
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
        orderRefundRecord.setRefundType(RefundEmums.REFUND.getCode());
        orderRefundRecord.setCreateTime(DateUtils.getDBDate());
        this.orderRefundRecordService.insert(orderRefundRecord);
        flag = true;
        return flag;
    }
}
