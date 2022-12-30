package com.java.gmall.order.feign;

import com.java.gmall.oms.api.OmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/30 11:44
 */
@FeignClient("oms-server")
public interface OmsFeign extends OmsApi {
}
