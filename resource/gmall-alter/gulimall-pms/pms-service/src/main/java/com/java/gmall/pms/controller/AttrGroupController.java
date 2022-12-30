package com.java.gmall.pms.controller;

import java.util.Arrays;
import java.util.List;


import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.pms.entity.AttrGroup;
import com.java.gmall.pms.service.AttrGroupService;
import com.java.gmall.pms.vo.GroupVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



/**
 * 属性分组
 * @author jiangli
 * @since  2020-01-10 04:05:29
 */
@Api(tags = "属性分组管理")
@RestController
@RequestMapping("pms/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

	@ApiOperation("根据三级分类id查询分组及组下的规格参数")
	@GetMapping("/withattrs/cat/{catId}")
	public Resp<List<GroupVO>> queryByCid(@PathVariable("catId")Long cid){

		List<GroupVO> attrGroupVOs = this.attrGroupService.queryByCid(cid);
		return Resp.ok(attrGroupVOs);
	}

	@ApiOperation("维护关联")
	@GetMapping("/withattr/{gid}")
	public Resp<GroupVO> queryGroupByGid(@PathVariable("gid")Long gid) {
		GroupVO groupVO = attrGroupService.queryGroupByGid(gid);
		return Resp.ok(groupVO);
	}

    @GetMapping("{catId}")
    public Resp<PageVo> queryGroupByPage(QueryCondition queryCondition,@PathVariable("catId")Long catId) {
        PageVo page = attrGroupService.queryGroupByPage(queryCondition,catId);
        return Resp.ok(page);
    }

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('pms:attrgroup:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = attrGroupService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{attrGroupId}")
    @PreAuthorize("hasAuthority('pms:attrgroup:info')")
    public Resp<AttrGroup> info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroup attrGroup = attrGroupService.getById(attrGroupId);

        return Resp.ok(attrGroup);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('pms:attrgroup:save')")
    public Resp<Object> save(@RequestBody AttrGroup attrGroup){
		attrGroupService.save(attrGroup);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('pms:attrgroup:update')")
    public Resp<Object> update(@RequestBody AttrGroup attrGroup){
		attrGroupService.updateById(attrGroup);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('pms:attrgroup:delete')")
    public Resp<Object> delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return Resp.ok(null);
    }

}
