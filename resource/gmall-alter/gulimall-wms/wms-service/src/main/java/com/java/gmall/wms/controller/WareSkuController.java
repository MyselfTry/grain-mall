package com.java.gmall.wms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.wms.entity.WareSku;
import com.java.gmall.wms.service.WareSkuService;
import com.java.gmall.wms.vo.SkuLockVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * 商品库存
 * @author jiangli
 * @since  2020-01-11 15:49:45
 */
@Api(tags = "商品库存管理")
@RestController
@RequestMapping("wms/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

	/**
	 * 检查并锁定库存
	 */
	@PostMapping
	public Resp<Object> checkAndLockStore(@RequestBody List<SkuLockVO> skuLockVOS) {
		String msg = wareSkuService.checkAndLockStore(skuLockVOS);
		if (StringUtils.isNotBlank(msg)) {
			return Resp.fail(msg);
		}
		return Resp.ok(null);
	}

	@ApiOperation("根据skuId查询库存信息")
	@GetMapping("{skuId}")
	public Resp<List<WareSku>> queryWareSkuBySkuId(@PathVariable("skuId")Long skuId){

		List<WareSku> wareSkuEntities = this.wareSkuService.list(new QueryWrapper<WareSku>().eq("sku_id", skuId));

		return Resp.ok(wareSkuEntities);
	}

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('wms:waresku:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = wareSkuService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('wms:waresku:info')")
    public Resp<WareSku> info(@PathVariable("id") Long id){
		WareSku wareSku = wareSkuService.getById(id);

        return Resp.ok(wareSku);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('wms:waresku:save')")
    public Resp<Object> save(@RequestBody WareSku wareSku){
		wareSkuService.save(wareSku);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('wms:waresku:update')")
    public Resp<Object> update(@RequestBody WareSku wareSku){
		wareSkuService.updateById(wareSku);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('wms:waresku:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
