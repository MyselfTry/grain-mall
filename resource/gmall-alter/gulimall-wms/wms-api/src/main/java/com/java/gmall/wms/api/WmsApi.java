package com.java.gmall.wms.api;

import com.java.core.bean.Resp;
import com.java.gmall.wms.vo.SkuLockVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author jiangli
 * @since 2020/1/30 18:24
 */
public interface WmsApi {

	/**
	 * 下单并锁定库存
	 */
	@PostMapping("wms/waresku")
	Resp<Object> checkAndLockStore(@RequestBody List<SkuLockVO> skuLockVOS);
}
