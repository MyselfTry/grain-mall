package com.java.gmall.oms.api;

import com.java.gmall.oms.entity.Order;
import com.java.gmall.oms.vo.OrderSubmitVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author jiangli
 * @since 2020/1/30 11:44
 */
public interface OmsApi {

	@PostMapping("oms/order")
	Order saveOrder(@RequestBody OrderSubmitVO orderSubmitVO);
}
