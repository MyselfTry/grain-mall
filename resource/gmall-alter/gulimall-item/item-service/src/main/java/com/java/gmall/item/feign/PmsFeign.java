package com.java.gmall.item.feign;

import com.java.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/26 13:56
 */
@FeignClient("pms-server")
public interface PmsFeign extends GmallPmsApi{
}
