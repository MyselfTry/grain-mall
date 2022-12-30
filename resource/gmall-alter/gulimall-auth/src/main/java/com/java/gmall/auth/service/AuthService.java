package com.java.gmall.auth.service;

import com.java.core.bean.Resp;
import com.java.core.utils.JwtUtils;
import com.java.gmall.auth.config.JwtProperties;
import com.java.gmall.auth.feign.GmallUmsFeign;
import com.java.gmall.ums.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangli
 * @since 2020/1/28 15:06
 */
@Service
public class AuthService {
	@Autowired
	private GmallUmsFeign gmallUmsFeign;
	@Autowired
	private JwtProperties jwtProperties;

	public String accredit(String username, String password) {
		// 调用微服务，执行查询
		Resp<Member> resp = this.gmallUmsFeign.queryUser(username, password);
		Member memberEntity = resp.getData();

		// 如果查询结果为null，则直接返回null
		if (memberEntity == null) {
			return null;
		}

		// 生成token
		Map<String, Object> map = new HashMap<>();
		map.put("id", memberEntity.getId());
		map.put("username", memberEntity.getUsername());
		try {
			return JwtUtils.generateToken(map, jwtProperties.getPrivateKey(), jwtProperties.getExpire());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
