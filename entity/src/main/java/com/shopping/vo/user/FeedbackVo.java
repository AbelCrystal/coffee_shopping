package com.shopping.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "意见反馈实体")
public class FeedbackVo implements Serializable {

    @ApiModelProperty(name="feedbackId",notes = "意见反馈id",dataType = "String",required = true,allowEmptyValue = false)
    private String feedbackId;

    @ApiModelProperty(name="feedbackUserId",notes="反馈人id",dataType = "String",required = true,allowEmptyValue = false)
    private String feedbackUserId;

    @ApiModelProperty(name="feedbackType",notes = "反馈类型",dataType = "String",required = true,allowEmptyValue = false)
    private String feedbackType;

    @ApiModelProperty(name="feedbackContent",notes = "反馈内容",dataType = "String",required = true,allowEmptyValue = false)
    private String feedbackContent;

    @ApiModelProperty(name="receiveMode",notes = "接收方式",dataType = "String",required = true,allowEmptyValue = false)
    private String receiveMode;

}
