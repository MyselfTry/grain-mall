package com.java.gmall.search.feign;

import com.java.gmall.pms.api.GmallPmsApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author jiangli
 * @since 2020/1/13 22:49
 */
@FeignClient("pms-server")
public interface GmallPmsFeign extends GmallPmsApi{
}
