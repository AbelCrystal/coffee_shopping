package com.shopping.api;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shopping.entity.Advertisement;
import com.shopping.entity.Notice;
import com.shopping.entity.ProductInfo;
import com.shopping.entity.SupplierInfo;
import com.shopping.enums.AdvertEmums;
import com.shopping.enums.MessageEnums;
import com.shopping.service.index.AdvertisementService;
import com.shopping.service.index.NoticeService;
import com.shopping.service.index.SupplierInfoService;
import com.shopping.service.product.ProductInfoService;
import com.shopping.vo.BaseListResult;
import com.shopping.vo.MessageVO;
import com.shopping.vo.order.OrderVo;
import com.shopping.vo.product.ProductInfoVo;
import com.shopping.vo.index.IndexVo;
import com.shopping.vo.supplier.SupplierInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api(value = "/api/index", tags = "首页")
@RestController
@RequestMapping(value = "/api/index")
public class IndexApi {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private SupplierInfoService supplierInfoService;
    @Autowired
    private ProductInfoService productInfoService;

    @ApiOperation(value = "获取首页信息", notes = "获取首页信息包括banner图片，公告，商家信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @GetMapping("/getIndexInfo")
    public MessageVO<IndexVo> getIndexInfo(HttpServletRequest request) {
        String type = AdvertEmums.ADVERT_APP_INDEX.getCode();
        List<Advertisement> listAdvert = advertisementService.selectListByType(type);
        List<Notice> listNotice = noticeService.selectListByNowTime();
        IndexVo result = new IndexVo();
        result.setAdverts(listAdvert);
        result.setNotices(listNotice);
        return MessageVO.builder(result)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "获取供应商列表", notes = "获取供应商列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", name = "pageNum", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(value = "页面大小", name = "pageSize", paramType = "query", defaultValue = "10")
    })
    @GetMapping("/getSupplierList")
    public MessageVO<BaseListResult<List<SupplierInfo>>> getSupplierList(int pageNum, int pageSize, HttpServletRequest request) {
        Page<List<SupplierInfo>> page = PageHelper.startPage(pageNum, pageSize);
        List<SupplierInfo> listSupplier = supplierInfoService.selectListByName();
        List<SupplierInfoVo> supplierInfoVos = new ArrayList<>();
        for (SupplierInfo supplierInfo :
                listSupplier) {
            SupplierInfoVo vo = new SupplierInfoVo();
            BeanUtils.copyProperties(supplierInfo, vo);
            supplierInfoVos.add(vo);
        }
        BaseListResult listResult = new BaseListResult();
        listResult.setListReult(supplierInfoVos);
        listResult.setTotal(page.getTotal());
        listResult.setPageNum(pageNum);
        return MessageVO.builder(listResult)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "获取首页商品列表", notes = "获取首页商品列表", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "页数", name = "pageNum", paramType = "query"),
            @ApiImplicitParam(value = "页面大小", name = "pageSize", paramType = "query")
    })
    @GetMapping("/getIndexProductList")
    public MessageVO<BaseListResult<List<ProductInfoVo>>> getIndexProductList(int pageNum, int pageSize) {
        BaseListResult result = new BaseListResult();
        Page<List<ProductInfo>> page = PageHelper.startPage(pageNum, pageSize);
        List<ProductInfoVo> productInfos = productInfoService.selectListByPage();
        result.setListReult(productInfos);
        result.setTotal(page.getTotal());
        result.setPageNum(pageNum);
        return MessageVO.builder(result)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }

    @ApiOperation(value = "广告列表", notes = "type:0为移动端首页，1为移动端广告页，2为移动端起始页,3为PC端首页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(value = "类型", name = "type", paramType = "query")
    })
    @GetMapping("/getAdvertListByType")
    public MessageVO<List<Advertisement>> getAdvertListByType(String type) {
        List<Advertisement> listAdvert = advertisementService.selectListByType(type);
        return MessageVO.builder(listAdvert)
                .msgCode(MessageEnums.SUCCESS)
                .build();
    }
}
