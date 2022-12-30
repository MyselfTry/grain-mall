package com.java.gmall.sms.controller;

import java.util.Arrays;
import java.util.List;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.sms.dto.SkuSaleDTO;
import com.java.gmall.sms.vo.SaleVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.java.gmall.sms.entity.SkuBounds;
import com.java.gmall.sms.service.SkuBoundsService;

/**
 * 商品sku积分设置
 * @author jiangli
 * @since  2020-01-11 18:31:30
 */
@Api(tags = "商品sku积分设置管理")
@RestController
@RequestMapping("sms/skubounds")
public class SkuBoundsController {
    @Autowired
    private SkuBoundsService skuBoundsService;

	/**
	 * 根据skuId查询营销信息
	 */
	@GetMapping("{skuId}")
	public ResponseEntity<List<SaleVO>> querySaleBySkuId(@PathVariable("skuId") Long skuId) {
		return ResponseEntity.ok(skuBoundsService.querySaleBySkuId(skuId));
	}


	@ApiOperation("新增sku的营销信息")
	@PostMapping("/sku/sale/save")
	public Resp<Object> saveSkuSaleInfo(@RequestBody SkuSaleDTO skuSaleDTO){
		this.skuBoundsService.saveSkuSaleInfo(skuSaleDTO);

		return Resp.ok(null);
	}

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sms:skubounds:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = skuBoundsService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sms:skubounds:info')")
    public Resp<SkuBounds> info(@PathVariable("id") Long id){
		SkuBounds skuBounds = skuBoundsService.getById(id);

        return Resp.ok(skuBounds);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sms:skubounds:save')")
    public Resp<Object> save(@RequestBody SkuBounds skuBounds){
		skuBoundsService.save(skuBounds);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sms:skubounds:update')")
    public Resp<Object> update(@RequestBody SkuBounds skuBounds){
		skuBoundsService.updateById(skuBounds);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sms:skubounds:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		skuBoundsService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
