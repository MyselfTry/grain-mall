package com.java.gmall.wms.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 库存工作单
 * @author jiangli
 * @since 2020-01-11 15:49:45
 */
@ApiModel
@Data
@TableName("wms_ware_order_task_detail")
public class WareOrderTaskDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * sku_id
	 */
	@ApiModelProperty(name = "skuId",value = "sku_id")
	private Long skuId;
	/**
	 * sku_name
	 */
	@ApiModelProperty(name = "skuName",value = "sku_name")
	private String skuName;
	/**
	 * 购买个数
	 */
	@ApiModelProperty(name = "skuNum",value = "购买个数")
	private Integer skuNum;
	/**
	 * 工作单id
	 */
	@ApiModelProperty(name = "taskId",value = "工作单id")
	private Long taskId;

}
