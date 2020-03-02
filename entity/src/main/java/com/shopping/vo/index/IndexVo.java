package com.shopping.vo.index;

import com.shopping.entity.Advertisement;
import com.shopping.entity.Notice;
import com.shopping.vo.supplier.SupplierInfoVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "首页广告，公告")
@Data
public class IndexVo implements Serializable {
    @ApiModelProperty(name = "adverts",notes = "首页广告",dataType ="List")
    private List<Advertisement> adverts;
    @ApiModelProperty(name = "notices",notes = "公告",dataType = "List")
    private List<Notice> notices;
//    @ApiModelProperty(name = "supplier",notes = "商户列表",dataType = "List")
//    private List<SupplierInfoVo> supplier;
}
