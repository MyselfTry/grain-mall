package com.java.gmall.wms.vo;

import lombok.Data;

/**
 * @author jiangli
 * @since 2020/1/30 17:05
 */
@Data
public class SkuLockVO {

	private Long skuId;

	private Integer count;

	private Boolean locked; // 库存足够就锁定

	private Long wareId; // 锁定的仓库id

	private String orderToken;
}
