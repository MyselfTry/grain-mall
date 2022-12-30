package com.java.gmall.pms.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.java.core.bean.Resp;
import com.java.core.exception.RRException;
import com.java.gmall.pms.config.ConstantProperties;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jiangli
 * @since 2020/1/11 9:26
 */
@RestController
@RequestMapping("/pms/oss")
public class OSSController {

	@GetMapping("/policy")
	public Resp policy() {
		String accessId = ConstantProperties.ACCESS_KEY_ID; // 请填写您的AccessKeyId。
		String accessKey = ConstantProperties.ACCESS_KEY_SECRET; // 请填写您的AccessKeySecret。
		String endpoint = ConstantProperties.END_POINT; // 请填写您的 endpoint。
		String bucket = ConstantProperties.BUCKET_NAME; // 请填写您的 bucketname 。
		String host = "https://" + bucket + "." + endpoint; // host的格式为 bucketname.endpoint
		String dir = ConstantProperties.FILE_HOST + "/" + new DateTime().toString("yyyy/MM/dd"); // 用户上传文件时指定的前缀。

		OSSClient client = new OSSClient(endpoint, accessId, accessKey);
		try {
			long expireTime = 30;
			long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
			Date expiration = new Date(expireEndTime);
			PolicyConditions policyConds = new PolicyConditions();
			policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
			policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

			String postPolicy = client.generatePostPolicy(expiration, policyConds);
			byte[] binaryData = postPolicy.getBytes("utf-8");
			String encodedPolicy = BinaryUtil.toBase64String(binaryData);
			String postSignature = client.calculatePostSignature(postPolicy);

			Map<String, String> respMap = new LinkedHashMap<String, String>();
			respMap.put("accessid", accessId);
			respMap.put("policy", encodedPolicy);
			respMap.put("signature", postSignature);
			respMap.put("dir", dir);
			respMap.put("host", host);
			respMap.put("expire", String.valueOf(expireEndTime / 1000));

			return Resp.ok(respMap);
		} catch (Exception e) {
			throw new RRException("获取OSS上传签名失败");
		}

	}
}
