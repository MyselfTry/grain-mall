package com.java.gmall.pms.controller;

import java.util.Arrays;
import java.util.List;


import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.SpuInfo;
import com.java.gmall.pms.service.SpuInfoService;
import com.java.gmall.pms.vo.SpuInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;





/**
 * spu信息
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Api(tags = "spu信息管理")
@RestController
@RequestMapping("pms/spuinfo")
public class SpuInfoController {
    @Autowired
    private SpuInfoService spuInfoService;

	@ApiOperation("spu商品信息查询")
	@GetMapping
	public Resp<PageVo> querySpuInfo(QueryCondition condition, @RequestParam("catId")Long catId){

		PageVo page = this.spuInfoService.querySpuInfo(condition, catId);
		return Resp.ok(page);
	}

	/**
	 * 远程调用分页查询
	 */
	@PostMapping("page")
	public Resp<List<SpuInfo>> page(@RequestBody QueryCondition queryCondition) {
		PageVo<SpuInfo> spuInfoPageVo = spuInfoService.queryPage(queryCondition);
		List<SpuInfo> list = spuInfoPageVo.getList();
		return Resp.ok(list);
	}


    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:spuinfo:list')")
    public Resp<PageVo<SpuInfo>> list(QueryCondition queryCondition) {
        PageVo page = spuInfoService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('pms:spuinfo:info')")
    public Resp<SpuInfo> info(@PathVariable("id") Long id){
		SpuInfo spuInfo = spuInfoService.getById(id);

        return Resp.ok(spuInfo);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:spuinfo:save')")
    public Resp<Object> save(@RequestBody SpuInfoVO spuInfoVO){
	    spuInfoService.saveSpuInfoVO(spuInfoVO);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:spuinfo:update')")
    public Resp<Object> update(@RequestBody SpuInfo spuInfo){
	    spuInfoService.updateSpuInfo(spuInfo);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:spuinfo:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		spuInfoService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
