package com.java.gmall.order.feign;

import com.java.gmall.wms.api.WmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/31 11:28
 */
@FeignClient("wms-server")
public interface WmsFeign extends WmsApi {
}
