package com.java.gmall.oms.controller;

import java.util.Arrays;

import com.java.core.bean.PageVo;
import com.java.core.bean.QueryCondition;
import com.java.core.bean.Resp;
import com.java.gmall.oms.vo.OrderSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.java.gmall.oms.entity.Order;
import com.java.gmall.oms.service.OrderService;

/**
 * 订单
 * @author jiangli
 * @since  2020-01-30 10:17:57
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("oms/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

	@PostMapping
	public ResponseEntity<Order> saveOrder(@RequestBody OrderSubmitVO orderSubmitVO) {
		Order order = orderService.saveOrder(orderSubmitVO);
		return ResponseEntity.ok(order);
	}

    /**
     * 列表
     */
    @ApiOperation("分页查询(排序)")
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('oms:order:list')")
    public Resp<PageVo> list(QueryCondition queryCondition) {
        PageVo page = orderService.queryPage(queryCondition);

        return Resp.ok(page);
    }


    /**
     * 信息
     */
    @ApiOperation("详情查询")
    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('oms:order:info')")
    public Resp<Order> info(@PathVariable("id") Long id){
		Order order = orderService.getById(id);

        return Resp.ok(order);
    }

    /**
     * 保存
     */
    @ApiOperation("保存")
    @PostMapping("/save")
    @PreAuthorize("hasAuthority('oms:order:save')")
    public Resp<Object> save(@RequestBody Order order){
		orderService.save(order);

        return Resp.ok(null);
    }

    /**
     * 修改
     */
    @ApiOperation("修改")
    @PostMapping("/update")
    @PreAuthorize("hasAuthority('oms:order:update')")
    public Resp<Object> update(@RequestBody Order order){
		orderService.updateById(order);

        return Resp.ok(null);
    }

    /**
     * 删除
     */
    @ApiOperation("删除")
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('oms:order:delete')")
    public Resp<Object> delete(@RequestBody Long[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return Resp.ok(null);
    }

}
