package com.shopping.vo.product;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "查询词条")
public class EntryVo implements Serializable {
    @ApiModelProperty(name = "id",notes = "id",dataType ="String")
    private String id;
    @ApiModelProperty(name = "name",notes = "名称",dataType ="String")
    private String name;
    @ApiModelProperty(name = "type",notes = "类型 1 分类；2 商家",dataType ="String")
    private String type;
    @ApiModelProperty(name = "level",notes = "分类级数 类型为分类时有",dataType ="String")
    private String level;

}