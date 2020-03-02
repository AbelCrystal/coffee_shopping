package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;
@ApiModel(value = "意见反馈实体")
@Data
public class FeedbackRequest implements Serializable {
    @ApiModelProperty(name="refundType",notes = "意见反馈类型:1商品相关 2物流状况 3客户服务 4优惠活动 5产品体验 6产品功能 7其他",dataType = "String",required = true,allowEmptyValue = true)
    @NotEmpty(message="意见反馈类型不能为空")
    private String feedbackType;
    @ApiModelProperty(name="feedbackContent",notes = "意见反馈内容",dataType = "String",required = true,allowEmptyValue = false)
    @NotEmpty(message="意见反馈类型不能为空")
    private String feedbackContent;
    @ApiModelProperty(name="receiveMode",notes = "意见反馈内容",dataType = "String",required = false,allowEmptyValue = true)
    @NotEmpty(message="意见反馈内容不能为空")
    private String receiveMode;
    @ApiModelProperty(name="picList",notes = "图片地址",dataType = "List",required = false,allowEmptyValue = true)
    private List<String> picList;
}
