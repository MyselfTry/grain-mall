package com.java.gmall.ums.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 成长值变化历史记录
 * @author jiangli
 * @since 2020-01-27 19:50:04
 */
@ApiModel
@Data
@TableName("ums_growth_change_history")
public class GrowthChangeHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty(name = "id",value = "id")
	private Long id;
	/**
	 * member_id
	 */
	@ApiModelProperty(name = "memberId",value = "member_id")
	private Long memberId;
	/**
	 * create_time
	 */
	@ApiModelProperty(name = "createTime",value = "create_time")
	private Date createTime;
	/**
	 * 改变的值（正负计数）
	 */
	@ApiModelProperty(name = "changeCount",value = "改变的值（正负计数）")
	private Integer changeCount;
	/**
	 * 备注
	 */
	@ApiModelProperty(name = "note",value = "备注")
	private String note;
	/**
	 * 积分来源[0-购物，1-管理员修改]
	 */
	@ApiModelProperty(name = "sourceType",value = "积分来源[0-购物，1-管理员修改]")
	private Integer sourceType;

}
